package com.white.bihudaily.data.impl;

import android.content.Context;

import com.white.bihudaily.base.BaseRepository;
import com.white.bihudaily.bean.DetailContent;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.bean.StoryExtra;
import com.white.bihudaily.data.DetailSource;
import com.white.bihudaily.db.LikeDao;
import com.white.bihudaily.db.StarDao;

import java.util.List;

import io.reactivex.Observable;

/**
 * Author White
 * Date 2016/8/13
 * Time 16:16
 */
public class DetailRepository extends BaseRepository implements DetailSource {


    @Override
    public boolean saveStarStory(Context context, Story story) {
        StarDao starDao = new StarDao();
        return starDao.save(story);
    }

    @Override
    public boolean removeStarStory(Context context, Story story) {
        StarDao starDao = new StarDao();
        return starDao.delete(story);
    }

    @Override
    public List<Story> getStarList(Context context) {
        StarDao starDao = new StarDao();
        return starDao.getStarList();
    }

    @Override
    public void saveLikeId(Context context, int storyId) {
        LikeDao likeDao = new LikeDao(context);
        likeDao.save(storyId);
    }

    @Override
    public void removeLikeId(Context context, int storyId) {
        LikeDao likeDao = new LikeDao(context);
        likeDao.delete(storyId);
    }

    @Override
    public List<Integer> getStarListId(Context context) {
        StarDao starDao = new StarDao();
        return starDao.getStarListId();
    }

    @Override
    public List<Integer> getLikeListId(Context context) {
        LikeDao likeDao = new LikeDao(context);
        return likeDao.getLikeListId();
    }


    @Override
    public Observable<DetailContent> loadDetailContent(int id) {
        return mBihuApi.getDetailContent(id);
    }


    @Override
    public Observable<StoryExtra> loadStoryExtra(int storyId) {
        return mBihuApi.getStoreExtra(storyId);
    }
}
