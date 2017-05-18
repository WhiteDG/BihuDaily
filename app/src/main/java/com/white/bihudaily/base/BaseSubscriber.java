package com.white.bihudaily.base;

import io.reactivex.Observer;

/**
 * Author White
 * Date 2016/8/16
 * Time 23:25
 */
public abstract class BaseSubscriber<T> implements Observer<T> {

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        onFailure(e);
    }

    protected abstract void onFailure(Throwable e);

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    protected abstract void onSuccess(T t);
}
