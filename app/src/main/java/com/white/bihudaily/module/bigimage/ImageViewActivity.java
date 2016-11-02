package com.white.bihudaily.module.bigimage;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.white.bihudaily.BasePresenter;
import com.white.bihudaily.R;
import com.white.bihudaily.adapter.ImageViewPagerAdapter;
import com.white.bihudaily.app.Constant;
import com.white.bihudaily.base.BaseWithToolbarActivity;
import com.white.bihudaily.other.HackyViewPager;

import java.util.List;

import butterknife.BindView;

public class ImageViewActivity extends BaseWithToolbarActivity {

//    @BindView(R.id.progress_big_image)
//    ProgressBar mProgressBar;
//    @BindView(R.id.iv_big_image)
//    PhotoView ivBigImage;

    @BindView(R.id.vp_image)
    HackyViewPager vpImage;
    @BindView(R.id.tv_current_page)
    TextView tvCurrentPage;

    private String imgUrl;
    private List<String> imgUrlList;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void beforeContentView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_view;
    }

    @Override
    protected void prepareData(Intent intent) {
        imgUrl = intent.getStringExtra(Constant.IMG_URL);
        imgUrlList = (List<String>) intent.getSerializableExtra(Constant.IMG_URL_LIST);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initView() {
        initToolbar("");
        mToolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    @Override
    protected void initListener() {
        vpImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tvCurrentPage.setText(String.format("%s/" + imgUrlList.size(), position + 1));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        vpImage.setAdapter(new ImageViewPagerAdapter(imgUrlList));
        vpImage.setCurrentItem(imgUrlList.indexOf(imgUrl));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_img:
                //保存图片

                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
