package com.white.bihudaily.base;

import com.white.bihudaily.api.BihuApi;
import com.white.bihudaily.api.BihuClient;

/**
 * Author White
 * Date 2016/8/14
 * Time 12:29
 */
public class BaseRepository implements BaseSource {

//    protected CompositeSubscription mCompositeSubscription;
    protected BihuApi mBihuApi = BihuClient.getBihuApi();


    /**
     * RxJava+Retrofit 获取数据
     *
     * @param observable
     * @param subscriber
     */
//    protected void rxRetrofit(Observable observable, Subscriber subscriber) {
//        Subscription subscribe = observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//        addSubscribe(subscribe);
//    }

//    protected void addSubscribe(Subscription subscribe) {
//        if (mCompositeSubscription == null) {
//            mCompositeSubscription = new CompositeSubscription();
//        }
//        mCompositeSubscription.add(subscribe);
//    }
//
//    @Override
//    public void unSubscribe() {
//        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
//            mCompositeSubscription.unsubscribe();
//            mCompositeSubscription = null;
//        }
//    }
}
