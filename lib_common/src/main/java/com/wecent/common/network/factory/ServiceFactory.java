package com.wecent.common.network.factory;

import com.wecent.common.network.ApiConstants;
import com.wecent.common.network.interceptor.CacheInterceptor;
import com.wecent.common.network.interceptor.HeaderInterceptor;
import com.wecent.common.network.interceptor.LoggingInterceptor;
import com.wecent.common.network.interceptor.ParamsInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @desc: Http网络请求工厂
 * @author: wecent
 * @date: 2020/4/25
 */
public class ServiceFactory {

    /**
     * 默认的超时时间
     */
    public static final int DEFAULT_MILLI_SECONDS = 60000;

    private static Retrofit mRetrofit;
    private static Map<String, Retrofit> mRetrofitMap = new HashMap<>();

    public synchronized static <T> T create(Class<T> service) {
        if (mRetrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_HTTP_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            builder.client(getOkHttpClient());
            mRetrofit = builder.build();
        }
        return mRetrofit.create(service);
    }

    public synchronized static <T> T create(Class<T> service, String retrofitKey, String baseUrl) {
        if (!mRetrofitMap.containsKey(retrofitKey) || mRetrofitMap.get(retrofitKey) == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            builder.client(getOkHttpClient());
            mRetrofitMap.put(retrofitKey, builder.build());
        }
        return mRetrofitMap.get(retrofitKey).create(service);
    }

    public static void clear() {
        mRetrofitMap.clear();
        mRetrofit = null;
    }

    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = getOkHttpClientBuilder();
        return builder.build();
    }

    @NonNull
    private static OkHttpClient.Builder getOkHttpClientBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .retryOnConnectionFailure(true)
                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new ParamsInterceptor())
                .addInterceptor(new CacheInterceptor())
                .connectTimeout(DEFAULT_MILLI_SECONDS, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_MILLI_SECONDS, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_MILLI_SECONDS, TimeUnit.MILLISECONDS);
        return builder;
    }

}
