package com.nlte.phonesafe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.nlte.phonesafe.adapter.HomeAdapter;
import com.nlte.phonesafe.utils.CacheUtil;
import com.nlte.phonesafe.utils.MD5;
import com.nlte.phonesafe.utils.ToastUtil;

public class HomeActivity extends AppCompatActivity {
    private Context context;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        /**
         * 用GridView展示批量数据 网格视图
         * 步骤：
         * 1.网格项的布局
         * 2.初始数据（固定）
         * 3.准备适配器 自定义适配器
         * 4.展示与监听（点击网格项监听）
         */
        GridView gridView = (GridView)findViewById(R.id.home_gv);
        gridView.setAdapter(new HomeAdapter(this));
        // 对网格视图实现点击网各项监听
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position){
                    case 0://手机防盗
                        if (TextUtils.isEmpty(CacheUtil.getString(context, CacheUtil
                                .SAFE_PASSWOED))){//假如为空，则进入到设置初始密码对话框
                            showSetupDialog();
                        }else {//否则进入到输入密码对话框
                            showAuthorDialog();
                        }
                        break;
                    case 1://通信卫士

                        break;
                    case 2://软件管理

                        break;
                    case 3://进程管理

                        break;
                    case 4://流量统计

                        break;
                    case 5://手机杀毒

                        break;
                    case 6://缓存清理

                        break;
                    case 7://高级工具

                        break;
                    case 8://设置中心
                        intent.setClass(context, SettingActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }
    //显示输入密码对话框
    private void showAuthorDialog() {
        View dialogView = View.inflate(context, R.layout.safe_author_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        mDialog = builder.create();
        mDialog.setCancelable(false);
        mDialog.show();

        final EditText pwdEt = (EditText) dialogView.findViewById(R.id.pwd_et);//输入密码文本框
        Button okBtn = (Button) dialogView.findViewById(R.id.ok_btn);//确定按钮
        Button cancelBtn = (Button) dialogView.findViewById(R.id.cancel_btn);//取消按钮
        //实现明密文切换
        final Button pwdChangeBtn = (Button)dialogView.findViewById(R.id.pwd_change_btn);
        pwdChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwdEt.getInputType() == 129){//129代表textpassword的输入方式
                    pwdEt.setInputType(EditorInfo.TYPE_CLASS_TEXT);
                    pwdChangeBtn.setBackgroundResource(R.drawable.eye_open);
                }else {
                    pwdEt.setInputType(129);
                    pwdChangeBtn.setBackgroundResource(R.drawable.eye_closed);
                }
            }
        });

        //确定按钮点击事件
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = pwdEt.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)){
                    ToastUtil.show(context, "输入密码不能为空");
                }else if (MD5.getMD5(pwd).equals(CacheUtil.getString(context, CacheUtil.SAFE_PASSWOED))){
//                    ToastUtil.show(context, "验证成功");
                    Intent intent = new Intent(context, LostFindActivity.class);
                    startActivity(intent);
                    mDialog.dismiss();
                }else {
                    ToastUtil.show(context, "验证不成功");
                }
            }
        });
        //取消按钮点击事件监听
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把对话框消失
                mDialog.dismiss();
            }
        });
    }

    /*显示设置手机防盗的初始化密码*/
    private void showSetupDialog() {
        View dialogView = View.inflate(context, R.layout.safe_setup_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        mDialog = builder.create();
        mDialog.setCancelable(false);
        mDialog.show();
//        builder.show();//该方法包含了对话框的创建和显示

        /*通过对话框根节点来获取其子控件*/
        final EditText pwdEt = (EditText) dialogView.findViewById(R.id.pwd_et);//输入密码文本框
        final EditText confirmPwdEt = (EditText) dialogView.findViewById(R.id.confirm_pwd_et);//确认密码文本框
        Button setupBtn = (Button) dialogView.findViewById(R.id.setup_btn);//设置按钮
        Button cancelBtn = (Button) dialogView.findViewById(R.id.cancel_btn);//取消按钮
        /*对按钮设置点击事件监听*/
        //设置按钮点击事件监听
        setupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取得用户输入的密码
                String pwd = pwdEt.getText().toString().trim();
                String confirmPwd = confirmPwdEt.getText().toString().trim();
                if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(confirmPwd)){//密码必能为空
                    ToastUtil.show(context, "密码不能为空");
                    return;
                }
                //两次输入的密码相同
                if (pwd.equals(confirmPwd)){
                    CacheUtil.putString(context, CacheUtil.SAFE_PASSWOED, MD5.getMD5(pwd));
                    mDialog.dismiss();
                    ToastUtil.show(context, "设置密码成功");
                }else {
                    ToastUtil.show(context, "两次密码输入不一致");
                }
            }
        });
        //取消按钮点击事件监听
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把对话框消失
                mDialog.dismiss();
            }
        });
    }

}
