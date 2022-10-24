package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//RestController表示只返回数据
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping()
    public boolean save(@RequestBody User user){
        //@RequestBody 表示前台传入json对象的时候，将json对象转换为user对象
        return userService.saveUser(user);
    }


    @GetMapping("/findAll")
    public List<User> findAll(){
//        return userService.findAll();
        return userService.list();
    }


    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
//        return userService.deleteById(id);
        return userService.removeById(id);
    }

    /**
     * 批量删除接口
     * @param ids
     * @return
     */
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
//        return userService.deleteById(id);
        return userService.removeByIds(ids);
    }
    /*
    @GetMapping("/page")
    //@RequestParam 就是将 :8081/user/page?pageNum=1&pageSize=10后面的数据读取出来
    // limit(args1,args2)  args1 = (pageNum - 1) * pageSize ; args2 = pageSize
    public Map<String, Object> findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        pageNum = (pageNum - 1)*pageSize;
        Integer total = userService.selectTotal();
        List<User> data = userService.selectPage(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("data",data);
        map.put("total",total);
        return map;
    }
     */

    //使用mp的方式进行分页查询
    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String email){
        //设置page的页码和当前页
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
        IPage<User> userPage = userService.page(page, queryWrapper);

        return userPage;
    }

    /*

    @GetMapping("/pageByUsername")
    //@RequestParam 就是将 :8081/user/page?pageNum=1&pageSize=10后面的数据读取出来
    // limit(args1,args2)  args1 = (pageNum - 1) * pageSize ; args2 = pageSize
    public Map<String, Object> findPageByUsername(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam String username){
        pageNum = (pageNum - 1)*pageSize;
        Integer total = userService.selectTotalByName(username);
        List<User> data = userService.selectPageByUserName(pageNum, pageSize,username);
        Map<String, Object> map = new HashMap<>();
        map.put("data",data);
        map.put("total",total);
        return map;
    }

    @GetMapping("/pageByEmail")
    //@RequestParam 就是将 :8081/user/page?pageNum=1&pageSize=10后面的数据读取出来
    // limit(args1,args2)  args1 = (pageNum - 1) * pageSize ; args2 = pageSize
    public Map<String, Object> findPageByEmail(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam String email){
        pageNum = (pageNum - 1)*pageSize;
        Integer total = userService.selectTotalByEmail(email);
        List<User> data = userService.selectPageByEmail(pageNum, pageSize,email);
        Map<String, Object> map = new HashMap<>();
        map.put("data",data);
        map.put("total",total);
        return map;
    }

    @GetMapping("/searchByUsernameAndEmail")
    //@RequestParam 就是将 :8081/user/page?pageNum=1&pageSize=10后面的数据读取出来
    // limit(args1,args2)  args1 = (pageNum - 1) * pageSize ; args2 = pageSize
    public Map<String, Object> findPageByUsernameAndEmail(@RequestParam Integer pageNum,
                                               @RequestParam Integer pageSize,
                                               @RequestParam String username,
                                               @RequestParam String email){
        pageNum = (pageNum - 1)*pageSize;
        Integer total = userService.selectTotalByNameAndEmail(username,email);
        List<User> data = userService.searchPageByUsernameAndEmail(pageNum, pageSize,username,email);
        Map<String, Object> map = new HashMap<>();
        map.put("data",data);
        map.put("total",total);
        return map;
    }

     */


}
