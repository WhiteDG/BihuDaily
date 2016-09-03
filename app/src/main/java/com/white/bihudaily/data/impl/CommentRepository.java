package com.white.bihudaily.data.impl;

import com.white.bihudaily.base.BaseRepository;
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
    public Observable<Comments> loadBeforeComment(int storyId, int lastCommentId) {
        return mBihuApi.getBeforeShortComment(storyId, lastCommentId);
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
