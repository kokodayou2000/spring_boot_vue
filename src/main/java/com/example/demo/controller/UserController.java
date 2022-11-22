package com.example.demo.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Constants;
import com.example.demo.common.Result;
import com.example.demo.controller.dto.UserDTO;
import com.example.demo.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.example.demo.service.IUserService;
import com.example.demo.entity.User;


import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author deng
 * @since 2022-10-25
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    //RequestBody将前端传入的json转换为UserDTO对象
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_401,"用户名和密码错误");
        }else{
            //当出现异常的时候，就直接返回了
            UserDTO userDto = userService.login(userDTO);

            return Result.success(userDto);
        }
    }
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        String nickname = userDTO.getNickname();
        String password = userDTO.getPassword();

        if (StrUtil.isBlank(username) || StrUtil.isBlank(password) || StrUtil.isBlank(nickname)){
            return Result.error(Constants.CODE_401,"用户名和密码错误");
        }else{
            //当出现异常的时候，就直接返回了
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setNickname(userDTO.getNickname());
            boolean save = userService.save(user);
            if (!save){
                return Result.error("400","创建用户失败");
            }else{
                UserDTO dto = new UserDTO();
                BeanUtil.copyProperties(user,dto,true);
                return Result.success(dto);
            }

        }
    }

    @GetMapping("/getUserByName/{username}")
    public Result getUserByName(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return Result.success(userService.getOne(queryWrapper));
    }




    @PostMapping
    public Boolean save(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return userService.removeByIds(ids);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @GetMapping("/page")
    public Page<User> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String username,
                               @RequestParam(defaultValue = "") String email,
                               @RequestParam(defaultValue = "") String address) {
        //设置page的页码和当前页
        //这个Page是IPage Page的接口，输出的结果需要进行类型转换
        IPage<User> page = new Page<>(pageNum,pageSize);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //SELECT id,username,password,nickname,email,phone,address FROM sys_user WHERE (username LIKE ? AND (email LIKE ?)) LIMIT ?
        //username like #{username} and email like #{email}

        //进行非空校验
        if (!("".equals(username))){
            queryWrapper.like("username",username);
        }
        if (!("".equals(email))){
            queryWrapper.like("email",email);
        }
        //倒序排列
        queryWrapper.orderByDesc("id");

        User user = TokenUtils.getCurrentUser();
        System.out.println(user.toString());


        return (Page<User>) userService.page(page, queryWrapper);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        List<User> list = userService.list();
        //通过工具类创建write 写入到磁盘路径
        // ExcelWriter writer = ExcelUtil.getWriter(fileUploadPath + "/用户信息.xlsx");
        //在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("username","用户名");
        writer.addHeaderAlias("password","密码");
        writer.addHeaderAlias("nickname","昵称");
        writer.addHeaderAlias("email","邮箱");
        writer.addHeaderAlias("phone","电话");
        writer.addHeaderAlias("address","地址");
        writer.addHeaderAlias("createTime","创建时间");

        //一次性写出 list内的对象到excel,使用默认样式，强制输出标题
        writer.write(list,true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }

    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception{
        //读取上传的file
        InputStream inputStream = file.getInputStream();
        //使用hutool的 Excel 工具类 读取
        ExcelReader reader = ExcelUtil.getReader(inputStream);


        reader.addHeaderAlias("用户名","username");
        reader.addHeaderAlias("密码","password");
        reader.addHeaderAlias("昵称","nickname");
        reader.addHeaderAlias("邮箱","email");
        reader.addHeaderAlias("电话","phone");
        reader.addHeaderAlias("地址","address");
        reader.addHeaderAlias("创建时间","createTime");

        //以User.class为模板进行读取
        List<User> list = reader.readAll(User.class);
        return userService.saveBatch(list);


    }

}
