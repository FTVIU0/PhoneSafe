package com.nlte.phonesafe.page;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nlte.phonesafe.ContactActivity;
import com.nlte.phonesafe.LostFindActivity;
import com.nlte.phonesafe.R;
import com.nlte.phonesafe.utils.CacheUtil;
import com.nlte.phonesafe.utils.ToastUtil;

/**
 * Created by NLTE on 2016/3/29 0029.
 */
public class Setup3Page extends BasePage {
    @ViewInject(R.id.safe_num_et)
    private EditText safeNumEt;//安全号码编辑框

    @OnClick(R.id.select_contact_btn)
    public void selectNum(View v){
        Intent intent = new Intent(context, ContactActivity.class);
        ((LostFindActivity)context).startActivityForResult(intent, 2);//2：代表请求码  要求有返回值
    }
    @OnClick(R.id.save_num_btn)
    public void saveNum(View v){
        String safeNum=safeNumEt.getText().toString().trim();
        CacheUtil.putString(context, CacheUtil.SAFE_NUM,safeNum);
        ToastUtil.show(context, "保存成功");
    }
    public Setup3Page(Context context){
        super(context);
    }
    @Override
    public View initView() {
        rootView = View.inflate(context, R.layout.fragment_protect_setup3, null);
        ViewUtils.inject(this, rootView);
        //设置安全号码的初始化
        safeNumEt.setText(CacheUtil.getString(context, CacheUtil.SAFE_NUM));
        return rootView;
    }

    @Override
    public void initdata() {

    }
}
