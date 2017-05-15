package com.white.bihudaily.module.splash;

import android.app.Activity;
import android.content.Context;

import com.white.bihudaily.BasePresenter;
import com.white.bihudaily.BaseView;
import com.white.bihudaily.bean.StartImg;

/**
 * Author White
 * Date 2016/8/13
 * Time 12:47
 */
public interface SplashContract {

    interface View extends BaseView<Presenter> {

        void showImg(String imgUrl);

        void showText(String text);

        void turn2DailyActivity();

        void getStartImgSuccess(StartImg startImg);

        void showImg(int resId);

        Context getContext();
    }

    interface Presenter extends BasePresenter {

        void loadImg();

        void showImg(Activity context);
    }
}
