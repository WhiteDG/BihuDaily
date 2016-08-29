package com.white.bihudaily.bigimage;

import com.white.bihudaily.BasePresenter;
import com.white.bihudaily.BaseView;

/**
 * Author White
 * Date 2016/8/14
 * Time 18:36
 */
public interface ImageContract {

    interface View extends BaseView<Presenter> {

        void showLoading(boolean isShow);

    }

    interface Presenter extends BasePresenter {
        void loadBigImage(String url);

        void downloadBigImage(String url);
    }
}
