package com.example.demo.utils;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.entity.User;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    /**
     * 过期时间5分钟
     */
//    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    public static String getToken(User user) {
//        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        DateTime offset = DateUtil.offsetMinute(new Date(), 60); //使用Hutool工具类来设置过期时间

        String token = "";
        token = JWT.create().withAudience(user.getId().toString()) // 将 user id 保存到 token 里面
                .withExpiresAt(offset) //60分钟后token过期
                .sign(Algorithm.HMAC256(user.getPassword()));    // 以 password 作为 token 的密钥
        return token;
    }

    private static IUserService staticUserService;

    @Resource
    private IUserService userService;

    @PostConstruct
    public void setUserService(){
        staticUserService = userService;
    }

    /**
     * 获取当前登录的User对象
     * @return
     */
    public static User getCurrentUser(){
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)){
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(Integer.valueOf(userId));
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

}
