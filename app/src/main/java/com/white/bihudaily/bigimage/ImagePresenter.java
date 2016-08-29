package com.white.bihudaily.bigimage;

import com.white.bihudaily.BasePresenterImpl;
import com.white.bihudaily.data.BigImageSource;

/**
 * Author White
 * Date 2016/8/22
 * Time 20:09
 */
public class ImagePresenter extends BasePresenterImpl<BigImageSource, ImageContract.View> implements ImageContract.Presenter {


    public ImagePresenter(BigImageSource source, ImageContract.View view) {
        super(source, view);
    }

    @Override
    public void loadBigImage(String url) {
        mView.showLoading(true);

        mView.showLoading(false);
    }

    @Override
    public void downloadBigImage(String url) {

    }

}
