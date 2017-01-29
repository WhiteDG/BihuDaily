package com.white.bihudaily.utils.imageloader;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.widget.ImageView;

/**
 * Author: Wh1te
 * Date: 2017-01-29
 */

public interface ImageLoaderStrategy {

    void display(Context context, ImageView imageView, String url);

    void display(Activity activity, ImageView imageView, String url);

    void display(Fragment fragment, ImageView imageView, String url);

    void display(android.support.v4.app.Fragment fragment, ImageView imageView, String url);

    void displayCircularImg(Context context, ImageView imageView, String url);

    void displayCircularImg(Activity activity, ImageView imageView, String url);

    void displayCircularImg(Fragment fragment, ImageView imageView, String url);

    void displayCircularImg(android.support.v4.app.Fragment fragment, ImageView imageView, String url);
}
