package com.white.bihudaily.data;

import com.white.bihudaily.base.BaseSource;
import com.white.bihudaily.base.BaseSubscriber;
import com.white.bihudaily.bean.Comments;

import rx.Observable;

/**
 * Author White
 * Date 2016/8/13
 * Time 16:01
 */
public interface CommentSource extends BaseSource {

    void loadBeforeComment(int storyId, int lastCommentId, BaseSubscriber<Comments> subscriber);


    Observable<Comments> loadLongComment(int storyId);


    Observable<Comments> loadShortComment(int storyId);
}
