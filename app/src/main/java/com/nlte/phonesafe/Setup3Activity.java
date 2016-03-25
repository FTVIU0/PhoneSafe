package com.nlte.phonesafe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nlte.phonesafe.utils.CacheUtil;
import com.nlte.phonesafe.utils.ToastUtil;

public class Setup3Activity extends AppCompatActivity {
    @ViewInject(R.id.safe_num_et)
    private EditText safeNumEt;//安全号码编辑框
    @ViewInject(R.id.select_contact_btn)
    private Button selectContactBtn;//选择联系人的按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        ViewUtils.inject(this);
        //设置安全号码的初始化
        safeNumEt.setText(CacheUtil.getString(this, CacheUtil.SAFE_NUM));
    }
    //下一步， 把用户设置的安全号码进行保存或者更新
    public void next(View view){
        if (TextUtils.isEmpty(safeNumEt.getText().toString().trim())){
            ToastUtil.show(this, "安全号码没设置");
        }else {//设置了安全号码
            CacheUtil.putString(this, CacheUtil.SAFE_NUM, safeNumEt.getText().toString().trim());
        }
        //进入下一个Activity
        Intent intent = new Intent(this, Setup4Activity.class);
        startActivity(intent);
        finish();//销毁当前Activity
        overridePendingTransition(R.anim.next_enter, R.anim.next_exit);
    }
    //上一步
    public void pre(View view){
        Intent intent = new Intent(this, Setup2Activity.class);
        startActivity(intent);
        finish();//销毁当前Activity
        overridePendingTransition(R.anim.pre_enter, R.anim.pre_exit);
    }
    //选择联系人
    public void selectContact(View view){
        ToastUtil.show(this, "选择联系人");
    }
}
