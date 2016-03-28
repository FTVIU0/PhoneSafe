package com.nlte.phonesafe;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nlte.phonesafe.fragment.ProtectFragment;
import com.nlte.phonesafe.fragment.ProtectSetupFragment;
import com.nlte.phonesafe.utils.CacheUtil;

public class LostFindActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        FragmentManager fragmentManager = getFragmentManager();
        if (CacheUtil.getBoolean(context, CacheUtil.PROTECT_SETTING)) {
            //添加展示手机防盗界面片段
            ProtectFragment protectFragment = new ProtectFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.content, protectFragment, "ProtectFragment")
                    .commit();
        } else {
            //进入手机防盗设置片段界面
            ProtectSetupFragment protectSetupFragment=new ProtectSetupFragment();
            //添加展示手机防盗界面片段
            fragmentManager.beginTransaction()
                    .replace(R.id.content, protectSetupFragment, "protectSetupFragment")
                    .commit();
        }
    }
}
