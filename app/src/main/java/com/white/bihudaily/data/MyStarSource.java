package com.white.bihudaily.data;

import android.content.Context;

import com.white.bihudaily.base.BaseSource;
import com.white.bihudaily.bean.Story;

import java.util.List;

/**
 * Author White
 * Date 2016/8/13
 * Time 16:01
 */
public interface MyStarSource extends BaseSource {


    void saveReader(Context context, int id);

    List<Integer> getReader(Context context);


    void removeStarStory(Context context, Story story);

    List<Story> getStarList(Context context);

    List<Integer> getStarListId(Context context);


}
