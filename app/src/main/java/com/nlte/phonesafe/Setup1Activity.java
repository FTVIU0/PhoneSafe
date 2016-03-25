package com.nlte.phonesafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Setup1Activity extends BaseSetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
        setTitle("1 欢迎使用手机防盗");
    }
    //下一步
    @Override
    public void nextActivity() {
        Intent intent = new Intent(this, Setup2Activity.class);
        startActivity(intent);
        finish();//销毁当前Activity
        /*重写Activity平移动画，该方法在startActivity（）或finish（）之后执行
        * enterAnim:进入的动画
        * exitAnim:退出的动画*/
        overridePendingTransition(R.anim.next_enter, R.anim.next_exit);
    }

    @Override
    public void preActivity() {
        finish();//销毁Activity
    }
}
