package com.nlte.phonesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.nlte.phonesafe.utils.CacheUtil;

/**
 * Created by NLTE on 2016/3/25 0025.
 */
public class BootCompliteReceiver extends BroadcastReceiver {
    //在主线程运行，且生命周期比较短，不适合做生命周期比较长的东西
    @Override
    public void onReceive(Context context, Intent intent) {
        // 取得当前SIM卡的串号
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context
                .TELEPHONY_SERVICE);
        String simSerialNumber = telephonyManager.getSimSerialNumber();
        String storeSimSerialNumber = CacheUtil.getString(context, CacheUtil.SIM, "");
        if (!storeSimSerialNumber.equals(simSerialNumber)){//当前的SIM卡串号与存储的不匹配
            //发短信给安全号码
            String safeNum = CacheUtil.getString(context, CacheUtil.SAFE_NUM, "18898871906");
            //发短信
            SmsManager smsManager = SmsManager.getDefault();//取得短信管理器
            smsManager.sendTextMessage(safeNum, null, "Boss, I lost", null, null);
        }
    }
}
