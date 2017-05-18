package com.white.bihudaily;

import com.white.bihudaily.base.BaseSource;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Author White
 * Date 2016/8/20
 * Time 20:56
 */
public class BasePresenterImpl<S extends BaseSource, V extends BaseView> implements BasePresenter {

    protected final S mSource;
    protected V mView;

    protected CompositeDisposable mSubscriptions;

    public BasePresenterImpl(S source, V view) {
        this.mView = view;
        this.mSource = source;
        mView.setPresenter(this);
        mSubscriptions = new CompositeDisposable();
    }

    @Override
    public void start() {

    }

    @Override
    public void unSubscribe() {
        if (mSubscriptions != null) {
            mSubscriptions.dispose();
            mSubscriptions.clear();
        }
    }
}
