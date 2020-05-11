package com.wecent.smartmvvm.api;

import com.wecent.common.network.factory.ServiceFactory;
import com.wecent.common.network.response.BaseResponse;
import com.wecent.smartmvvm.login.LoginEntity;

import io.reactivex.Observable;

/**
 * @desc: Http 网络请求管理
 * @author: wecent
 * @date: 2020/4/12
 */
public class ApiManager {

    private static ApiManager mInstance;
    private ApiService mService;

    private ApiManager() {

    }

    public static ApiManager getInstance() {
        if (mInstance == null) {
            mInstance = new ApiManager();
        }
        return mInstance;
    }

    private ApiService getService() {
        if (mService == null) {
            mService = ServiceFactory.create(ApiService.class);
        }
        return mService;
    }

    /**
     * 用户登录
     *
     * @param phone    手机号
     * @param password 密码
     * @return
     */
    public Observable<BaseResponse<LoginEntity>> login(String phone,
                                                       String password) {
        return getService().login(phone, password);
    }

//
//    /**
//     * 注销登录
//     *
//     * @return
//     */
//    public Observable<BaseEntity> postDelete() {
//        return mService.postDelete();
//    }
//
//    /**
//     * 用户注册
//     *
//     * @param phone    手机号
//     * @param password 密码
//     * @param code     验证码
//     * @return
//     */
//    public Observable<RegisterEntity> postRegister(String phone, String password, String code) {
//        return mService.postRegister(phone, passward, code);
//    }
//
//    /**
//     * 用户找回密码
//     *
//     * @param phone    手机号
//     * @param password 密码
//     * @param code     验证码
//     * @return
//     */
//    public Observable<BaseEntity> postForget(String phone, String password, String code) {
//        return mService.postForget(phone, password, code);
//    }
//
//    /**
//     * 获取短信验证码
//     *
//     * @param phone 手机号
//     * @return
//     */
//    public Observable<BaseEntity> postCaptcha(String phone) {
//        return mService.postCaptcha(phone);
//    }
//
//    /**
//     * 用户退出登录
//     *
//     * @return
//     */
//    public Observable<BaseEntity> postLogout() {
//        return mService.postLogout();
//    }

}
