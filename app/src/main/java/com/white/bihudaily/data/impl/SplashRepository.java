package com.white.bihudaily.data.impl;

import com.white.bihudaily.api.BihuClient;
import com.white.bihudaily.base.BaseRepository;
import com.white.bihudaily.bean.StartImg;
import com.white.bihudaily.data.SplashSource;

import rx.Observable;

/**
 * Author White
 * Date 2016/8/13
 * Time 12:54
 */
public class SplashRepository extends BaseRepository implements SplashSource {
    @Override
    public Observable<StartImg> loadImg() {
        return BihuClient.getBihuService().getStartImg();
    }
}
