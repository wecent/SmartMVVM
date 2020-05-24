package com.wecent.common.network.interceptor;

import com.wecent.common.utils.LogUtils;
import com.wecent.common.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @desc: CacheInterceptor
 * @author: wecent
 * @date: 2020/4/25
 */
public class CacheInterceptor implements Interceptor {

    /**
     * 设缓存有效期为一天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24;

    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtils.isAvailable()) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            LogUtils.e("no network");
        }
        Response originalResponse = chain.proceed(request);

        if (NetworkUtils.isAvailable()) {
            // 有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", cacheControl)
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, " + CACHE_CONTROL_CACHE)
                    .build();
        }
    }

}
