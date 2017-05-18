package com.white.bihudaily.module.comment;

import com.white.bihudaily.BasePresenterImpl;
import com.white.bihudaily.R;
import com.white.bihudaily.base.BaseSubscriber;
import com.white.bihudaily.bean.Comment;
import com.white.bihudaily.bean.Comments;
import com.white.bihudaily.data.CommentSource;
import com.white.bihudaily.utils.TransformUtils;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Author White
 * Date 2016/8/18
 * Time 13:18
 */
public class CommentPresenter extends BasePresenterImpl<CommentSource, CommentContract.View> implements CommentContract.Presenter {


    public CommentPresenter(CommentSource source, CommentContract.View view) {
        super(source, view);
    }

    @Override
    public void loadLongComment(int storyId) {
        mView.showLoading(true);
        mSource.loadLongComment(storyId).compose(TransformUtils.<Comments>defaultSchedulers())
                .subscribe(new BaseSubscriber<Comments>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mSubscriptions.add(d);
                    }

                    @Override
                    protected void onFailure(Throwable e) {
                        mView.showLoading(false);
                        mView.showToast("获取长评论列表失败");
                    }

                    @Override
                    protected void onSuccess(Comments comments) {
                        mView.showLoading(false);
                        mView.showLongComment(comments);
                    }
                });
    }

    @Override
    public void loadShortComment(int storyId) {
        mView.showLoading(true);
        mSource.loadShortComment(storyId).compose(TransformUtils.<Comments>defaultSchedulers())
                .subscribe(new BaseSubscriber<Comments>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mSubscriptions.add(d);
                    }

                    @Override
                    protected void onFailure(Throwable e) {
                        mView.showLoading(false);
                        mView.showToast("获取短评论列表失败");
                    }

                    @Override
                    protected void onSuccess(Comments comments) {
                        mView.showLoading(false);
                        List<Comment> beforeCommentList = comments.getComments();
                        if (beforeCommentList != null && beforeCommentList.size() != 0) {
                            mView.setLastCommentId(beforeCommentList.get(beforeCommentList.size() - 1).getId());
                            mView.showShortComment(comments);
                        } else {
                            mView.showToast("暂无短评");
                        }
                    }
                });
    }

    @Override
    public void loadBeforeShortComment(int storyId, int lastCommentId) {
        mView.showLoadMore(true);
        mSource.loadBeforeComment(storyId, lastCommentId).compose(TransformUtils.<Comments>defaultSchedulers())
                .subscribe(new BaseSubscriber<Comments>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mSubscriptions.add(d);
                    }

                    @Override
                    protected void onFailure(Throwable e) {
                        mView.showLoadMore(false);
                        mView.showToast(R.string.load_fail);
                    }

                    @Override
                    protected void onSuccess(Comments comments) {
                        mView.showLoadMore(false);
                        List<Comment> beforeCommentList = comments.getComments();
                        if (beforeCommentList != null && beforeCommentList.size() != 0) {
                            mView.setLastCommentId(beforeCommentList.get(beforeCommentList.size() - 1).getId());
                            mView.showBeforeComment(beforeCommentList);
                        } else {
                            mView.showNoMoreData();
                        }
                    }
                });

    }

}
