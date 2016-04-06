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
        /*通过SQLiteDatabase打开数据库
         * OPEN_READONLY：只读方式
         * path：要访问的数据库文件路径: /data/data/《包》/files/address.db
         */
        SQLiteDatabase db = SQLiteDatabase.openDatabase(
                context.getFilesDir()+"/address.db",
                null,
                SQLiteDatabase.OPEN_READONLY);
        /*
         * 号码的查询分析：
         *   1. 手机号码查询 ： 11位
         *      如何设别手机号码
         *      通过正则表达式来识别：
         *        ^1[345678]\d{9}$
         *   2. 三位的 号码  110   120  119  121 等   ： 特殊号码
         *   3. 四位的号码   5556 ,5554 : 虚拟号码
         *   4. 五位的号码  10086  10000  95553  95556   12345 等
         *      服务号码
         *   5. 本地座机号码  7位或者8位
         *   6. 长途号码   020-68767565    0735-56435434
         */
        if (num.matches("^1[345678]\\d{9}$")){
            Cursor cursor = db.rawQuery("select location from data2 where  id in(select outkey from data1 where id = ?)",
                    new String[]{num.substring(0, 7)});
            //遍历游标
            while (cursor.moveToNext()){
                location = cursor.getString(0);
            }
            cursor.close();
            db.close();
        }else {
            switch (num.length()){
                case 3://特殊号码
                    location="特殊号码";
                    break;
                case 4://虚拟号码
                    location="虚拟号码";
                    break;
                case 5://客服号码
                    location="客服号码";
                    break;
                case 7://本地号码
                case 8://本地号码
                    location="本地号码";
                    break;
                default:
                    if (num.startsWith("0")){// 长途号码  ，位数： 11或者12位 ，必须以 0 开始   020-5678956   0755-23456789   029
                        //依据area 来识别号码归属地  ，取得号码的前缀  ，假如区号是 三位的 ，比如 020
                        if (num.length()>10){
                            String area = num.substring(1, 3);
                            Cursor cursor = db.rawQuery("select location from data2 where area = ?",
                                    new String[]{area});
                            if (cursor.moveToNext()){
                                location = cursor.getString(0);
                                cursor.close();
                            }else{//依据area 来识别号码归属地  ，取得号码的前缀  ，假如区号是 三位的找不到，则查找区号是4位的 ，比如 0755
                                String area1 = num.substring(1, 4);
                                Cursor cursor1 = db.rawQuery("select location from data2 where area = ?",
                                        new String[]{area1});
                                if (cursor1.moveToNext()){
                                    location = cursor.getString(0);
                                    cursor1.close();
                                }
                            }
                        }
                    }
                    break;
            }
        }
        db.close();
        return location;
    }
}
