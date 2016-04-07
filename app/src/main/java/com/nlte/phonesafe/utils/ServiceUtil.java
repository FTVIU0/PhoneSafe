package com.nlte.phonesafe.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

import java.util.List;

/**
 * 服务相关工具类
 * Created by NLTE on 2016/4/8 0008.
 */
public class ServiceUtil {
    public static boolean isServiceRunning(Context context, String className){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //取得正在运行的服务
        List<RunningServiceInfo> runningServiceInfos = activityManager.getRunningServices(10);
        for (RunningServiceInfo temp : runningServiceInfos){
            if (className.equals(temp.service.getClassName())){
                return true;
            }
        }
        return false;
    }
}
