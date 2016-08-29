package com.white.bihudaily.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.common.base.Charsets;
import com.white.bihudaily.R;
import com.white.bihudaily.app.Constant;
import com.white.bihudaily.base.BaseWithToolbarActivity;
import com.white.bihudaily.bean.DetailContent;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.bean.StoryExtra;
import com.white.bihudaily.data.impl.DetailRepository;
import com.white.bihudaily.utils.ActivityUtils;
import com.white.bihudaily.utils.NetUtils;
import com.white.bihudaily.utils.SPUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;

/**
 * Author White
 * Date 2016/8/18
 * Time 16:14
 */
public abstract class BaseDetailActivity extends BaseWithToolbarActivity<DetailContract.Presenter> implements DetailContract.View {

    @BindView(R.id.web_content)
    WebView webContent;

    @BindView(R.id.ll_loading)
    LinearLayout llLoading;

    @BindString(R.string.menu_like)
    String like;
    @BindString(R.string.menu_liked)
    String liked;
    @BindString(R.string.menu_star)
    String star;
    @BindString(R.string.menu_stared)
    String stared;

    protected Story mStory;
    protected DetailContent mDetailContent;
    protected DetailContract.Presenter mPresenter;
    protected StoryExtra storyExtra;

    protected View actionCommentView;
    protected View actionLikeView;
    protected TextView commentText;
    protected TextView likeText;
    protected ImageView likeImg;

    protected List<String> imgUrlList;

    @Override
    protected DetailContract.Presenter createPresenter() {
        return new DetailPresenter(new DetailRepository(), this);
    }

    @Override
    protected void beforeContentView() {

    }

    @Override
    protected void prepareData(Intent intent) {
        mStory = (Story) intent.getSerializableExtra(Constant.STORY);
    }

