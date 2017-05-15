package com.white.bihudaily.api;

import com.white.bihudaily.bean.Comments;
import com.white.bihudaily.bean.DetailContent;
import com.white.bihudaily.bean.Latest;
import com.white.bihudaily.bean.StartImg;
import com.white.bihudaily.bean.StoryExtra;
import com.white.bihudaily.bean.Theme;
import com.white.bihudaily.bean.Themes;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Author White
 * Date 2016/8/13
 * Time 13:20
 */
public interface BihuApi {

    // 闪屏页图片
    @GET("start-image/1080*1776")
    Observable<StartImg> getStartImg();

    @Streaming
    @GET
    Observable<ResponseBody> downloadImage(@Url String imgUrl);


    // 今日热闻
    @GET("news/latest")
    Observable<Latest> getLatest();

    // 新闻详情
    @GET("story/{id}")
    Observable<DetailContent> getDetailContent(@Path("id") int id);

    // 加载更多(前一天新闻)
    @GET("news/before/{date}")
    Observable<Latest> getBefore(@Path("date") String date);

    // 新闻的附加数据(点赞数,评论数等)
    @GET("story-extra/{id}")
    Observable<StoryExtra> getStoreExtra(@Path("id") int id);

    // 获取新闻的长评论
    @GET("story/{id}/long-comments")
    Observable<Comments> getLongComments(@Path("id") int id);

    // 获取新闻的短评论
    @GET("story/{id}/short-comments")
    Observable<Comments> getShortComments(@Path("id") int id);

    // 短评论加载更多
    @GET("story/{story_id}/short-comments/before/{comment_id}")
    Observable<Comments> getBeforeShortComment(@Path("story_id") int storyId, @Path("comment_id") int lastCommentId);

    // 长评论加载更多
    @GET("story/{story_id}/long-comments/before/{comment_id}")
    Observable<Comments> getBeforeLongComment(@Path("story_id") int storyId, @Path("comment_id") int lastCommentId);

    // 获取主题列表
    @GET("themes")
    Observable<Themes> getThemes();

    // 获取某个主题日报的列表
    @GET("theme/{id}")
    Observable<Theme> getTheme(@Path("id") int id);

    // 某个主题日报加载更多
    @GET("theme/{theme_id}/before/{story_id}")
    Observable<Theme> getBeforeTheme(@Path("theme_id") int theme_id, @Path("story_id") int story_id);

}
