package com.white.bihudaily.data.impl;

import android.content.Context;

import com.white.bihudaily.base.BaseRepository;
import com.white.bihudaily.bean.Theme;
import com.white.bihudaily.data.ThemeSource;
import com.white.bihudaily.db.ReaderDao;

import java.util.List;

import rx.Observable;

/**
 * Author White
 * Date 2016/8/13
 * Time 16:16
 */
public class ThemeRepository extends BaseRepository implements ThemeSource {

    @Override
    public void saveReader(Context context, int id) {
        ReaderDao readerDao = new ReaderDao(context);
        readerDao.save(id);
    }

    @Override
    public List<Integer> getReader(Context context) {
        ReaderDao readerDao = new ReaderDao(context);
        return readerDao.getReaderList();
    }



    @Override
    public Observable<Theme> loadTheme(int id) {
        return mBihuApi.getTheme(id);
    }


    @Override
    public Observable<Theme> loadBeforeTheme(int themeId, int storyId) {
        return mBihuApi.getBeforeTheme(themeId, storyId);
    }

}
