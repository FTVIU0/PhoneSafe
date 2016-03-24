package com.nlte.phonesafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nlte.phonesafe.utils.ToastUtil;

public class Setup4Activity extends AppCompatActivity {
    @ViewInject(R.id.protect_cb)
    private CheckBox protectCb;//复选控件，实现手机防盗的开启和关闭
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        ViewUtils.inject(this);
    }
    //设置完成
    public void setupfinish(View view){
        // TODO: 2016/3/23 0023
        ToastUtil.show(this, "设置完成");
    }
    //上一步
    public void pre(View view){
        Intent intent = new Intent(this, Setup3Activity.class);
        startActivity(intent);
        finish();//销毁当前Activity
        overridePendingTransition(R.anim.pre_enter, R.anim.pre_exit);
    }
}
