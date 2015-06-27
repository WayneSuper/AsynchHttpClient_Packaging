package com.wayne.asynch;

/**
 * DESCRIPT: 结果封装
 * Author:Wayne
 * EMAIL:chudian@gmail.com
 * CREATE:2015/6/27
 * VERSION:1.0
 */
public class Result<T> {
    public static final int JSON_PARSER_ERROR = 0x001;
    public static final int RESULT_EMPTY = 0x002;
    private int status;
    private String message;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOK() {
        return status >= 2000 && status < 3000;
    }
}
