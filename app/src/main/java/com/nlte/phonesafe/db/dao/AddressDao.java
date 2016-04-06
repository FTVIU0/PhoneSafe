package com.nlte.phonesafe.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**访问号码归属地工具类
 * Created by NLTE on 2016/4/6 0006.
 */
public class AddressDao {
    public static String queryAdress(Context context, String num){
        String location = "";
        SQLiteDatabase db = SQLiteDatabase.openDatabase(
                context.getFilesDir()+"/address.db",
                null,
                SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select location from data2 where  id in(select outkey from data1 where id = ?)",
                new String[]{num.substring(0, 7)});
        //遍历游标
        while (cursor.moveToNext()){
            location = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return location;
    }
}
