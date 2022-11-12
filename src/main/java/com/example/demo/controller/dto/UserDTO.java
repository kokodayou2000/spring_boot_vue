package com.example.demo.controller.dto;

import lombok.Data;

/**
 * 接受前端登录请求的类
 */
@Data
public class UserDTO {

    private String username;

    private String password;

    private String nickname;

    private String avatarUrl;

    private String token;

}
