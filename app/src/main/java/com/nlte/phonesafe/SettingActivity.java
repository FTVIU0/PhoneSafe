package com.nlte.phonesafe;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.nlte.phonesafe.com.nlte.phonesafe.utils.CacheUtil;
import com.nlte.phonesafe.com.nlte.phonesafe.view.SettingView;

public class SettingActivity extends AppCompatActivity {
    private Context context;
    private SettingView mUpdateSv;
    private SettingView mSoftLockSv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        context = this;
        mUpdateSv = (SettingView)findViewById(R.id.update_sv);
        mSoftLockSv = (SettingView)findViewById(R.id.soft_sv);
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
    }
}
