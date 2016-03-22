package com.nlte.phonesafe;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nlte.phonesafe.com.nlte.phonesafe.utils.CacheUtil;

public class LostFindActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        if (CacheUtil.getBoolean(context, CacheUtil.PROTECT_SETTING)) {
            //进入手机防盗展示界
            setContentView(R.layout.activity_lost_find);
        } else {
            //进入设置向导界面

        }
    }
}
