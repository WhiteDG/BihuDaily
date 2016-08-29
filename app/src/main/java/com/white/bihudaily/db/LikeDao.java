package com.white.bihudaily.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.white.bihudaily.app.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Author White
 * Date 2016/8/14
 * Time 22:26
 */
public class LikeDao {
    private BihuDBHelper mBihuDBHelper;

    public LikeDao(Context context) {
        mBihuDBHelper = new BihuDBHelper(context);
    }

    public void save(int id) {
        SQLiteDatabase database = mBihuDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.ID, id);
        database.insert(Constant.TABLE_LIKE, null, contentValues);
        database.close();
    }

    public void delete(int id) {
        SQLiteDatabase database = mBihuDBHelper.getWritableDatabase();
        database.delete(Constant.TABLE_LIKE, " id = ? ", new String[]{id + ""});
        database.close();
    }

    public List<Integer> getLikeListId() {
        List<Integer> likeList = new ArrayList<>();
        SQLiteDatabase database = mBihuDBHelper.getReadableDatabase();
        Cursor query = null;
        try {
            database.beginTransaction();
            query = database.query(Constant.TABLE_LIKE, new String[]{Constant.ID}, null, null, null, null, null);
            while (query.moveToNext()) {
                likeList.add(query.getInt(query.getColumnIndex(Constant.ID)));
            }
            database.setTransactionSuccessful();
            database.endTransaction();
        } catch (Exception e) {
            database.endTransaction();
        } finally {
            if (query != null) {
                query.close();
            }
            database.close();
        }
        return likeList;
    }
}
