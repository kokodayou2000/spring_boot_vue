package com.example.demo.service;

import com.example.demo.controller.dto.UserDTO;
import com.example.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author deng
 * @since 2022-10-25
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);




//    UserDTO register(UserDTO userDTO);
}
