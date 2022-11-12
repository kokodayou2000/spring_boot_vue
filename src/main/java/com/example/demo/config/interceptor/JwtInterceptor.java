package com.example.demo.config.interceptor;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Verification;
import com.example.demo.common.Constants;
import com.example.demo.controller.exception.ServiceException;
import com.example.demo.entity.User;
import com.example.demo.service.IUserService;
import com.example.demo.utils.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    /**
     * 重写该方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token"); //get Token
        //如果不是映射到方法，就直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        //进行验证
        if (StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401,"无token");
        }
        String userId ;
        try{
            userId = JWT.decode(token).getAudience().get(0); //取出载荷中的值
        }catch (JWTDecodeException e){
            throw new ServiceException(Constants.CODE_401,"Token解码错误");
        }
        //根据userId查询数据库信息
        User user = userService.getById(userId);
        if (null == user){
            throw new ServiceException(Constants.CODE_401,"用户不存在,请重新输入");
        }
        //根据用户密码加签验证 Token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();

        try{
            jwtVerifier.verify(token);
        }catch (JWTVerificationException e){
            throw new ServiceException(Constants.CODE_401,"Token验证失败");
        }
        return true;
    }
}
