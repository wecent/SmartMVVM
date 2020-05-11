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

    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=3600
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
     */
    static final String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600";

    /**
     * 避免出现 HTTP 403 Forbidden
     * 参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
     */
    static final String AVOID_HTTP403_FORBIDDEN = "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";


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
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, " + CACHE_CONTROL_CACHE)
                    .removeHeader("Pragma")
                    .build();
        }
    }

}
