package com.white.bihudaily.data.impl;

import android.content.Context;

import com.white.bihudaily.app.BihuDailyApplication;
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

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Author White
 * Date 2016/8/13
 * Time 16:16
 */
public class DailyRepository extends BaseRepository implements DailySource {


    Consumer<Latest> mConsumer = new Consumer<Latest>() {
        @Override
        public void accept(Latest latest) throws Exception {
            // 标记已读
            List<Integer> reader = getReader(BihuDailyApplication.getAppContext());
            List<Story> stories = latest.getStories();
            for (Story story : stories) {
                story.setRead(reader.contains(story.getId()));
                if (story.getImages() == null || story.getImages().size() == 0) {
                    story.setShowType(Story.TYPE_NO_IMG_STORY);
                }
            }
        }
    };

    @Override
    public void saveCache(Context context, final List<Story> stories, final List<TopStory> top_stories) {
        StoryDao storyDao = new StoryDao();
        storyDao.saveStoryList(stories, top_stories);
    }

    @Override
    public Observable<Latest> getCache(Context context) {
        return Observable.just(context).map(new Function<Context, Latest>() {
            @Override
            public Latest apply(Context context) throws Exception {
                StoryDao storyDao = new StoryDao();
                Latest latest = new Latest();
                latest.setStories(storyDao.getStoryList());
                latest.setTop_stories(storyDao.getTopStoryList());
                String date = (String) SPUtils.get(context, Constant.KEY_CURRENT_DATE, "");
                latest.setDate(date);
                return latest;
            }
        }).doOnNext(mConsumer);
//        return Observable.just(context).map(new Func1<Context, Latest>() {
//
//            @Override
//            public Latest call(Context context) {
//                StoryDao storyDao = new StoryDao();
//                Latest latest = new Latest();
//                latest.setStories(storyDao.getStoryList());
//                latest.setTop_stories(storyDao.getTopStoryList());
//                String date = (String) SPUtils.get(context, Constant.KEY_CURRENT_DATE, "");
//                latest.setDate(date);
//                return latest;
//            }
//        }).doOnNext(mAction1);
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
        StarDao starDao = new StarDao();
        return starDao.getStarListId();
    }


    @Override
    public Observable<Latest> loadLatest(final Context context) {
        return mBihuApi.getLatest().doOnNext(new Consumer<Latest>() {
            @Override
            public void accept(Latest latest) throws Exception {
                // 缓存第一页数据
                saveCache(context, latest.getStories(), latest.getTop_stories());
                SPUtils.put(context, Constant.KEY_CURRENT_DATE, latest.getDate());
                SPUtils.put(context, Constant.KEY_HAS_CACHE, true);
            }
        }).doOnNext(mConsumer);
    }

    @Override
    public Observable<Latest> loadBefore(String date) {
        return mBihuApi.getBefore(date)
                .doOnNext(mConsumer);
    }


}
