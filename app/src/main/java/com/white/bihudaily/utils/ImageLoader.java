package com.white.bihudaily.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.white.bihudaily.app.BihuDailyApplication;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Author White
 * Date 2016/8/16
 * Time 22:00
 */
public class ImageLoader {

    private static void loadImg(RequestManager requestManager, String url, ImageView imageView) {
        requestManager.load(url).fitCenter().centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    private static void loadCircularImg(RequestManager requestManager, String url, final ImageView imageView) {
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

    public static void display(Activity activity, ImageView imageView, String url) {
        loadImg(Glide.with(activity), url, imageView);
    }

    public static void displayCircularImg(Fragment fragment, ImageView imageView, String url) {
        loadCircularImg(Glide.with(fragment), url, imageView);
    }

    public static void displayCircularImg(Activity activity, ImageView imageView, String url) {
        loadCircularImg(Glide.with(activity), url, imageView);
    }

    public static void display(Fragment fragment, ImageView imageView, String url) {
        loadImg(Glide.with(fragment), url, imageView);
    }

    public static void display(Context context, ImageView imageView, String url) {
        loadImg(Glide.with(context), url, imageView);
    }

    public static void downloadImg(String url) {
        try {
            File img = Glide.with(BihuDailyApplication.getAppContext()).load(url)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
