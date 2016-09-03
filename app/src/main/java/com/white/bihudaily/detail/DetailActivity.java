package com.white.bihudaily.detail;

import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.white.bihudaily.R;
import com.white.bihudaily.bean.DetailContent;
import com.white.bihudaily.utils.ImageLoader;

import butterknife.BindView;

public class DetailActivity extends BaseDetailActivity {


    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbar;


    @BindView(R.id.iv_detail_top)
    ImageView ivDetailTop;

    @BindView(R.id.tv_imgSource)
    TextView tvImgSource;

    @BindView(R.id.tv_detail_title)
    TextView tvDetailTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void showDetail(DetailContent detailContent) {
        mDetailContent = detailContent;
        collapsingToolbar.setTitle(" ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        tvDetailTitle.setText(detailContent.getTitle());

        loadHtml(detailContent);

        if (detailContent.getImage() == null) {
            ivDetailTop.setImageResource(R.drawable.splash);
        } else {
            tvImgSource.setText(detailContent.getImage_source());
//            Glide.with(DetailActivity.this).load(detailContent.getImage()).centerCrop().fitCenter()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(ivDetailTop);
            ImageLoader.display(DetailActivity.this, ivDetailTop, detailContent.getImage());

        }
    }

}