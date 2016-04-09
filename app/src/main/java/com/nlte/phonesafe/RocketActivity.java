package com.nlte.phonesafe;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class RocketActivity extends AppCompatActivity {
    private ImageView rocketIv;
    private PointF startF;//一个点的位置，包括X， Y
    private Matrix matrix = new Matrix();//矩阵
    private Matrix startMatrix = new Matrix();//图片的起始矩阵
    private Vibrator vibrator;
    private Handler handle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //更新UI控件的位置
            matrix.postTranslate(0, -15);
            rocketIv.setImageMatrix(matrix);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocket);
        //获取系统的震动功能
        vibrator  = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        rocketIv = (ImageView) findViewById(R.id.rocket_iv);
        //对小火箭的触摸监听事件
        rocketIv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN://一根手指按下:
                        //震动100ms
                        vibrator.vibrate(100);
                        //获取手指按下后的点的位置
                        startF = new PointF(event.getX(), event.getY());
                        //获取当前图片的矩阵
                        startMatrix.set(rocketIv.getImageMatrix());
                        break;
                    case MotionEvent.ACTION_MOVE://移动:
                        //取得偏移量
                        float dx = event.getX() - startF.x;
                        float dy = event.getY() - startF.y;
                        //设置起始矩阵
                        matrix.set(startMatrix);
                        //矩阵的平移
                        matrix.postTranslate(dx, dy);
                        break;
                    case MotionEvent.ACTION_UP://手指松开:
                        //取消震动
                        vibrator.cancel();
                        /*小火箭实现发射*/
                        rockShot();
                        break;
                }
                rocketIv.setImageMatrix(matrix);
                return true;
            }
        });
    }

    //对小火箭的触摸监听事件
    private void rockShot() {
        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i<=80; i++){
                    SystemClock.sleep(20);
                    handle.sendEmptyMessage(0);
                }
                super.run();
            }
        }.start();
    }
}
