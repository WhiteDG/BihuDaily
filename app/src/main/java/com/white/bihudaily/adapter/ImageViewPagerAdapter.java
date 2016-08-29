package com.white.bihudaily.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.white.bihudaily.R;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Author White
 * Date 2016/8/23
 * Time 11:29
 */
public class ImageViewPagerAdapter extends PagerAdapter {

    private List<String> mUrlList;

    public ImageViewPagerAdapter(List<String> urlList) {
        this.mUrlList = urlList;
    }

    @Override
    public int getCount() {
        return mUrlList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        Glide.with(container.getContext())
                .load(mUrlList.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .placeholder(R.drawable.default_pic_content_image_loading_light)
//                .error(R.drawable.default_pic_content_image_download_light)
                .into(photoView);
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
