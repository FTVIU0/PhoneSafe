package com.nlte.phonesafe;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nlte.phonesafe.utils.CacheUtil;
import com.nlte.phonesafe.utils.ToastUtil;
import com.nlte.phonesafe.view.SettingView;

public class Setup2Activity extends AppCompatActivity {
    private Context context;
    private static final int READ_PHONE_STATE = 123;//获取手机串号请求码
    // 1.注解式声明 ，取代用findViewById来引用
    @ViewInject(R.id.bind_sim_sv)
    private SettingView bindSV;// SettingView控件
    private String simSerialNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
       /* 采用Xutil框架的viewUtils 模块实现 控件的注入
       首先要引入当前Activiyt关联的布局的控件对象 ，再注入到已经声明注解的控件中
         该方法要在setContentView(R.layout.activity_setup2)之后，即布局的控件先实例化为对象，再注入
        对绑定sim控件 SettingView初始化*/
        ViewUtils.inject(this);//注入
        if (TextUtils.isEmpty(CacheUtil.getString(this, CacheUtil.SIM, ""))) { // 表示没有绑定sim卡
            bindSV.setChecked(false);
        } else {
            bindSV.setChecked(true);
        }
        // 对SettingView控件设置点击监听事件 ,实现对sim卡绑定和解密
        bindSV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (bindSV.getChecked()) {
                    // 解绑sim卡
                    bindSV.setChecked(false);
                    CacheUtil.putString(context, CacheUtil.SIM, "");
                } else {
                    // 绑定sim卡
                    bindSV.setChecked(true);
                    // 保存的是sim的序列号
                    // 通过系统提供的服务来取得 ,取得电话管理服务
                    simSerialNumber = getSIMSerialNum();
                    CacheUtil.putString(context, CacheUtil.SIM,
                            simSerialNumber);
                    ToastUtil.show(Setup2Activity.this, simSerialNumber);
                }
            }
        });
    }


    /*获取SIM卡串号*/

    private String getSIMSerialNum(){
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String simSerialNumber = telephonyManager.getSimSerialNumber();// 取得sim序列号
        return simSerialNumber;
    }
    //下一步， 要求必须绑定SIM卡才能指向
    public void next(View view){
        if (bindSV.getChecked()){
        Intent intent = new Intent(this, Setup3Activity.class);
        startActivity(intent);
        finish();//销毁当前Activity
        overridePendingTransition(R.anim.next_enter, R.anim.next_exit);
        }else {
            ToastUtil.show(Setup2Activity.this, "请先绑定SIM卡");
        }
    }
    //上一步
    public void pre(View view){
        Intent intent = new Intent(this, Setup1Activity.class);
        startActivity(intent);
        finish();//销毁当前Activity
        overridePendingTransition(R.anim.pre_enter, R.anim.pre_exit);
    }
}
