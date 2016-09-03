package com.white.bihudaily.comment;

import com.white.bihudaily.BasePresenter;
import com.white.bihudaily.BaseView;
import com.white.bihudaily.bean.Comment;
import com.white.bihudaily.bean.Comments;

import java.util.List;

/**
 * Author White
 * Date 2016/8/14
 * Time 18:36
 */
public interface CommentContract {

    interface View extends BaseView<Presenter> {

        void showLoading(boolean isShow);

        void showLoadFail();

        void showLongComment(Comments comments);

        void showShortComment(Comments comments);

        void setLastCommentId(int lastCommentId);

        void showLoadMore(boolean active);

        void showBeforeComment(List<Comment> beforeCommentList);

        void showNoMoreData();
    }

    interface Presenter extends BasePresenter {
        void loadLongComment(int storyId);

        void loadShortComment(int storyId);

        void loadBeforeShortComment(int storyId, int lastCommentId);
    }
}
