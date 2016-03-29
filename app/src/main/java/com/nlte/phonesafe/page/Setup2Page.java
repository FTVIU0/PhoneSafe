package com.nlte.phonesafe.page;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nlte.phonesafe.R;
import com.nlte.phonesafe.Setup2Activity;
import com.nlte.phonesafe.utils.CacheUtil;
import com.nlte.phonesafe.utils.ToastUtil;
import com.nlte.phonesafe.view.SettingView;

/**
 * Created by NLTE on 2016/3/29 0029.
 */
public class Setup2Page extends BasePage {
    private static final int READ_PHONE_STATE = 123;//获取手机串号请求码
    // 1.注解式声明 ，取代用findViewById来引用
    @ViewInject(R.id.bind_sim_sv)
    private SettingView bindSV;// SettingView控件
    private String simSerialNumber;

    public Setup2Page(Context context){
        super(context);
    }
    @Override
    public View initView() {
        rootView = View.inflate(context, R.layout.fragment_protect_setup2, null);
        ViewUtils.inject(this, rootView);
        if (TextUtils.isEmpty(CacheUtil.getString(context, CacheUtil.SIM, ""))) { // 表示没有绑定sim卡
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
                    ToastUtil.show(context, simSerialNumber);
                }
            }
        });
        return rootView;
    }
    /*获取SIM卡串号*/
    private String getSIMSerialNum(){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String simSerialNumber = telephonyManager.getSimSerialNumber();// 取得sim序列号
        return simSerialNumber;
    }

    @Override
    public void initdata() {

    }
}
