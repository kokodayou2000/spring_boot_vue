package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;


    public int save(User user){

        System.out.println(user.toString());
        //id是自增的，一般不需要进行设置，除非是想通过id更新某个数据
        if (user.getId() == null){
            return userMapper.save(user);
        }else{
            return userMapper.update(user);
        }

    }


    public List<User> findAll() {
        return userMapper.findAll();
    }

    public Integer deleteById(Integer id) {
        return userMapper.deleteById(id);
    }

    public List<User> selectPage(Integer pageNum, Integer pageSize) {

        return userMapper.findPage(pageNum,pageSize);
    }

    public Integer selectTotal() {
        return userMapper.selectTotal();
    }

    public List<User> selectPageByUserName(Integer pageNum, Integer pageSize, String username) {
        return userMapper.findPageByUsername(pageNum,pageSize,username);
    }

    public Integer selectTotalByName(String username) {
        return userMapper.selectTotalByName(username);
    }

    public Integer selectTotalByEmail(String email) {
        return userMapper.selectTotalByEmail(email);
    }

    public List<User> selectPageByEmail(Integer pageNum, Integer pageSize, String email) {
        return userMapper.findPageByEmail(pageNum,pageSize,email);
    }

    public Integer selectTotalByNameAndEmail(String username, String email) {
        return userMapper.selectTotalByUsernameAndEmail(username,email);
    }

    public List<User> searchPageByUsernameAndEmail(Integer pageNum, Integer pageSize, String username, String email) {
        return userMapper.findPageByUsernameAndEmail(pageNum,pageSize,username,email);
    }
}
