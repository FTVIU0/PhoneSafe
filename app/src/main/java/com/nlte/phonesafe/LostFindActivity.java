package com.nlte.phonesafe;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.nlte.phonesafe.fragment.ProtectFragment;
import com.nlte.phonesafe.fragment.ProtectSetupFragment;
import com.nlte.phonesafe.utils.CacheUtil;

public class LostFindActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_find);
        context = this;
        FragmentManager fragmentManager = getFragmentManager();
        if (CacheUtil.getBoolean(context, CacheUtil.PROTECT_SETTING)) {
            //添加展示手机防盗界面片段
            ProtectFragment protectFragment = new ProtectFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.contentActivity, protectFragment, "ProtectFragment")
                    .commit();
        } else {
            //进入手机防盗设置片段界面
            ProtectSetupFragment protectSetupFragment=new ProtectSetupFragment();
            //添加展示手机防盗界面片段
            fragmentManager.beginTransaction()
                    .replace(R.id.contentActivity, protectSetupFragment, "protectSetupFragment")
                    .commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data!=null && requestCode==2){
            //设置安全号码
            EditText safeNumEt=(EditText) findViewById(R.id.safe_num_et);
            safeNumEt.setText(data.getStringExtra("num"));
        }
    }
}
