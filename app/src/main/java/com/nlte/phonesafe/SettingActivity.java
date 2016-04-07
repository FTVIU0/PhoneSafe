package com.nlte.phonesafe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nlte.phonesafe.service.AddressService;
import com.nlte.phonesafe.utils.CacheUtil;
import com.nlte.phonesafe.utils.ServiceUtil;
import com.nlte.phonesafe.view.SettingView;

public class SettingActivity extends AppCompatActivity {
    private Context context;
    private SettingView mUpdateSv;
    private SettingView mSoftLockSv;
    private SettingView mAddressSv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        context = this;
        mUpdateSv = (SettingView)findViewById(R.id.update_sv);
        mSoftLockSv = (SettingView)findViewById(R.id.soft_sv);
        mAddressSv = (SettingView)findViewById(R.id.address_sv);
        //设置自动升级自定义点击监听事件 每一次点击切换复选状态
        mUpdateSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUpdateSv.getChecked()){
                    CacheUtil.putBoolean(context, CacheUtil.APK_UPDATE, false);
                    mUpdateSv.setChecked(false);
                }else {
                    CacheUtil.putBoolean(context, CacheUtil.APK_UPDATE, true);
                    mUpdateSv.setChecked(true);
                }
            }
        });
        //对软件锁的点击事件
        mSoftLockSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSoftLockSv.getChecked()){
                    mSoftLockSv.setChecked(false);
                }else {
                    mSoftLockSv.setChecked(true);
                }
            }
        });
        /*if (CacheUtil.getBoolean(context, "Address")){
            mAddressSv.setChecked(true);
        }else {
            mAddressSv.setChecked(false);
        }*/
        //显示手机号码归属地的点击监听事件
        mAddressSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddressService.class);
                if (mAddressSv.getChecked()){
                    stopService(intent);
                    mAddressSv.setChecked(false);
                    CacheUtil.putBoolean(context, "Address", false);
                }else {
                    startService(intent);
                    mAddressSv.setChecked(true);
                    CacheUtil.putBoolean(context, "Address", true);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        //获取服务的状态
        boolean serviceStatus = ServiceUtil.isServiceRunning(context, "com.nlte.phonesafe.service.AddressService");
        if (serviceStatus){
            mAddressSv.setChecked(true);
        }else {
            mAddressSv.setChecked(false);
        }
        super.onStart();
    }
}
