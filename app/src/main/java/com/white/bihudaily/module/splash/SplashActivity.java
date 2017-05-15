package com.white.bihudaily.module.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.white.bihudaily.R;
import com.white.bihudaily.app.Constant;
import com.white.bihudaily.base.BaseActivity;
import com.white.bihudaily.bean.StartImg;
import com.white.bihudaily.data.impl.SplashRepository;
import com.white.bihudaily.service.SplashService;
import com.white.bihudaily.utils.ActivityUtils;

import butterknife.BindView;

public class SplashActivity extends BaseActivity<SplashContract.Presenter> implements SplashContract.View {

    @BindView(R.id.iv_start)
    ImageView ivStart;

    @BindView(R.id.tv_imgAuthor)
    TextView tvImgAuthor;

    private ScaleAnimation mScaleAnim;

    @Override
    protected void beforeContentView() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void prepareData(Intent intent) {

    }

    @Override
    protected void initView() {
        mScaleAnim = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mScaleAnim.setFillAfter(true);
        mScaleAnim.setDuration(3000);
    }

    @Override
    protected void initListener() {
        mScaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                turn2DailyActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter.showImg(this);
    }

    @Override
    protected SplashContract.Presenter createPresenter() {
        return new SplashPresenter(new SplashRepository(), this);
    }


    @Override
    public void showImg(int resId) {
        ivStart.setImageResource(resId);
        ivStart.startAnimation(mScaleAnim);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showImg(String imgUrl) {
        Glide.with(this).
                load(imgUrl).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                into(ivStart);
        ivStart.startAnimation(mScaleAnim);
    }

    @Override
    public void showText(String text) {
        tvImgAuthor.setText(text);
    }


    @Override
    public void turn2DailyActivity() {
        ActivityUtils.toDailysActivity(SplashActivity.this);
        finish();
    }

    @Override
    public void getStartImgSuccess(final StartImg startImg) {
        Intent intent = new Intent(this, SplashService.class);
        intent.putExtra(Constant.IMAGE_URL, startImg);
        startService(intent);
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
