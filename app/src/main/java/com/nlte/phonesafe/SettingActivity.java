package com.nlte.phonesafe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.nlte.phonesafe.service.AddressService;
import com.nlte.phonesafe.utils.CacheUtil;
import com.nlte.phonesafe.utils.ServiceUtil;
import com.nlte.phonesafe.view.SettingStyleView;
import com.nlte.phonesafe.view.SettingView;

public class SettingActivity extends AppCompatActivity {
    private Context context;
    private SettingView mUpdateSv;
    private SettingView mSoftLockSv;
    private SettingView mAddressSv;
    private SettingStyleView mLocationStyleSsv;
    private String[] styleItems = {"卫士蓝","金属灰","活力橙","苹果绿","半透明"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        context = this;
        mUpdateSv = (SettingView)findViewById(R.id.update_sv);
        mSoftLockSv = (SettingView)findViewById(R.id.soft_sv);
        mAddressSv = (SettingView)findViewById(R.id.address_sv);
        mLocationStyleSsv = (SettingStyleView)findViewById(R.id.locationStyle_ssv);
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
        Log.d("mAddressSv.getChecked1", mAddressSv.getChecked()+"");
        //显示手机号码归属地的点击监听事件
        if (CacheUtil.getBoolean(context, "Address")){
            mAddressSv.setChecked(true);
            Log.d("CacheUtil.getBoolean", CacheUtil.getBoolean(context, "Address")+"");
        }else {
            mAddressSv.setChecked(false);
            Log.d("CacheUtil.getBoolean", CacheUtil.getBoolean(context, "Address")+"");
        }
        Log.d("mAddressSv.getChecked2", mAddressSv.getChecked()+"");
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
        int which = CacheUtil.getInt(context, CacheUtil.LOCATION_STYLE);
        mLocationStyleSsv.setDes(styleItems[which]);
        //设置手机号码归属地提示框风格点击事件
        mLocationStyleSsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示单选对话框
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setIcon(R.drawable.eye_open)
                        .setTitle("号码归属地风格")
                        .setSingleChoiceItems(
                                styleItems,
                                CacheUtil.getInt(context, CacheUtil.LOCATION_STYLE),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //点击单选 ，则把选择 通过Sharedpreferencs保存
                                        CacheUtil.putInt(context, CacheUtil.LOCATION_STYLE, which);
                                        mLocationStyleSsv.setDes(styleItems[which]);
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton("取消", null)
                        .create();
                alertDialog.show();
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
