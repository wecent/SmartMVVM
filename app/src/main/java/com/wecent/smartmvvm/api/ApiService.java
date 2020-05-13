package com.wecent.smartmvvm.api;

import com.wecent.common.network.response.BaseResponse;
import com.wecent.smartmvvm.login.LoginEntity;


import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @desc: Http 网络请求服务
 * @author: wecent
 * @date: 2020/4/12
 */
public interface ApiService {

    /**
     * 用户登录
     *
     * @param phone    手机号
     * @param password 密码
     * @return
     */
    @POST("api/auth/login")
    Observable<BaseResponse<LoginEntity>> login(@Query("phone") String phone,
                                                @Query("password") String password);
//
//    @POST("api/auth/del")
//    Observable<BaseEntity> postDelete();
//
//    @POST("api/auth/register")
//    Observable<RegisterEntity> postRegister(@Query("phone") String phone,
//                                            @Query("password") String password,
//                                            @Query("code") String code);
//
//    @POST("api/auth/findPwd")
//    Observable<BaseEntity> postForget(@Query("phone") String phone,
//                                      @Query("password") String password,
//                                      @Query("code") String code);
//
//    @POST("api/auth/regCaptcha")
//    Observable<BaseEntity> postCaptcha(@Query("phone") String phone);
//
//    @POST("api/auth/logout")
//    Observable<BaseEntity> postLogout();

}
