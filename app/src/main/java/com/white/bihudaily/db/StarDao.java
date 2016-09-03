package com.white.bihudaily.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.white.bihudaily.app.BihuDailyApplication;
import com.white.bihudaily.app.Constant;
import com.white.bihudaily.bean.Story;

import java.util.ArrayList;
import java.util.List;

/**
 * Author White
 * Date 2016/8/14
 * Time 22:26
 */
public class StarDao {
    private BihuDBHelper mBihuDBHelper;

    public StarDao() {
        mBihuDBHelper = new BihuDBHelper(BihuDailyApplication.getAppContext());
    }

    public boolean save(Story story) {
        SQLiteDatabase database = mBihuDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.ID, story.getId());
        contentValues.put(Constant.TITLE, story.getTitle());
        contentValues.put(Constant.IMAGE, story.getImages() == null ? null : story.getImages().get(0));
        contentValues.put(Constant.MULTI_PIC, story.isMultipic() ? 1 : 0);
        long insert = database.insert(Constant.TABLE_STAR, null, contentValues);
        database.close();
        return insert != -1;
    }

    public boolean delete(Story story) {
        SQLiteDatabase database = mBihuDBHelper.getWritableDatabase();
        int i = database.delete(Constant.TABLE_STAR, " id=? ", new String[]{story.getId() + ""});
        database.close();
        return i != 0;
    }

    public List<Story> getStarList() {
        List<Story> stories = new ArrayList<>(0);
        SQLiteDatabase database = mBihuDBHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.query(Constant.TABLE_STAR,
                    new String[]{Constant.ID, Constant.TITLE, Constant.IMAGE, Constant.MULTI_PIC},
                    null, null, null, null, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(Constant.ID));
                String title = cursor.getString(cursor.getColumnIndex(Constant.TITLE));
                String image = cursor.getString(cursor.getColumnIndex(Constant.IMAGE));
                int multiPic = cursor.getInt(cursor.getColumnIndex(Constant.MULTI_PIC));
                stories.add(new Story(id, title, image, null, multiPic == 1, false));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }

        return stories;
    }

    public List<Integer> getStarListId() {
        List<Integer> starListId = new ArrayList<>(0);
        SQLiteDatabase database = mBihuDBHelper.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.query(Constant.TABLE_STAR, new String[]{Constant.ID}, null, null, null, null, null);
            while (cursor.moveToNext()) {
                starListId.add(cursor.getInt(cursor.getColumnIndex(Constant.ID)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }

        return starListId;
    }
}
