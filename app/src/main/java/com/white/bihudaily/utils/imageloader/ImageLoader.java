package com.white.bihudaily.utils.imageloader;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.widget.ImageView;

/**
 * Author: Wh1te
 * Date: 2017-01-29
 */

public class ImageLoader {

    private ImageLoaderStrategy mStrategy;
    private static ImageLoader mInstance;

    private ImageLoader() {
        mStrategy = new GlideStrategy();
    }

    public static ImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    public void setStrategy(ImageLoaderStrategy strategy) {
        this.mStrategy = strategy;
    }

    public void display(Context context, ImageView imageView, String url) {
        mStrategy.display(context, imageView, url);
    }

    public void display(Activity activity, ImageView imageView, String url) {
        mStrategy.display(activity, imageView, url);
    }

    public void display(Fragment fragment, ImageView imageView, String url) {
        mStrategy.display(fragment, imageView, url);
    }

    public void display(android.support.v4.app.Fragment fragment, ImageView imageView, String url) {
        mStrategy.display(fragment, imageView, url);
    }

    public void displayCircularImg(Context context, ImageView imageView, String url) {
        mStrategy.displayCircularImg(context, imageView, url);
    }

    public void displayCircularImg(Activity activity, ImageView imageView, String url) {
        mStrategy.displayCircularImg(activity, imageView, url);
    }

    public void displayCircularImg(Fragment fragment, ImageView imageView, String url) {
        mStrategy.displayCircularImg(fragment, imageView, url);
    }

    public void displayCircularImg(android.support.v4.app.Fragment fragment, ImageView imageView, String url) {
        mStrategy.displayCircularImg(fragment, imageView, url);
    }
}
