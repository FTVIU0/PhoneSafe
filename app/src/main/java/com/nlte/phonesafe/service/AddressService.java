package com.nlte.phonesafe.service;
/**
 * 监听来电服务
 */

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.nlte.phonesafe.db.dao.AddressDao;

public class AddressService extends Service {
    private TelephonyManager telephonyManager;
    private myTelephonyListener telephonyListener;
    @Override
    public void onCreate() {
        //获取系统电话服务
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //监听电话状态
        telephonyListener = new myTelephonyListener();
        telephonyManager.listen(telephonyListener,PhoneStateListener.LISTEN_CALL_STATE);
    }

    private class myTelephonyListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state){
                case TelephonyManager.CALL_STATE_IDLE://空闲状态
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK://通话状态
                    break;
                case TelephonyManager.CALL_STATE_RINGING://响铃状态
                    String location = AddressDao.queryAdress(AddressService.this, incomingNumber);
                    //当手机铃声响时，显示手机号码的归属地
                    Toast.makeText(getApplicationContext(), location, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void onDestroy() {
        telephonyManager.listen(telephonyListener,PhoneStateListener.LISTEN_NONE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
