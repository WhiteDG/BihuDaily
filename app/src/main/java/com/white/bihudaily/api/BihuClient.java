package com.white.bihudaily.api;

import com.white.bihudaily.app.BihuDailyApplication;
import com.white.bihudaily.app.Constant;
import com.white.bihudaily.utils.NetUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

    private static final int DEFAULT_TIMEOUT = 10;
    private static final int CACHE_SIZE = 10 * 1024 * 1024;

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (NetUtils.isConnected(BihuDailyApplication.getAppContext())) {
                int maxAge = 60; // 在线缓存在1分钟内可读取
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    public static BihuApi getBihuService() {

        if (mBihuApi == null) {
            if (okHttpClient == null) {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                // 设置超时时间
                builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
                builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
                builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
                // 添加缓存
                File cacheFile = new File(BihuDailyApplication.getAppContext().getCacheDir(), "okHttpCache");
                builder.cache(new Cache(cacheFile, CACHE_SIZE));
                // 添加拦截器
                builder.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (!NetUtils.isConnected(BihuDailyApplication.getAppContext())) {
                            // 没有网络连接
                            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                        }
                        return chain.proceed(request);
                    }
                });
                builder.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                        .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
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
