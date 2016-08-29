package com.white.bihudaily.api;

import com.white.bihudaily.bean.Comments;
import com.white.bihudaily.bean.DetailContent;
import com.white.bihudaily.bean.Latest;
import com.white.bihudaily.bean.StartImg;
import com.white.bihudaily.bean.StoryExtra;
import com.white.bihudaily.bean.Theme;
import com.white.bihudaily.bean.Themes;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Author White
 * Date 2016/8/13
 * Time 13:20
 */
public interface BihuApi {

    @GET("start-image/1080*1776")
    Observable<StartImg> getStartImg();

    @GET("news/latest")
    Observable<Latest> getLatest();

    @GET("story/{id}")
    Observable<DetailContent> getDetailContent(@Path("id") int id);

    @GET("news/before/{date}")
    Observable<Latest> getBefore(@Path("date") String date);

    @GET("story-extra/{id}")
    Observable<StoryExtra> getStoreExtra(@Path("id") int id);

    @GET("story/{id}/long-comments")
    Observable<Comments> getLongComments(@Path("id") int id);

    @GET("story/{id}/short-comments")
    Observable<Comments> getShortComments(@Path("id") int id);

    @GET("themes")
    Observable<Themes> getThemes();

    @GET("theme/{id}")
    Observable<Theme> getTheme(@Path("id") int id);

    @GET("theme/{theme_id}/before/{story_id}")
    Observable<Theme> getBeforeTheme(@Path("theme_id") int theme_id, @Path("story_id") int story_id);

    @GET("story/{story_id}/short-comments/before/{comment_id}")
    Observable<Comments> getBeforeComment(@Path("story_id") int storyId, @Path("comment_id") int lastCommentId);
}
