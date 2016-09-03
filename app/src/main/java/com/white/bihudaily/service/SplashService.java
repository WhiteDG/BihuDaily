package com.white.bihudaily.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Author White
 * Date 2016/8/14
 * Time 12:16
 */
public class SplashService extends IntentService {


    public SplashService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // 后台下载图片

    }
}
