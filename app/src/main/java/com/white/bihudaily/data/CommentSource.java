package com.white.bihudaily.data;

import com.white.bihudaily.base.BaseSource;
import com.white.bihudaily.bean.Comments;

import io.reactivex.Observable;


/**
 * Author White
 * Date 2016/8/13
 * Time 16:01
 */
public interface CommentSource extends BaseSource {

    Observable<Comments> loadBeforeComment(int storyId, int lastCommentId);

    Observable<Comments> loadLongComment(int storyId);

    Observable<Comments> loadShortComment(int storyId);
}
