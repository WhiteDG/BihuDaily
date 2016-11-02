package com.white.bihudaily.module.editor;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.white.bihudaily.BasePresenter;
import com.white.bihudaily.R;
import com.white.bihudaily.app.Constant;
import com.white.bihudaily.base.BaseWithToolbarActivity;
import com.white.bihudaily.bean.Editor;

import butterknife.BindView;

public class EditorInfoActivity extends BaseWithToolbarActivity {

    @BindView(R.id.web_editor)
    WebView webEditor;

    private Editor mEditor;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void beforeContentView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_editor_info;
    }

    @Override
    protected void prepareData(Intent intent) {
        mEditor = intent.getParcelableExtra(Constant.EDITOR);
    }

    @Override
    protected void initView() {
        initToolbar(R.string.editor_info_title);

        WebSettings webSettings = webEditor.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(getApplicationContext().getDir("cache", 0).getPath());
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLoadsImagesAutomatically(true);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String url = String.format("https://news-at.zhihu.com/api/4/editor/%s/profile-page/android", mEditor.getId());
        webEditor.loadUrl(url);
    }
}
