package com.nlte.phonesafe;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by NLTE on 2016/3/25 0025.
 */
public abstract class BaseSetupActivity extends AppCompatActivity {
    private TextView titleTextView;//声明标题栏文本框
    //下一个Activity
    public void next(View view){
        nextActivity();
    }
    //上一个Activity
    public void pre(View view){
        preActivity();
    }
    //抽象方法
    public abstract void nextActivity();
    public abstract void preActivity();
    //设置标题
    public void setTitle(String string){
        TextView title = (TextView)findViewById(R.id.title_bar);
        title.setText(string);
    }
    //设置返回键,监听按键事件，不销毁当前Activity，只返回上一步

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            //返回上一个Activity
            preActivity();
            return true;//为true则不销毁当前Activity
        }
        return super.onKeyDown(keyCode, event);
    }
}
