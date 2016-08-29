package com.white.bihudaily.data.impl;

import android.content.Context;

import com.white.bihudaily.app.Constant;
import com.white.bihudaily.base.BaseRepository;
import com.white.bihudaily.bean.Latest;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.bean.TopStory;
import com.white.bihudaily.data.DailySource;
import com.white.bihudaily.db.ReaderDao;
import com.white.bihudaily.db.StarDao;
import com.white.bihudaily.db.StoryDao;
import com.white.bihudaily.utils.SPUtils;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Author White
 * Date 2016/8/13
 * Time 16:16
 */
public class DailyRepository extends BaseRepository implements DailySource {


    @Override
    public void saveCache(Context context, final List<Story> stories, final List<TopStory> top_stories) {
        StoryDao storyDao = new StoryDao(context);
        storyDao.saveStoryList(stories, top_stories);
    }

    @Override
    public Observable<Latest> getCache(Context context) {
        return Observable.just(context).map(new Func1<Context, Latest>() {

            @Override
            public Latest call(Context context) {
                StoryDao storyDao = new StoryDao(context);
                Latest latest = new Latest();
                latest.setStories(storyDao.getStoryList());
                latest.setTop_stories(storyDao.getTopStoryList());
                String date = (String) SPUtils.get(context, Constant.KEY_CURRENTDATE, "");
                latest.setDate(date);
                return latest;
            }
        });
    }

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
    public List<Integer> getStarListId(Context context) {
        StarDao starDao = new StarDao(context);
        return starDao.getStarListId();
    }



    @Override
    public Observable<Latest> loadLatest(final Context context) {
        return mBihuApi.getLatest().doOnNext(new Action1<Latest>() {
            @Override
            public void call(Latest latest) {
                saveCache(context, latest.getStories(), latest.getTop_stories());
                SPUtils.put(context, Constant.KEY_CURRENTDATE, latest.getDate());
            }
        });
//        rxRetrofit(latestObservable, subscriber);
    }


    @Override
    public Observable<Latest> loadBefore(String date) {
        return mBihuApi.getBefore(date);
    }


}
