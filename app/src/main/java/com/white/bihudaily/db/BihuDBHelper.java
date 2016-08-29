package com.white.bihudaily.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.white.bihudaily.app.Constant;

/**
 * Author White
 * Date 2016/8/14
 * Time 16:13
 */
public class BihuDBHelper extends SQLiteOpenHelper {


    public BihuDBHelper(Context context) {
        super(context, Constant.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createStory = "CREATE TABLE "
                + Constant.TABLE_STORY
                + "("
                + Constant.UID + " integer primary key autoincrement,"
                + Constant.ID + " int,"
                + Constant.TITLE + " text,"
                + Constant.IMAGE + " text,"
                + Constant.DATE + " text,"
                + Constant.MULTIPIC + " int,"
                + Constant.TOP + " int"
                + ")";
        String createReader = "CREATE TABLE "
                + Constant.TABLE_READER
                + "("
                + Constant.UID + " integer primary key autoincrement,"
                + Constant.ID + " int"
                + ")";
        String createLike = "CREATE TABLE "
                + Constant.TABLE_LIKE
                + "("
                + Constant.UID + " integer primary key autoincrement,"
                + Constant.ID + " int"
                + ")";
        String createStar = "CREATE TABLE "
                + Constant.TABLE_STAR
                + "("
                + Constant.UID + " integer primary key autoincrement,"
                + Constant.ID + " int,"
                + Constant.TITLE + " text,"
                + Constant.IMAGE + " text,"
                + Constant.MULTIPIC + " int"
                + ")";
        sqLiteDatabase.execSQL(createStory);
        sqLiteDatabase.execSQL(createReader);
        sqLiteDatabase.execSQL(createStar);
        sqLiteDatabase.execSQL(createLike);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
