package com.nlte.phonesafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.nlte.phonesafe.utils.CacheUtil;
import com.nlte.phonesafe.utils.ToastUtil;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        /*引用图片控件*/
        ImageView welcomeImageView = (ImageView)findViewById(R.id.welcome_iv);

        /**
         * 设置动画集
         */
        AnimationSet set = new AnimationSet(false);

        /*动画1：旋转动画*/
        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotateAnimation.setDuration(2500);//设置时长
        rotateAnimation.setFillAfter(true);//设置最终状态为填充效果
        set.addAnimation(rotateAnimation);//添加动画

        /*动画2：缩放*/
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0, 1,
                0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(2500);//设置时长
        rotateAnimation.setFillAfter(true);//设置最终状态为填充效果
        set.addAnimation(scaleAnimation);//添加动画

        /*动画3：透明度 来自XML*/
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.welcome_alpha);
        set.addAnimation(animation);//添加动画

        /**
         * 监听动画
         */
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                try {
                    Thread.sleep(1500);//动画结束后，页面停留1.5秒，再跳转到导航页面
                    ToastUtil.show(getApplicationContext(), "喜羊羊");
                    /*动画结束， 进入到GuideActivity*/
                    if (CacheUtil.getBoolean(WelcomeActivity.this, CacheUtil.IS_FIRST_USE, true)){
                        Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
                        startActivity(intent);
                        finish();//欢迎界面结束退出
                    }else {
                        Intent intent = new Intent(WelcomeActivity.this, SplashActivity.class);
                        startActivity(intent);
                        finish();//欢迎界面结束退出
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        /**
         * 展示动画
         */
        welcomeImageView.startAnimation(set);
    }
}
