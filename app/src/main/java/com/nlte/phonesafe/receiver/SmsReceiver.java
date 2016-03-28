package com.nlte.phonesafe.receiver;

import android.Manifest;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.nlte.phonesafe.R;
import com.nlte.phonesafe.service.GPSService;
import com.nlte.phonesafe.utils.CacheUtil;
import com.nlte.phonesafe.utils.ToastUtil;

public class SmsReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    @Override
    public void onReceive(Context context, Intent intent) {
        //取得设备方案管理者
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        String action = intent.getAction();
        //判断广播消息
        if (action.equals(SMS_RECEIVED_ACTION)){
            Bundle bundle = intent.getExtras();
            //如果不为空
            if (bundle!=null){
                //将pdus里面的内容转化成Object[]数组
                Object pdusData[] = (Object[]) bundle.get("pdus");// pdus ：protocol data unit  ：
                //解析短信
                SmsMessage[] msg = new SmsMessage[pdusData.length];
                for (int i = 0;i < msg.length;i++){
                    byte pdus[] = (byte[]) pdusData[i];
                    msg[i] = SmsMessage.createFromPdu(pdus);
                }
                StringBuffer content = new StringBuffer();//获取短信内容
                StringBuffer phoneNumber = new StringBuffer();//获取地址
                //分析短信具体参数
                for (SmsMessage temp : msg){
                    content.append(temp.getMessageBody());
                    phoneNumber.append(temp.getOriginatingAddress());
                }
                System.out.println("发送者号码："+phoneNumber.toString()+"  短信内容："+content.toString());
                if ("#*location*#".equals(content.toString().trim())){
                    Intent locationIntent=new Intent();
                    locationIntent.setClass(context, GPSService.class);
                    context.startService(locationIntent);//开启服务

                    //取得当前手机的位置，并且发给安全号码的手机
                    String latitude = CacheUtil.getString(context, "latitude");
                    String longitude = CacheUtil.getString(context, "longitude");
                    System.out.println(latitude+longitude);
                    /*发短信通知安全号码*/
                    /*if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS ) ==
                            PackageManager.PERMISSION_GRANTED){
                    SmsManager manager=SmsManager.getDefault();
                    manager.sendTextMessage
                            (CacheUtil.getString(context, CacheUtil.SAFE_NUM), null, latitude+longitude, null, null);
                    }*/

                    abortBroadcast();//截断短信广播
                }else if ("#*alarm*#".equals(content.toString().trim())){
                    MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.guoge);
                    //播放音乐
                    mediaPlayer.start();
                    abortBroadcast();//截断短信广播
                }else if ("#*wipe*#".equals(content.toString().trim())){
                    ToastUtil.show(context, "清除数据");
                    //声明一个组件
                    ComponentName componentName = new ComponentName(context, MyAdminReceiver.class);
                    if (devicePolicyManager.isAdminActive(componentName)){
                        devicePolicyManager.wipeData(0);
                    }
                    abortBroadcast();//截断短信广播
                }else if ("#*lockscreen*#".equals(content.toString().trim())){
                    //声明一个组件
                    ComponentName componentName = new ComponentName(context, MyAdminReceiver.class);
                    if (devicePolicyManager.isAdminActive(componentName)){
                        devicePolicyManager.lockNow();
                    }
                    abortBroadcast();//截断短信广播
                }
            }
        }
    }
}
