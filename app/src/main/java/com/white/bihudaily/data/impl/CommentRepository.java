package com.white.bihudaily.data.impl;

import com.white.bihudaily.base.BaseRepository;
import com.white.bihudaily.base.BaseSubscriber;
import com.white.bihudaily.bean.Comments;
import com.white.bihudaily.data.CommentSource;

import rx.Observable;

/**
 * Author White
 * Date 2016/8/13
 * Time 16:16
 */
public class CommentRepository extends BaseRepository implements CommentSource {


    @Override
    public void loadBeforeComment(int storyId, int lastCommentId, BaseSubscriber<Comments> subscriber) {
        Observable<Comments> beforeCommentObservable = mBihuApi.getBeforeComment(storyId, lastCommentId);
    }


    @Override
    public Observable<Comments> loadLongComment(int storyId) {
        return mBihuApi.getLongComments(storyId);
    }


    @Override
    public Observable<Comments> loadShortComment(int storyId) {
        return mBihuApi.getShortComments(storyId);
    }
}
