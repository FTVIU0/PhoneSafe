package com.nlte.phonesafe.page;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnCompoundButtonCheckedChange;
import com.nlte.phonesafe.LostFindActivity;
import com.nlte.phonesafe.R;
import com.nlte.phonesafe.utils.CacheUtil;

/**
 * Created by NLTE on 2016/3/29 0029.
 */
public class Setup4Page extends BasePage {
    @ViewInject(R.id.protect_cb)
    private CheckBox protectCb;//复选控件，实现手机防盗的开启和关闭
    //绑定状态改变监听
    @OnCompoundButtonCheckedChange(R.id.protect_cb)
    public void test_saveProtectState(CompoundButton buttonView, boolean isChecked){
        if (isChecked){
            CacheUtil.putBoolean(context, CacheUtil.IS_PROTECT, true);
        }else{
            CacheUtil.putBoolean(context, CacheUtil.IS_PROTECT, false);
        }
    }
    @ViewInject(R.id.setup_finish_btn)
    private Button setupFinishBtn;

    public Setup4Page(Context context){
        super(context);
    }
    @Override
    public View initView() {
        rootView = View.inflate(context, R.layout.fragment_protect_setup4, null);
        ViewUtils.inject(this, rootView);
        //初始化CHeckBox
        if (CacheUtil.getBoolean(context, CacheUtil.IS_PROTECT)){
            protectCb.setChecked(true);
        }else {
            protectCb.setChecked(false);
        }
        setupFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheUtil.putBoolean(context, CacheUtil.PROTECT_SETTING, true);
                ((LostFindActivity)context).finish();
            }
        });
        return rootView;
    }

    @Override
    public void initdata() {

    }
}
