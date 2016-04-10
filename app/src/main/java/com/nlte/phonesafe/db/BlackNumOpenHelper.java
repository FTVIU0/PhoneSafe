package com.nlte.phonesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by NLTE on 2016/4/9 0009.
 */
public class BlackNumOpenHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "blacknum.db";
    private static final int version = 1;
    //创建数据库
    public BlackNumOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表t_info
        db.execSQL("create table t_info(id integer primary key autoincrement, num text, mode integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("onUpgrade");
    }
}
