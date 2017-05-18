package com.white.bihudaily.data;

import com.white.bihudaily.base.BaseSource;
import com.white.bihudaily.bean.StartImg;

import io.reactivex.Observable;

/**
 * Author White
 * Date 2016/8/13
 * Time 12:56
 */
public interface SplashSource extends BaseSource {

    Observable<StartImg> loadImg();

}
