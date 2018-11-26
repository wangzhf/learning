package com.wangzhf.common.util;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * 使用Retrofit实现HTTP请求
 */
public class RetrofitHelper<T> {

    /**
     * 超时时间
     */
    public static final int TIMEOUT = 60;

    /**
     * 初始化Retrofit
     */
    public Retrofit initRetrofit(String url) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 设置超时
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();
        return new Retrofit.Builder()
            // 设置请求的域名
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();
    }
}
