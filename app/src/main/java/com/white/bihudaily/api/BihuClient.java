package com.white.bihudaily.api;

import com.white.bihudaily.app.BihuDailyApplication;
import com.white.bihudaily.app.Constant;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author White
 * Date 2016/8/13
 * Time 13:26
 */
public class BihuClient {
    private static BihuApi mBihuApi;
    private static OkHttpClient okHttpClient;
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    private static final int DEFAUT_TIMEOUT = 10;

    public static BihuApi getBihuApi() {

        if (mBihuApi == null) {
            if (okHttpClient == null) {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                // 设置超时时间
                builder.connectTimeout(DEFAUT_TIMEOUT, TimeUnit.SECONDS);
                builder.readTimeout(DEFAUT_TIMEOUT, TimeUnit.SECONDS);
                builder.writeTimeout(DEFAUT_TIMEOUT, TimeUnit.SECONDS);
                // 添加缓存
                File cacheFile = new File(BihuDailyApplication.appContext.getCacheDir(), "okHttpCache");
                builder.cache(new Cache(cacheFile, 10 * 10 * 1024));
                // 添加拦截器
                // TODO: 2016/8/21 没有网络连接则使用缓存
                okHttpClient = builder.build();
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mBihuApi = retrofit.create(BihuApi.class);
        }
        return mBihuApi;
    }


}
