package com.white.bihudaily.data;

import android.content.Context;

import com.white.bihudaily.base.BaseSource;
import com.white.bihudaily.bean.Theme;

import java.util.List;

import rx.Observable;

/**
 * Author White
 * Date 2016/8/13
 * Time 16:01
 */
public interface ThemeSource extends BaseSource {

    void saveReader(Context context, int id);

    List<Integer> getReader(Context context);



    Observable<Theme> loadTheme(int id);


    Observable<Theme> loadBeforeTheme(int themeId, int storyId);

}
