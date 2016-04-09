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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nlte.phonesafe.R;
import com.nlte.phonesafe.db.dao.AddressDao;
import com.nlte.phonesafe.utils.CacheUtil;

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
                    diyToast(location);
//                    Log.d("TTTAAAGGG", location);
//                    Toast.makeText(getApplicationContext(), location, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    }
    private int[] toastBackground = {R.color.blue, R.color.gray, R.color.organge, R.color.green, R.color.white};
    /*自定义Toast*/
    private void diyToast(String location) {
        //获取根节点
        View toastView = View.inflate(this, R.layout.location_toast, null);
        //获取选中的Toast风格
        int selectToastStyle = CacheUtil.getInt(this, CacheUtil.LOCATION_STYLE);
        //设置Toast的背景
        toastView.setBackgroundResource(toastBackground[selectToastStyle]);
        TextView locationTv = (TextView) toastView.findViewById(R.id.location_tv);
        locationTv.setText(location);
        //自定义Toast
        Toast toast = new Toast(this);
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 50);
        toast.show();
    }


    @Override
    public void onDestroy() {
        telephonyManager.listen(telephonyListener,PhoneStateListener.LISTEN_NONE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
