package com.white.bihudaily.module.mystar;

import android.content.Context;
import android.support.annotation.NonNull;

import com.white.bihudaily.BasePresenter;
import com.white.bihudaily.BaseView;
import com.white.bihudaily.bean.Story;

import java.util.List;

/**
 * Author White
 * Date 2016/8/14
 * Time 18:36
 */
public interface MyStarContract {

    interface View extends BaseView<Presenter> {

        void setRefreshLoadingIndicator(boolean active);

        void showStoryDetailsUi(Story story);

        void showLoadingThemeError();

        void showNoTheme();

        void showStories(List<Story> stories);


    }

    interface Presenter extends BasePresenter {

        void loadStories(Context context);

        void openStoryDetails(@NonNull Story item);

        void markReader(Context context, int id);

        List<Integer> getReaderList(Context context);

    }
}
