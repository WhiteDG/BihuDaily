package com.white.bihudaily.module.splash;

import android.app.Activity;
import android.content.Intent;

import com.white.bihudaily.BasePresenterImpl;
import com.white.bihudaily.R;
import com.white.bihudaily.app.Constant;
import com.white.bihudaily.data.SplashSource;
import com.white.bihudaily.service.SplashService;
import com.white.bihudaily.utils.CommonUtil;
import com.white.bihudaily.utils.NetUtils;
import com.white.bihudaily.utils.SPUtils;

/**
 * Author White
 * Date 2016/8/13
 * Time 12:45
 */
public class SplashPresenter extends BasePresenterImpl<SplashSource, SplashContract.View> implements SplashContract.Presenter {


    public SplashPresenter(SplashSource source, SplashContract.View splashView) {
        super(source, splashView);
    }

    @Override
    public void loadImg() {
        Intent intent = new Intent(mView.getContext(), SplashService.class);
        mView.getContext().startService(intent);
    }

    @Override
    public void showImg(Activity context) {
        if (!NetUtils.isConnected(context)) {
            mView.turn2DailyActivity();
            return;
        }

        boolean hasOpenApp = (boolean) SPUtils.get(context, Constant.KEY_HAS_OPEN_APP, false);
        String imgText = (String) SPUtils.get(context, Constant.KEY_START_IMG_TEXT, "@Bihu");
        mView.showText(imgText);
        if (!hasOpenApp) {
            // 第一次打开APP
            mView.showImg(R.drawable.splash);
            SPUtils.put(context, Constant.KEY_HAS_OPEN_APP, true);
            loadImg();
        } else {
            String imgPath = (String) SPUtils.get(context, Constant.KEY_START_IMG_PATH, "");
            if (imgPath != null && !imgPath.equals("")) {
                mView.showImg(imgPath);
                String today = (String) SPUtils.get(context, Constant.KEY_TODAY, "");
                if (!CommonUtil.getToday().equals(today)) {
                    loadImg();
                }
            } else {
                // 没有缓存
                mView.showImg(R.drawable.splash);
                loadImg();
                // 没有缓存
                mView.showImg(R.drawable.splash);
            }
        }
    }
}
