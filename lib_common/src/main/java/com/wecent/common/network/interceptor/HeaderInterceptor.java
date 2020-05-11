package com.wecent.common.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @desc: 网络请求请求头拦截器
 * @author: wecent
 * @date: 2020/4/25
 */
public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request request = originalRequest.newBuilder()
//                    .header("", "")
//                    .header("", "")
                .method(originalRequest.method(), originalRequest.body())
                .build();
        return chain.proceed(request);
    }

}
