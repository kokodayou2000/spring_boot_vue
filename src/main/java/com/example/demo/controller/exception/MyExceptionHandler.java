package com.example.demo.controller.exception;

import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyExceptionHandler {

    /**
     * @ExceptionHandler相当于controller的@RequestMapping
     * 如果抛出的的是ServiceException，则调用该方法
     * @param se 业务异常
     * @return Result
     */
//    @ExceptionHandler(ServiceException.class)
//    @ResponseBody
//    public Response handle(ServiceException se){
//        return ResponseUtils.error(se.getCode(),se.getMessage());
//    }

    /**
     * 全局异常捕获器，当throw new ServiceException的时候，会被这个捕获器获取到，并且返回Result对象
     * @param se
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handler(ServiceException se){
        return Result.error(se.getCode(),se.getMessage());
    }
}
