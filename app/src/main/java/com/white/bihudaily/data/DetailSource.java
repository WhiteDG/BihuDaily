package com.white.bihudaily.data;

import android.content.Context;

import com.white.bihudaily.base.BaseSource;
import com.white.bihudaily.bean.DetailContent;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.bean.StoryExtra;

import java.util.List;

import rx.Observable;

/**
 * Author White
 * Date 2016/8/13
 * Time 16:01
 */
public interface DetailSource extends BaseSource {

    void saveStarStory(Context context, Story story);

    void removeStarStory(Context context, Story story);

    List<Story> getStarList(Context context);

    List<Integer> getStarListId(Context context);

    void saveLikeId(Context context, int storyId);

    void removeLikeId(Context context, int storyId);

    List<Integer> getLikeListId(Context context);


    Observable<DetailContent> loadDetailContent(int id);


    Observable<StoryExtra> loadStoryExtra(int storyId);


}
