package com.white.bihudaily.module.settings;

import android.content.Intent;
import android.os.Bundle;

import com.white.bihudaily.BasePresenter;
import com.white.bihudaily.R;
import com.white.bihudaily.base.BaseWithToolbarActivity;

public class SettingActivity extends BaseWithToolbarActivity {


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
    }

    @Override
    protected void initView() {
        initToolbar(R.string.action_settings);
        getFragmentManager().beginTransaction().add(R.id.fly_setting_content, new SettingsFragment())
                .commit();
    }

    @Override
    protected void initListener() {

    }


    @Override
    protected void initData(Bundle savedInstanceState) {

    }

}