    @SuppressLint("AddJavascriptInterface")
    @Override
    protected void initView() {

        initToolbar();
        setToolbarTitle("");
        WebSettings webSettings = webContent.getSettings();
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
        // 调用js
        webContent.addJavascriptInterface(this, "BihuDaily");

    }


    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
//        mPresenter = new DetailPresenter(new DailyRepository(), this);
        mPresenter.loadDetailContent(mStory.getId());
        mPresenter.loadStoryExtra(mStory.getId());

    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        ActivityUtils.setVisible(isShow, llLoading);
    }

    @Override
    public void showLoadFail() {
        showToast(R.string.load_fail);
        onBackPressed();
    }

    @Override
    public void showStoryExtra(StoryExtra storyExtra) {
        this.storyExtra = storyExtra;
        commentText.setText(storyExtra.getComments() + "");
        likeText.setText(storyExtra.getPopularity() + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        menu.getItem(2).setActionView(R.layout.action_comment);
        menu.getItem(3).setActionView(R.layout.action_like);

        actionCommentView = menu.getItem(2).getActionView();
        actionLikeView = menu.getItem(3).getActionView();

        commentText = (TextView) actionCommentView.findViewById(R.id.action_item_comment_text);
        likeText = (TextView) actionLikeView.findViewById(R.id.action_item_vote_text);
        likeImg = (ImageView) actionLikeView.findViewById(R.id.action_item_vote_image);

        actionCommentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.toCommentActivity(BaseDetailActivity.this, mStory.getId(), storyExtra);
            }
        });
        actionLikeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 假装点赞
                if (likeImg.getContentDescription().equals("praise")) {
                    likeImg.setContentDescription("praised");
                    likeImg.setImageResource(R.drawable.praised);
                    Integer likeNum = Integer.valueOf(likeText.getText().toString());
                    likeText.setText(likeNum + 1 + "");
                    showToast("赞+1");
                } else {
                    likeImg.setContentDescription("praise");
                    likeImg.setImageResource(R.drawable.praise);
                    Integer likeNum = Integer.valueOf(likeText.getText().toString());
                    likeText.setText(likeNum - 1 + "");
                    showToast("赞-1");
                }
            }
        });

        List<Integer> starList = mPresenter.getStarListId(this);
        if (starList.contains(mStory.getId())) {
            menu.getItem(1).setIcon(R.drawable.collected);
            menu.getItem(1).setTitle(stared);
        }
        List<Integer> likeList = mPresenter.getLikeListId(this);
        if (likeList.contains(mStory.getId())) {
            menu.getItem(3).setIcon(R.drawable.praised);
            menu.getItem(3).setTitle(liked);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                showShare(mDetailContent.getShare_url());
                break;
            case R.id.menu_star:
                if (item.getTitle().equals(star)) {
                    mPresenter.addStar(this, mStory);
                    item.setTitle(stared);
                    item.setIcon(R.drawable.collected);
                } else {
                    mPresenter.removeStar(this, mStory);
                    item.setTitle(star);
                    item.setIcon(R.drawable.collect);
                }
                break;
            case R.id.menu_comment:
                ActivityUtils.toCommentActivity(this, mStory.getId(), storyExtra);
                break;
            case R.id.menu_like:
                if (item.getTitle().equals(like)) {
                    mPresenter.addLike(this, mStory.getId());
                    item.setTitle(liked);
                    item.setIcon(R.drawable.praised);
                } else {
                    mPresenter.removeLike(this, mStory.getId());
                    item.setTitle(like);
                    item.setIcon(R.drawable.praise);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showShare(String share_url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, share_url);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "分享到..."));
    }

    /**
     * 替换html中的<img class="content-image">标签的属性
     *
     * @param html
     * @return
     */
    protected String replaceImgTagFromHTML(String html, boolean autoLoad, boolean nightMode) {
        imgUrlList = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Elements es = doc.getElementsByTag("img");
        for (Element e : es) {
            if (!"avatar".equals(e.attr("class"))) {
                String imgUrl = e.attr("src");
                imgUrlList.add(imgUrl);
                String src = String.format("file:///android_asset/default_pic_content_image_%s_%s.png",
                        autoLoad ? "loading" : "download",
                        nightMode ? "dark" : "light");
                e.attr("src", src);
                e.attr("zhimg-src", imgUrl);
                e.attr("onclick", "onImageClick(this)");
            }
        }
        return doc.html();
    }

    protected void loadHtml(DetailContent detailContent) {
        StringBuilder htmlSb = new StringBuilder("<!doctype html>\n<html><head>\n<meta charset=\"utf-8\">\n" +
                "\t<meta name=\"viewport\" content=\"width=device-width,user-scalable=no\">");

        String content = detailContent.getBody();
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">\n";
        String img_replace = "<script src=\"file:///android_asset/img_replace.js\"></script>\n";
        String video = "<script src=\"file:///android_asset/video.js\"></script>\n";
        String zepto = "<script src=\"file:///android_asset/zepto.min.js\"></script>\n";
        String autoLoadImage = "onload=\"onLoaded()\"";

        boolean autoLoad = NetUtils.isWifi(this) || (boolean) SPUtils.get(this, Constant.KEY_AUTO_LOAD_IMAGE, true);
        boolean nightMode = (boolean) SPUtils.get(this, Constant.KEY_NIGHT, false);
        boolean largeFont = (boolean) SPUtils.get(this, Constant.KEY_BIG_FONT, false);
//        if (!autoLoad) { // 移动网络
//            autoLoad = (boolean) SPUtils.get(this, Constant.KEY_AUTO_LOAD_IMAGE, true);
//        }

        htmlSb.append(css)
                .append(zepto)
                .append(img_replace)
                .append(video).append("</head><body className=\"\"").append(autoLoad ? autoLoadImage : "").append(" >")
                .append(content);
        if (nightMode) {
            String night = "<script src=\"file:///android_asset/night.js\"></script>\n";
            htmlSb.append(night);
        }
        if (largeFont) {
            String bigFont = "<script src=\"file:///android_asset/large-font.js\"></script>\n";
            htmlSb.append(bigFont);
        }
        htmlSb.append("</body></html>");
        String html = htmlSb.toString();

        html = html.replace("<div class=\"img-place-holder\">", "");
        html = replaceImgTagFromHTML(html, autoLoad, nightMode);

        webContent.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }

    // ======================= js ========================
    @JavascriptInterface
    public void clickToLoadImage(final String imgPath) {
        if (TextUtils.isEmpty(imgPath))
            return;
        webContent.post(new Runnable() {
            @Override
            public void run() {
                Glide.with(BaseDetailActivity.this).load(imgPath)
                        .downloadOnly(new SimpleTarget<File>() {
                            @Override
                            public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                                String str = "file://" + resource.getAbsolutePath();//加载完成的图片地址
                                try {
                                    String[] arrayOfString = new String[2];
                                    arrayOfString[0] = URLEncoder.encode(imgPath, Charsets.UTF_8.name());//旧url
                                    arrayOfString[1] = str;
                                    onImageLoadingComplete("onImageLoadingComplete", arrayOfString);
                                } catch (Exception e) {

                                }
                            }
                        });
            }
        });
    }

    @JavascriptInterface
    public void loadImage(final String imgPath) {
        if (TextUtils.isEmpty(imgPath))
            return;
        webContent.post(new Runnable() {
            @Override
            public void run() {
                Glide.with(BaseDetailActivity.this).load(imgPath)
                        .downloadOnly(new SimpleTarget<File>() {
                            @Override
                            public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                                String str = "file://" + resource.getAbsolutePath();//加载完成的图片地址
                                try {
                                    String[] arrayOfString = new String[2];
                                    arrayOfString[0] = URLEncoder.encode(imgPath, Charsets.UTF_8.name());//旧url
                                    arrayOfString[1] = str;
                                    onImageLoadingComplete("onImageLoadingComplete", arrayOfString);
                                } catch (Exception e) {

                                }
                            }
                        });
            }
        });
    }

    @JavascriptInterface
    public void openImage(String imgPath) {
        ActivityUtils.toImageViewActivity(this, imgPath, imgUrlList);
    }

    public final void onImageLoadingComplete(String paramString, String[] paramArrayOfString) {
        String str = "'" + TextUtils.join("','", paramArrayOfString) + "'";
        webContent.loadUrl("javascript:" + paramString + "(" + str + ");");
    }
}
