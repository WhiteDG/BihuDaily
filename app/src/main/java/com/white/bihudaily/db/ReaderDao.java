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
public class ReaderDao {
    private BihuDBHelper mBihuDBHelper;

    public ReaderDao(Context context) {
        mBihuDBHelper = new BihuDBHelper(context);
    }

    public void save(int id) {
        SQLiteDatabase database = mBihuDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.ID, id);
        database.insert(Constant.TABLE_READER, null, contentValues);
        database.close();
    }

    public List<Integer> getReaderList() {
        List<Integer> readerList = new ArrayList<>();
        SQLiteDatabase database = mBihuDBHelper.getReadableDatabase();
        Cursor query = null;
        try {
            database.beginTransaction();
            query = database.query(Constant.TABLE_READER, new String[]{Constant.ID}, null, null, null, null, null);
            while (query.moveToNext()) {
                readerList.add(query.getInt(query.getColumnIndex(Constant.ID)));
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
        return readerList;
    }
}
