package com.nlte.phonesafe;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.lidroid.xutils.ViewUtils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.nlte.phonesafe.db.dao.AddressDao;

public class AddressQueryActivity extends AppCompatActivity {
    @ViewInject(R.id.num_et)
    private EditText inputNumEt;
    @ViewInject(R.id.query_btn)
    private Button queryBtn;
    @ViewInject(R.id.location_tv)
    private TextView locationTv;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_address_query);
        ViewUtils.inject(this);

        inputNumEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String str = s.toString();
                String location =  AddressDao.queryAdress(context, str);
                if (!TextUtils.isEmpty(location)){
                    locationTv.setText(location);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //查询按钮点击事件
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num  = inputNumEt.getText().toString().trim();
                if (TextUtils.isEmpty(num)){
                    Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
                    inputNumEt.startAnimation(shake);
                }else{
                    String location = AddressDao.queryAdress(context, num);
                    if (!TextUtils.isEmpty(num)){
                        locationTv.setText(location);
                    }
                }
                //实现震动效果， 通过系统服务来获取震动对象
                Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(10000);//震动持续时间
                /*以某种频率实现震动
                 * pattern：震动的采样
                 * repeat:
                 *   当值为-1  ，表示  不重复
                 *   当大于0的整数 ，则按数组的下标开始重复
                 */
                //vibrator.vibrate(new long[]{500, 300, 100, 200}, -1);
                vibrator.cancel();//停止震动
            }
        });
    }

}
