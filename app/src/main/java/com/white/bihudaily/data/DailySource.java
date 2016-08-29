package com.white.bihudaily.data;

import android.content.Context;

import com.white.bihudaily.base.BaseSource;
import com.white.bihudaily.bean.Latest;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.bean.TopStory;

import java.util.List;

import rx.Observable;

/**
 * Author White
 * Date 2016/8/13
 * Time 16:01
 */
public interface DailySource extends BaseSource {


    void saveCache(Context context, List<Story> stories, List<TopStory> top_stories);


    Observable<Latest> getCache(Context context);

    void saveReader(Context context, int id);

    List<Integer> getReader(Context context);


    List<Integer> getStarListId(Context context);



    Observable<Latest> loadLatest(Context context);


    Observable<Latest> loadBefore(String date);

}
