package com.example.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.generator.config.INameConvert;
import com.example.demo.common.Constants;
import com.example.demo.controller.dto.UserDTO;
import com.example.demo.controller.exception.ServiceException;
import com.example.demo.entity.Menu;
import com.example.demo.entity.User;
import com.example.demo.mapper.MenuMapper;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.RoleMenuMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.utils.TokenUtils;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author deng
 * @since 2022-10-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static Logger LOGGER = LoggerFactory.getLogger("用户登录日志");

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private MenuMapper menuMapper;

    @Override
    public UserDTO login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        //当后台出现脏数据的时候，比如存在相同的username和password的情况，getOne会得到一个集合，这时候就会抛出异常
//        User one = getOne(queryWrapper);

        //解决方法1 使用list接收数据
//        List<User> list = list(queryWrapper);
//        return list.size() != 0;

        //解决方法2 使用try catch， 发生这种情况直接返回false
        User one ;
        try{
            one = getOne(queryWrapper);
        }catch (Exception e){
            LOGGER.error("数据库中不存在该用户数据");
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }

        if (one != null){
            BeanUtil.copyProperties(one,userDTO,true); //表示忽略大小写
            String token = TokenUtils.getToken(one); //得到token

            userDTO.setToken(token); //设置token

            //UserDTO -> MenuList
            //这个想要根据Role_MenuList这个表来确定这个角色对应的菜单

            //得到这个角色对应的 role
            String role = one.getRole(); //Role_Admin

            Integer roleId = roleMapper.selectByFlag(role);
            List<Integer> role_menu_List = roleMenuMapper.selectByRoleId(roleId);
            ArrayList<Menu> menus = new ArrayList<>();
            for (Integer integer : role_menu_List) {
                QueryWrapper<Menu> wrapper = new QueryWrapper<>();
                wrapper.eq("id",integer);
                Menu menu = menuMapper.selectOne(wrapper);
                menus.add(menu);
            }

            userDTO.setMenuList(menus);

            LOGGER.info("用户{} -> Token {} ",one.getUsername(),userDTO.getToken());
            LOGGER.info("用户{} -> 在{} 登录成功",one.getUsername(),new DateTime());
            LOGGER.info("用户{} -> 对应能使用的菜单 {} ",one.getUsername(),userDTO.getMenuList());


            return userDTO;
        }else{
            throw new ServiceException(Constants.CODE_401,"用户名和密码错误");
        }

    }




}
