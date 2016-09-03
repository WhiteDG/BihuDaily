package com.white.bihudaily.dailys.theme;

import android.content.Context;
import android.support.annotation.NonNull;

import com.white.bihudaily.BasePresenterImpl;
import com.white.bihudaily.base.BaseSubscriber;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.bean.Theme;
import com.white.bihudaily.data.ThemeSource;
import com.white.bihudaily.utils.TransformUtils;

import java.util.List;

import rx.Subscription;

/**
 * Author White
 * Date 2016/8/16
 * Time 13:58
 */
public class ThemePresenter extends BasePresenterImpl<ThemeSource, ThemeContract.View> implements ThemeContract.Presenter {

    public ThemePresenter(ThemeSource source, ThemeContract.View view) {
        super(source, view);
    }

    @Override
    public void loadTheme(int id, final Context context) {
        mView.setRefreshLoadingIndicator(true);
        Subscription subscription = mSource.loadTheme(id)
                .compose(TransformUtils.<Theme>defaultSchedulers())
                .subscribe(new BaseSubscriber<Theme>() {
                    @Override
                    protected void onFailure(Throwable e) {
                        mView.setRefreshLoadingIndicator(false);
                        mView.showLoadingThemeError();
                    }

                    @Override
                    protected void onSuccess(Theme theme) {
                        mView.setRefreshLoadingIndicator(false);
                        List<Story> stories = theme.getStories();
                        mView.setLastStoryId(stories.get(stories.size() - 1).getId());
                        mView.showTheme(theme);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void loadBeforeTheme(int themeId, int storyId) {
        mView.showLoadMore(true);
        Subscription subscribe = mSource.loadBeforeTheme(themeId, storyId).compose(TransformUtils.<Theme>defaultSchedulers())
                .subscribe(new BaseSubscriber<Theme>() {
                    @Override
                    protected void onFailure(Throwable e) {
                        mView.showLoadMore(false);
                        mView.showLoadingBeforeError();
                    }

                    @Override
                    protected void onSuccess(Theme theme) {
                        mView.showLoadMore(false);
                        List<Story> stories = theme.getStories();
                        mView.setLastStoryId(stories.get(stories.size() - 1).getId());
                        mView.addBeforeTheme(stories);
                    }
                });
        mSubscriptions.add(subscribe);
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

    @Override
    public void openEditorList(Theme theme) {
        mView.showEditorListUi(theme);
    }

}
