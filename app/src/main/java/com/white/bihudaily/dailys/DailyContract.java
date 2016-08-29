package com.white.bihudaily.dailys;

/**
 * Author White
 * Date 2016/8/13
 * Time 12:25
 */

import android.content.Context;
import android.support.annotation.NonNull;

import com.white.bihudaily.BasePresenter;
import com.white.bihudaily.BaseView;
import com.white.bihudaily.bean.Latest;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.bean.TopStory;

import java.util.List;

/**
 * view 和 presenter 的联系类
 */
public interface DailyContract {

    interface View extends BaseView<Presenter> {

        void setRefreshLoadingIndicator(boolean active);

        void setMoreLoadingIndicator(boolean active);

        void showTopStories(List<TopStory> topStories);

        void showStories(List<Story> stories);

        void showStoryDetailsUi(Story story);

        void showLoadingLatestsError();

        void showNoStories();

        void addBefore(List<Story> stories);

        void showLoadingBeforeError();

        void showLatest(Latest latest);

        void setCurrentDate(String date);

        void showLoadMore(boolean active);

    }

    interface Presenter extends BasePresenter {

        void loadLatest(Context context, boolean getFromCache);

        void loadBefore(String date);

        void openStoryDetails(@NonNull Story item);

        void markReader(Context context, int id);

        List<Integer> getReaderList(Context context);


    }
}
