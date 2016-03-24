package com.nlte.phonesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**缓存工具类， 用来保存私有数据 SharedPreference
 * Created by NLTE on 2016/3/14 0014.
 */
public class CacheUtil {
    public static final String SAFE_PASSWOED = "safe_passwoed";//保存手机防盗密码
    private static SharedPreferences mSp;
    public static final String APK_UPDATE = "apk_update";//是否存在存在更新
    public static final String CONFIG_SP = "config_sp";//config_sp.xml文件
    public static final String IS_FIRST_USE = "is_first_use";//是否第一次使用
    public static String PROTECT_SETTING = "protect_setting";
    public static String SIM = "sim";//SIM卡串号

    /*获取SharedPrefenence对象*/
    private static SharedPreferences getSPreference(Context context){
        if (mSp == null){
            mSp = context.getSharedPreferences(CONFIG_SP, Context.MODE_PRIVATE);
        }
        return mSp;
    }
    /*保存boolean数据*/
    public static void putBoolean(Context context, String key, boolean value){
        SharedPreferences sp = getSPreference(context);
        sp.edit().putBoolean(key, value).commit();//把数据保存到config_sp.xml文件
    }
    /*保存String数据*/
    public static void putString(Context context, String key, String value){
        SharedPreferences sp = getSPreference(context);
        sp.edit().putString(key, value).commit();//把数据保存到config_sp.xml文件
    }
    /*取String数据 默认返回null*/
    public static String getString(Context context, String key){
        SharedPreferences sp = getSPreference(context);
        return sp.getString(key, null);
    }
    public static String getString(Context context, String key, String defValue){
        SharedPreferences sp = getSPreference(context);
        return sp.getString(key, defValue);//返回传递过来的值
    }
    /*取boolean数据 默认返回false*/
    public static boolean getBoolean(Context context, String key){
        SharedPreferences sp = getSPreference(context);
        return sp.getBoolean(key, false);
    }
    public static boolean getBoolean(Context context, String key, boolean defValue){
        SharedPreferences sp = getSPreference(context);
        return sp.getBoolean(key, defValue);//返回传递过来的值
    }
}
