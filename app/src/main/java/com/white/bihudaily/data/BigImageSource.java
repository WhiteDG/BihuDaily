package com.white.bihudaily.data;

import com.white.bihudaily.base.BaseSource;

/**
 * Author White
 * Date 2016/8/13
 * Time 16:01
 */
public interface BigImageSource extends BaseSource {


    void download(String url);

    void loadImg(String url);


}
