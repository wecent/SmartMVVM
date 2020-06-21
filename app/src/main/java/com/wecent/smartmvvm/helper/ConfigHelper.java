package com.wecent.smartmvvm.helper;

import android.text.TextUtils;

import com.wecent.smartmvvm.constant.ConfigConstants;
import com.wecent.common.utils.SPUtils;

/**
 * @desc: 登录信息本地缓存管理
 * @author: wecent
 * @date: 2020/4/28
 */
public class ConfigHelper {

    public static String getToken() {
        return SPUtils.getInstance().getString(ConfigConstants.KEY_USER_TOKEN);
    }

    public static void setToken(String token) {
        SPUtils.getInstance().put(ConfigConstants.KEY_USER_TOKEN, token);
    }

    public static String getName() {
        return SPUtils.getInstance().getString(ConfigConstants.KEY_USER_NAME);
    }

    public static void setName(String name) {
        SPUtils.getInstance().put(ConfigConstants.KEY_USER_NAME, name);
    }

    public static String getAvatar() {
        return SPUtils.getInstance().getString(ConfigConstants.KEY_USER_AVATAR);
    }

    public static void setAvatar(String avatar) {
        SPUtils.getInstance().put(ConfigConstants.KEY_USER_AVATAR, avatar);
    }

    public static String getPhone() {
        return SPUtils.getInstance().getString(ConfigConstants.KEY_USER_PHONE);
    }

    public static void setPhone(String phone) {
        SPUtils.getInstance().put(ConfigConstants.KEY_USER_PHONE, phone);
    }

    /**
     * 注入登录本地缓存
     */
    public static void injectConfig() {
        SPUtils.getInstance().put(ConfigConstants.KEY_USER_TOKEN, "");
        SPUtils.getInstance().put(ConfigConstants.KEY_USER_NAME, "");
        SPUtils.getInstance().put(ConfigConstants.KEY_USER_AVATAR, "");
        SPUtils.getInstance().put(ConfigConstants.KEY_USER_PHONE, "");
    }

    /**
     * 清除登录本地缓存
     */
    public static void clearConfig() {
        SPUtils.getInstance().put(ConfigConstants.KEY_USER_TOKEN, "");
        SPUtils.getInstance().put(ConfigConstants.KEY_USER_NAME, "");
        SPUtils.getInstance().put(ConfigConstants.KEY_USER_AVATAR, "");
        SPUtils.getInstance().put(ConfigConstants.KEY_USER_PHONE, "");
    }

    public static boolean isLogin() {
        return !TextUtils.isEmpty(getToken());
    }
}
