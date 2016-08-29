package com.white.bihudaily.detail;

import android.content.Context;

import com.white.bihudaily.BasePresenterImpl;
import com.white.bihudaily.base.BaseSubscriber;
import com.white.bihudaily.bean.DetailContent;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.bean.StoryExtra;
import com.white.bihudaily.data.DetailSource;
import com.white.bihudaily.utils.TransformUtils;

import java.util.List;

import rx.Subscription;

/**
 * Author White
 * Date 2016/8/14
 * Time 18:38
 */
public class DetailPresenter extends BasePresenterImpl<DetailSource, DetailContract.View> implements DetailContract.Presenter {


    public DetailPresenter(DetailSource source, DetailContract.View view) {
        super(source, view);
    }

    @Override
    public void loadDetailContent(int storyId) {
        mView.showLoading(true);
        Subscription subscription = mSource.loadDetailContent(storyId).compose(TransformUtils.<DetailContent>defaultSchedulers())
                .subscribe(new BaseSubscriber<DetailContent>() {
                    @Override
                    protected void onFailure(Throwable e) {
                        mView.showLoading(false);
                        mView.showLoadFail();
                    }

                    @Override
                    protected void onSuccess(DetailContent detailContent) {
                        mView.showLoading(false);
                        mView.showDetail(detailContent);
                    }
                });
        mSubscriptions.add(subscription);

    }

    @Override
    public void loadStoryExtra(int storyId) {
        Subscription subscription = mSource.loadStoryExtra(storyId).compose(TransformUtils.<StoryExtra>defaultSchedulers())
                .subscribe(new BaseSubscriber<StoryExtra>() {

                    @Override
                    protected void onFailure(Throwable e) {

                    }

                    @Override
                    protected void onSuccess(StoryExtra storyExtra) {
                        mView.showStoryExtra(storyExtra);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void addStar(Context context, Story story) {
        mSource.saveStarStory(context, story);
    }

    @Override
    public void removeStar(Context context, Story story) {
        mSource.removeStarStory(context, story);
    }

    @Override
    public List<Integer> getStarListId(Context context) {
        return mSource.getStarListId(context);
    }

    @Override
    public List<Integer> getLikeListId(Context context) {
        return mSource.getLikeListId(context);
    }

    @Override
    public void addLike(Context context, int storyId) {
        mSource.saveLikeId(context, storyId);
    }

    @Override
    public void removeLike(Context context, int storyId) {
        mSource.removeLikeId(context, storyId);
    }

}
