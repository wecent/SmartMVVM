package com.wecent.common.network.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * @desc: 自定义 Http 网络请求异常类
 * @author: wecent
 * @date: 2020/4/25
 */
public class HttpException {

    /**
     * 未知错误
     */
    public static final int UNKNOWN_ERROR = 100;

    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 101;

    /**
     * 网络错误
     */
    public static final int NETWORK_ERROR = 102;

    public static BaseException handleException(Throwable e) {
        BaseException ex;
        if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            // 解析错误
            ex = new BaseException(PARSE_ERROR, e.getMessage());
            return ex;
        } else if (e instanceof ConnectException) {
            // 网络错误
            ex = new BaseException(NETWORK_ERROR, e.getMessage());
            return ex;
        } else if (e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
            // 连接错误
            ex = new BaseException(NETWORK_ERROR, e.getMessage());
            return ex;
        } else {
            // 未知错误
            ex = new BaseException(UNKNOWN_ERROR, e.getMessage());
            return ex;
        }
    }
}