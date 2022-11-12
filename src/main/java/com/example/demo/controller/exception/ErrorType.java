package com.example.demo.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  ErrorType {
    /**
     * 错误类型
     */
    OBJECT_NOT_FOUND("0", "对象不存在"),

    INVALID_PARAMS("1", "参数不正确"),

    result_not_exist("2", "记录不存在");

    /**
     * 错误码
     */
    private String code;

    /**
     * 提示信息
     */
    private String msg;
}
