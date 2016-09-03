package com.white.bihudaily.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.white.bihudaily.app.BihuDailyApplication;
import com.white.bihudaily.app.Constant;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.bean.TopStory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author White
 * Date 2016/8/14
 * Time 16:20
 */
public class StoryDao {

    private BihuDBHelper mBihuDBHelper;

    public StoryDao() {
        mBihuDBHelper = new BihuDBHelper(BihuDailyApplication.getAppContext());
    }

    public List<TopStory> getTopStoryList() {
        List<TopStory> stories = new ArrayList<>();
        SQLiteDatabase database = mBihuDBHelper.getReadableDatabase();
        Cursor query = null;
        try {
            query = database.query(Constant.TABLE_STORY,
                    new String[]{Constant.ID, Constant.TITLE, Constant.IMAGE, Constant.DATE, Constant.TOP},
                    " top=? ", new String[]{"1"}, null, null, null);
            while (query.moveToNext()) {
                int id = query.getInt(query.getColumnIndex(Constant.ID));
                String title = query.getString(query.getColumnIndex(Constant.TITLE));
                String image = query.getString(query.getColumnIndex(Constant.IMAGE));
                stories.add(new TopStory(id, title, image));
            }
            return stories;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (query != null) {
                query.close();
            }
            database.close();
        }
        return Collections.emptyList();
    }

    public List<Story> getStoryList() {
        List<Story> stories = new ArrayList<>();
        SQLiteDatabase database = mBihuDBHelper.getReadableDatabase();
        Cursor query = null;
        try {
            query = database.query(Constant.TABLE_STORY,
                    new String[]{Constant.ID, Constant.TITLE, Constant.IMAGE, Constant.DATE, Constant.TOP, Constant.MULTI_PIC, Constant.READ},
                    " top=? ", new String[]{"0"}, null, null, null);
            while (query.moveToNext()) {
                int id = query.getInt(query.getColumnIndex(Constant.ID));
                String title = query.getString(query.getColumnIndex(Constant.TITLE));
                String image = query.getString(query.getColumnIndex(Constant.IMAGE));
                String date = query.getString(query.getColumnIndex(Constant.DATE));
                int multiPic = query.getInt(query.getColumnIndex(Constant.MULTI_PIC));
                int read = query.getInt(query.getColumnIndex(Constant.READ));
                stories.add(new Story(id, title, image, date, multiPic == 1, read == 1));
            }
            return stories;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (query != null) {
                query.close();
            }
            database.close();
        }
        return Collections.emptyList();
    }

    public void saveStoryList(List<Story> stories, List<TopStory> top_stories) {
        SQLiteDatabase database = mBihuDBHelper.getWritableDatabase();
        try {
            database.beginTransaction();
            database.delete(Constant.TABLE_STORY, "1=1", null);
            for (TopStory topStory : top_stories) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(Constant.ID, topStory.getId());
                contentValues.put(Constant.TITLE, topStory.getTitle());
                contentValues.put(Constant.IMAGE, topStory.getImage());
                contentValues.put(Constant.TOP, 1);
                long insert = database.insert(Constant.TABLE_STORY, null, contentValues);
                if (insert <= 0) {// 出现错误
                    database.endTransaction();
                }
            }
            for (Story story : stories) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(Constant.ID, story.getId());
                contentValues.put(Constant.TITLE, story.getTitle());
                contentValues.put(Constant.IMAGE, story.getImages().get(0));
                contentValues.put(Constant.DATE, story.getDate());
                contentValues.put(Constant.TOP, 0);
                contentValues.put(Constant.MULTI_PIC, story.isMultipic() ? 1 : 0);
                contentValues.put(Constant.READ, story.isRead() ? 1 : 0);
                long insert = database.insert(Constant.TABLE_STORY, null, contentValues);
                if (insert <= 0) {// 出现错误
                    database.endTransaction();
                }
            }
            database.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            database.close();
        }
    }
}
