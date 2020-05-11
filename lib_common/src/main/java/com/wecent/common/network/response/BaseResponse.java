package com.wecent.common.network.response;

/**
 * @desc:
 * @author: wecent
 * @date: 2020/4/12
 */
public class BaseResponse<T> {

    public int code;
    public String msg;
    public T data;

    @Override
    public String toString() {
        return "Response{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

}