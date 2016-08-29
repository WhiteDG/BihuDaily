package com.white.bihudaily;

import android.view.View;

/**
 * Author White
 * Date 2016/8/12
 * Time 20:21
 */
public interface BaseView<T> {

    void setPresenter(T presenter);


    void showToast(String msg);

    void showToast(int msgId);

    void showSnackBar(View view, String msg);

    void showSnackBar(View view, int msg);
}
