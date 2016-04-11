package com.nlte.phonesafe.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;

import com.nlte.phonesafe.db.BlackNumOpenHelper;
import com.nlte.phonesafe.entity.BlackNuminfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 黑名单访问数据库的工具类
 * Created by NLTE on 2016/4/9 0009.
 */
public class BlackNumDao {
    public static final String TABLE = "t_info";
    public static final String NUM = "num";
    public static final String MODE = "mode";

    //访问方式的设置
    public static final int CALL = 0;
    public static final int SMS = 1;
    public static final int ALL = 2;
    private BlackNumOpenHelper helper;

    public BlackNumDao(Context context) {
        helper = new BlackNumOpenHelper(context);
    }

    /**
     * 添加
     * @param num 电话号码
     * @param mode 拦截方式 1.电话拦截 2.短信拦截 3.ALl
     */
    public void add(String num, int mode){
        SQLiteDatabase db = helper.getWritableDatabase();//加了锁 ，比getReadableDatabase 要慢一些
        ContentValues values = new ContentValues();
        values.put(NUM, num);
        values.put(MODE, mode);
        db.insert(TABLE, null, values);
        db.close();
    }

    /**
     * 删除黑名单，传递的是号码
     * @param num 电话号码
     */
    public void delete(String num){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE, NUM+"=?", new String[]{num});
        db.close();
    }
    //查询某个黑名单的拦截方式
    public int queryBlackNumMode(String num){
        int mode = -1;
        SQLiteDatabase db = helper.getReadableDatabase();//没有加锁，速度较快
        Cursor cursor = db.query(TABLE, new String[]{MODE}, NUM+"=?", new String[]{NUM}, null, null, null);
        if (cursor.moveToNext()){
            mode = cursor.getInt(cursor.getColumnIndex(MODE));
        }
        cursor.close();
        db.close();
        return mode;
    }

    //更新黑名单的拦截方式
    public void updateBlackNumMode(String num, int mode){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MODE, mode);
        db.update(TABLE, values, NUM+"=?", new String[]{num});
        db.close();
    }

    //查询所有的黑名单
    public List<BlackNuminfo> getAllBlackNums() {
        SystemClock.sleep(2000);//休眠3秒，模拟延时加载
        List<BlackNuminfo> data = null;
        SQLiteDatabase db = helper.getReadableDatabase();//没有加锁，速度较快
        Cursor cursor = db.query(TABLE, new String[]{NUM, MODE}, null, null, null, null, null);
        if (cursor.getCount()>0){
            data = new ArrayList<BlackNuminfo>();
        }
        while (cursor.moveToNext()){
            String num = cursor.getString(0);
            int mode = cursor.getInt(1);
            BlackNuminfo blackNuminfo = new BlackNuminfo(num, mode);
            data.add(blackNuminfo);
        }
        cursor.close();
        db.close();
        return data;
    }
}
