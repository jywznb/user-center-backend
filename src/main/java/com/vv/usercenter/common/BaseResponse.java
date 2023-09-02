package com.vv.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 * @param <T>
 * @author jyw
 */
@Data
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = -7012720887203883610L;
    private int code;
    private T data;
    private String message;
    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data, String message) {
        this(code, data, message, "");
    }

    public BaseResponse(int code, T data) {
        this(code, data, "", "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
}