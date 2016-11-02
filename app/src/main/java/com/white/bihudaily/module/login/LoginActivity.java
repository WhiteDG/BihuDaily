package com.white.bihudaily.module.login;

import android.content.Intent;
import android.os.Bundle;

import com.white.bihudaily.BasePresenter;
import com.white.bihudaily.R;
import com.white.bihudaily.base.BaseWithToolbarActivity;

public class LoginActivity extends BaseWithToolbarActivity {


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void beforeContentView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void prepareData(Intent intent) {

    }

    @Override
    protected void initView() {
        initToolbar();
        setToolbarTitle("登录");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
