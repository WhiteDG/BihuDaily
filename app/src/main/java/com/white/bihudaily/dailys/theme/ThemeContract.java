package com.white.bihudaily.dailys.theme;

/**
 * Author White
 * Date 2016/8/13
 * Time 12:25
 */

import android.content.Context;
import android.support.annotation.NonNull;

import com.white.bihudaily.BasePresenter;
import com.white.bihudaily.BaseView;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.bean.Theme;

import java.util.List;

/**
 * view 和 presenter 的联系类
 */
public interface ThemeContract {

    interface View extends BaseView<Presenter> {

        void setRefreshLoadingIndicator(boolean active);

        void setMoreLoadingIndicator(boolean active);

        void showStoryDetailsUi(Story story);

        void showEditorListUi(Theme theme);

        void showLoadingThemeError();

        void showNoTheme();

        void addBeforeTheme(List<Story> stories);

        void showLoadingBeforeError();

        void showTheme(Theme theme);

        void setLastStoryId(int lastStoryId);

        void showLoadMore(boolean active);

    }

    interface Presenter extends BasePresenter {


        void loadTheme(int id, Context context);

        void loadBeforeTheme(int themeId, int StoryId);

        void openStoryDetails(@NonNull Story item);

        void markReader(Context context, int id);

        List<Integer> getReaderList(Context context);

        void openEditorList(Theme theme);


    }
}
