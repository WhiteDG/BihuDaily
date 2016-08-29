package com.white.bihudaily.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.white.bihudaily.BasePresenter;
import com.white.bihudaily.R;
import com.white.bihudaily.app.Constant;
import com.white.bihudaily.base.BaseWithToolbarActivity;
import com.white.bihudaily.utils.CacheCleanUtil;
import com.white.bihudaily.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseWithToolbarActivity {


    @BindView(R.id.rl_no_pic)
    RelativeLayout mRlNoPic;
    @BindView(R.id.rl_big_font)
    RelativeLayout mRlBigFont;
    @BindView(R.id.tv_cache_size)
    TextView mTvCacheSize;
    @BindView(R.id.ll_clear_cache)
    LinearLayout mLlClearCache;
    @BindView(R.id.tv_notification)
    TextView mTvNotification;
    @BindView(R.id.tv_me)
    TextView mTvMe;
    @BindView(R.id.cb_no_img)
    AppCompatCheckBox mCbNoImg;
    @BindView(R.id.cb_big_font)
    AppCompatCheckBox mCbBigFont;

    private String cacheSize;
    private boolean isNoImgMode;
    private boolean isBigFont;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void beforeContentView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void prepareData(Intent intent) {
        isNoImgMode = (boolean) SPUtils.get(this, Constant.KEY_AUTO_LOAD_IMAGE, true);
        isBigFont = (boolean) SPUtils.get(this, Constant.KEY_BIG_FONT, false);
        cacheSize = CacheCleanUtil.getTotalCacheSzie();
    }

    @Override
    protected void initView() {
        initToolbar(R.string.action_settings);
        mCbNoImg.setChecked(!isNoImgMode);
        mCbBigFont.setChecked(isBigFont);
        mTvCacheSize.setText(cacheSize);
    }

    @Override
    protected void initListener() {

    }

    @OnClick(R.id.tv_me)
    public void aboutMe(View view) {

    }

    @OnClick(R.id.ll_clear_cache)
    public void clearCache(View view) {
        CacheCleanUtil.cleanAllCache();
        mTvCacheSize.setText("无缓存");
    }

    @OnClick(R.id.rl_big_font)
    public void toggleBigFont(View view) {
        mCbBigFont.toggle();
    }

    @OnClick(R.id.rl_no_pic)
    public void toggleNoImg(View view) {
        mCbNoImg.toggle();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        SPUtils.put(this, Constant.KEY_AUTO_LOAD_IMAGE, !mCbNoImg.isChecked());
        SPUtils.put(this, Constant.KEY_BIG_FONT, mCbBigFont.isChecked());
        super.onDestroy();
    }
}
