package com.nlte.phonesafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnCompoundButtonCheckedChange;
import com.nlte.phonesafe.utils.CacheUtil;
import com.nlte.phonesafe.utils.ToastUtil;

public class Setup4Activity extends AppCompatActivity {
    @ViewInject(R.id.protect_cb)
    private CheckBox protectCb;//复选控件，实现手机防盗的开启和关闭
    //绑定状态改变监听
    @OnCompoundButtonCheckedChange(R.id.protect_cb)
    public void test_saveProtectState(CompoundButton buttonView, boolean isChecked){
        if (isChecked){
            CacheUtil.putBoolean(this, CacheUtil.IS_PROTECT, true);
        }else{
            CacheUtil.putBoolean(this, CacheUtil.IS_PROTECT, false);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        ViewUtils.inject(this);

        //初始化CHeckBox
        if (CacheUtil.getBoolean(this, CacheUtil.IS_PROTECT)){
            protectCb.setChecked(true);
        }else {
            protectCb.setChecked(false);
        }
    }
    //设置完成
    public void setupfinish(View view){
        ToastUtil.show(this, "设置完成");
        //保存设置完成
        CacheUtil.putBoolean(this, CacheUtil.PROTECT_SETTING, true);
        finish();
    }
    //上一步
    public void pre(View view){
        Intent intent = new Intent(this, Setup3Activity.class);
        startActivity(intent);
        finish();//销毁当前Activity
        overridePendingTransition(R.anim.pre_enter, R.anim.pre_exit);
    }
}
