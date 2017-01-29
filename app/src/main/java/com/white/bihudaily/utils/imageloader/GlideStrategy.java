package com.white.bihudaily.utils.imageloader;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * Author: Wh1te
 * Date: 2017-01-29
 */

public class GlideStrategy implements ImageLoaderStrategy {

    private static void loadImg(RequestManager requestManager, ImageView imageView, String url) {
        requestManager.load(url).fitCenter().centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    private static void loadCircularImg(RequestManager requestManager, final ImageView imageView, String url) {
        requestManager.load(url).asBitmap().fitCenter().centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(imageView.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    @Override
    public void display(Context context, ImageView imageView, String url) {
        loadImg(Glide.with(context), imageView, url);
    }

    @Override
    public void display(Activity activity, ImageView imageView, String url) {
        loadImg(Glide.with(activity), imageView, url);
    }

    @Override
    public void display(Fragment fragment, ImageView imageView, String url) {
        loadImg(Glide.with(fragment), imageView, url);
    }

    @Override
    public void display(android.support.v4.app.Fragment fragment, ImageView imageView, String url) {
        loadImg(Glide.with(fragment), imageView, url);
    }

    @Override
    public void displayCircularImg(Context context, ImageView imageView, String url) {
        loadCircularImg(Glide.with(context), imageView, url);
    }

    @Override
    public void displayCircularImg(Activity activity, ImageView imageView, String url) {
        loadCircularImg(Glide.with(activity), imageView, url);
    }

    @Override
    public void displayCircularImg(Fragment fragment, ImageView imageView, String url) {
        loadCircularImg(Glide.with(fragment), imageView, url);
    }

    @Override
    public void displayCircularImg(android.support.v4.app.Fragment fragment, ImageView imageView, String url) {
        loadCircularImg(Glide.with(fragment), imageView, url);
    }
}
