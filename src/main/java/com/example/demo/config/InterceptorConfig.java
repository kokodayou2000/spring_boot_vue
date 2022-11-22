package com.example.demo.config;

import com.example.demo.config.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getJwtInterceptor())
                .addPathPatterns("/**") //拦截所有的请求，通过判断Token是否合法 来决定是否登录
                .excludePathPatterns("/user/login","/user/register","/**/import","/**/export","/file/**","/echarts/**","/role/**","/menu/**","/dict/**","/roleMenu/**");
    }

    //将对象注册到容器中，如果直接new 就无法注入到容器了，JwtInterceptor中的UserService类就无法被注入
    @Bean
    public JwtInterceptor getJwtInterceptor(){
        return new JwtInterceptor();
    }
}
