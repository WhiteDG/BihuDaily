package com.white.bihudaily;

import com.white.bihudaily.base.BaseSource;

import rx.subscriptions.CompositeSubscription;

/**
 * Author White
 * Date 2016/8/20
 * Time 20:56
 */
public class BasePresenterImpl<S extends BaseSource, V extends BaseView> implements BasePresenter {

    protected final S mSource;
    protected V mView;

    protected CompositeSubscription mSubscriptions;

    public BasePresenterImpl(S source, V view) {
        this.mView = view;
        this.mSource = source;
        mView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void start() {

    }

    @Override
    public void unSubscribe() {
//        if (mSource != null) {
//            mSource.unSubscribe();
//        }
        if (mSubscriptions != null && mSubscriptions.hasSubscriptions()) {
            mSubscriptions.unsubscribe();
            mSubscriptions.clear();
        }
    }
}
