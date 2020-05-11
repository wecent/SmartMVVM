package com.wecent.common.network.manager;

/**
 * @desc: 网络状态
 * @author: wecent
 * @date: 2020/4/12
 */
public class NetworkState {

    private String code;
    private boolean success = true;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
