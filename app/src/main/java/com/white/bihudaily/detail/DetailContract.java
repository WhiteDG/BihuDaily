package com.white.bihudaily.detail;

import android.content.Context;

import com.white.bihudaily.BasePresenter;
import com.white.bihudaily.BaseView;
import com.white.bihudaily.bean.DetailContent;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.bean.StoryExtra;

import java.util.List;

/**
 * Author White
 * Date 2016/8/14
 * Time 18:36
 */
public interface DetailContract {

    interface View extends BaseView<Presenter> {

        void showLoading(boolean isShow);

        void showLoadFail();

        void showDetail(DetailContent detailContent);

        void showStoryExtra(StoryExtra storyExtra);

        void setStarState(boolean isStar);

        void showAddStarFail();

        void showRemoveStarFail();
    }

    interface Presenter extends BasePresenter {
        void loadDetailContent(int storyId);

        void loadStoryExtra(int storyId);

        void addStar(Context context, Story story);

        void removeStar(Context context, Story story);

        List<Integer> getStarListId(Context context);

        List<Integer> getLikeListId(Context context);

        void addLike(Context context, int storyId);

        void removeLike(Context context, int storyId);
    }
}
