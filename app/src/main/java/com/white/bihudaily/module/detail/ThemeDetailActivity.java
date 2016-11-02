package com.white.bihudaily.module.detail;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.white.bihudaily.R;
import com.white.bihudaily.bean.DetailContent;

public class ThemeDetailActivity extends BaseDetailActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_theme_detail;
    }


    @Override
    public void showDetail(DetailContent detailContent) {
        mDetailContent = detailContent;
        if (detailContent.getType() == 1) {
            webContent.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            webContent.loadUrl(detailContent.getShare_url());
        } else {
            loadHtml(detailContent);
        }
    }

}
