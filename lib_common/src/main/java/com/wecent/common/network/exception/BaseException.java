package com.wecent.common.network.exception;

import static com.wecent.common.network.exception.HttpException.NETWORK_ERROR;
import static com.wecent.common.network.exception.HttpException.PARSE_ERROR;
import static com.wecent.common.network.exception.HttpException.UNKNOWN_ERROR;

/**
 * @desc: Http网络请求异常类
 * @author: wecent
 * @date: 2020/4/25
 */
public class BaseException extends Exception {

    public int code;
    public String display;

    public BaseException(int code, String display) {
        this.code = code;
        this.display = display;
    }

    public BaseException(int code, String message, String display) {
        super(message);
        this.code = code;
        this.display = display;
    }

    public boolean isHttpException() {
        return code == UNKNOWN_ERROR
                || code == PARSE_ERROR
                || code == NETWORK_ERROR;
    }
}