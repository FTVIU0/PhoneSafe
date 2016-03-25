package com.nlte.phonesafe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nlte.phonesafe.utils.CacheUtil;

public class LostFindActivity extends AppCompatActivity {
    private Context context;
    @ViewInject(R.id.safe_num_tv)
    private TextView safeNumTv;//安全号码文本框
    @ViewInject(R.id.protected_iv)
    private ImageView lockIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        if (CacheUtil.getBoolean(context, CacheUtil.PROTECT_SETTING)) {
            //进入手机防盗展示界
            setContentView(R.layout.activity_lost_find);
            ViewUtils.inject(this);
        } else {
            //进入到设置向导界面
            Intent intent=new Intent();
            intent.setClass(context, Setup1Activity.class);
            startActivity(intent);
            finish();
        }
    }
    //进入重新设置向导
    public void resetup(View view){
        Intent intent = new Intent(LostFindActivity.this, Setup1Activity.class);
        startActivity(intent);
        CacheUtil.putBoolean(LostFindActivity.this, CacheUtil.IS_PROTECT, false);
    }

    @Override
    protected void onStart() {
        //初始化手机安全号码
        safeNumTv.setText(CacheUtil.getString(this, CacheUtil.SAFE_NUM));
        safeNumTv.setEnabled(false);//不可用
        //初始化是否显示锁
        if (CacheUtil.getBoolean(this, CacheUtil.IS_PROTECT)){
            lockIv.setImageResource(R.drawable.lock);
        }else {
            lockIv.setImageResource(R.drawable.unlock);
        }
        super.onStart();
    }
}
