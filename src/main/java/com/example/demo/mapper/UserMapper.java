package com.example.demo.mapper;


import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {


    List<User> findAll();

    Integer save(User user);
    Integer update(User user);
    Integer deleteById(Integer id);

    List<User> findPage(Integer pageNum, Integer pageSize);

    Integer selectTotal();

    List<User> findPageByUsername(Integer pageNum, Integer pageSize, String username);

    Integer selectTotalByName(String username);

    Integer selectTotalByEmail(String email);

    List<User> findPageByEmail(Integer pageNum, Integer pageSize, String email);

    Integer selectTotalByUsernameAndEmail(String username, String email);

    List<User> findPageByUsernameAndEmail(Integer pageNum, Integer pageSize, String username, String email);
}
