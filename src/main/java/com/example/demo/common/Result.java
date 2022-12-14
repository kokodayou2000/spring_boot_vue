package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.spring.web.json.Json;

/**
 * 接口统一返回包装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private String code;

    private String msg;

    private Object data;

    public static Result success(){
        return new Result(Constants.CODE_200,"success",null);
    }

    public static Result success(Object data){
        return new Result(Constants.CODE_200,"success",data);
    }

    public static Result error(){
        return new Result(Constants.CODE_500,"服务器错误",null);
    }

    public static Result error(String code,String msg){
        return new Result(code,msg,null);
    }


}
