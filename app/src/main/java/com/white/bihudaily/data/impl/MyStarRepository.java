package com.white.bihudaily.data.impl;

import android.content.Context;

import com.white.bihudaily.base.BaseRepository;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.data.MyStarSource;
import com.white.bihudaily.db.ReaderDao;
import com.white.bihudaily.db.StarDao;

import java.util.List;

/**
 * Author White
 * Date 2016/8/13
 * Time 16:16
 */
public class MyStarRepository extends BaseRepository implements MyStarSource {


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
    public void removeStarStory(Context context, Story story) {
        StarDao starDao = new StarDao();
        starDao.delete(story);
    }

    @Override
    public List<Story> getStarList(Context context) {
        StarDao starDao = new StarDao();
        return starDao.getStarList();
    }

    @Override
    public List<Integer> getStarListId(Context context) {
        StarDao starDao = new StarDao();
        return starDao.getStarListId();
    }


}
