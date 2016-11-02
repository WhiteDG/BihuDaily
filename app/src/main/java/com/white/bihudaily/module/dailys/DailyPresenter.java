package com.white.bihudaily.module.dailys;

import android.content.Context;
import android.support.annotation.NonNull;

import com.white.bihudaily.BasePresenterImpl;
import com.white.bihudaily.base.BaseSubscriber;
import com.white.bihudaily.bean.Latest;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.data.DailySource;
import com.white.bihudaily.utils.TransformUtils;

import java.util.List;

import rx.Subscription;

/**
 * Author White
 * Date 2016/8/13
 * Time 15:56
 */
public class DailyPresenter extends BasePresenterImpl<DailySource, DailyContract.View> implements DailyContract.Presenter {


    public DailyPresenter(DailySource dailySource, DailyContract.View dailysView) {
        super(dailySource, dailysView);
    }


    @Override
    public void loadLatest(final Context context, final boolean getFromCache) {
        if (getFromCache) {
            Subscription subscribe = mSource.getCache(context)
                    .compose(TransformUtils.<Latest>defaultSchedulers())
                    .subscribe(new BaseSubscriber<Latest>() {
                        @Override
                        protected void onFailure(Throwable e) {

                        }

                        @Override
                        protected void onSuccess(Latest latest) {
                            if (latest.getStories() != null && latest.getStories().size() > 0) {
                                mView.setCurrentDate(latest.getDate());
                                mView.showLatest(latest);
                            }
                        }
                    });
            mSubscriptions.add(subscribe);
            return;
        }
        mView.setRefreshLoadingIndicator(true);
        Subscription subscription = mSource.loadLatest(context).compose(TransformUtils.<Latest>defaultSchedulers())

                .subscribe(new BaseSubscriber<Latest>() {
                    @Override
                    protected void onFailure(Throwable e) {
                        mView.setRefreshLoadingIndicator(false);
                        mView.showLoadingLatestError();
                    }

                    @Override
                    protected void onSuccess(Latest latest) {
                        mView.setRefreshLoadingIndicator(false);
                        if (latest.getStories() == null || latest.getStories().size() == 0) {
                            mView.showNoStories();
                            return;
                        }
                        mView.setCurrentDate(latest.getDate());
                        mView.showLatest(latest);
                    }
                });
        mSubscriptions.add(subscription);

    }

    @Override
    public void loadBefore(String date) {
        mView.showLoadMore(true);
        Subscription subscription = mSource.loadBefore(date).compose(TransformUtils.<Latest>defaultSchedulers())
                .subscribe(new BaseSubscriber<Latest>() {
                    @Override
                    protected void onFailure(Throwable e) {
                        mView.showLoadMore(false);
                        mView.showLoadingBeforeError();
                    }

                    @Override
                    protected void onSuccess(Latest latest) {
                        mView.showLoadMore(false);
                        mView.setCurrentDate(latest.getDate());
                        mView.addBefore(latest.getStories());
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void openStoryDetails(@NonNull Story item) {
        mView.showStoryDetailsUi(item);
    }

    @Override
    public void markReader(Context context, int id) {
        mSource.saveReader(context, id);
    }

    @Override
    public List<Integer> getReaderList(Context context) {

        return mSource.getReader(context);
    }
}
