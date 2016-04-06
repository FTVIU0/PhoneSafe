package com.nlte.phonesafe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.nlte.phonesafe.utils.CacheUtil;
import com.nlte.phonesafe.utils.Constants;
import com.nlte.phonesafe.utils.ToastUtil;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {
    public static final int MSG_SHOW_DIOLOG = 1;//显示升级对话框的标记
    public static final int MSG_ENTER_HOME = 2;//进入home界面的标记
    public static final int MSG_SERVER_ERROR= 3;// 访问服务端错误的标记
    private TextView mVersionTv;//版本号
    private TextView mProcessTv;//下载百分比
    private ProgressBar mDownlaodProgressBar;//apk下载进度条
    private Context context;//上下文
    private int newVersionCode;//服务端版本号
    private String apkUrl;//服务端apk的url地址
    private String versionDes;//服务端版本描述
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_ENTER_HOME:
                    enterHome();//进入主界面
                    break;
                case MSG_SHOW_DIOLOG:
                    showUpdateDialog();//显示更新对话框
                    break;
                case MSG_SERVER_ERROR:
                    enterHome();//进入主界面
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();//初始化视图界面

        /*根据用户配置是否要更新版本*/
        if (CacheUtil.getBoolean(context,CacheUtil.APK_UPDATE,true)){
            checkUpdate();//检测更新
        }else{
            new Thread(){
                public void run() {
                    SystemClock.sleep(2000);//休眠两秒
                    runOnUiThread(new Runnable() {
                         @Override
                        public void run() {
                            enterHome();//进入HomeActivity
                        }
                    });
                };
            }.start();
        }
        //把assets目录的adress.db 拷贝到手机本地
        copyDB();
    }
    /*把assets目录的adress.db 拷贝到手机本地
    * 步骤：
    *   1.打开assets目录
    *   2.获取输入流
    *   3.在手机本地创建一个文件，开辟输出流，写入文件*/
    private void copyDB() {
        File file = new File(getFilesDir(), "address.db");
        if (!file.exists()){//当文件存在时，不创建
            //1.打开assets目录
            AssetManager assetManger = getAssets();
            try {
                //2.获取文件的输入流
                InputStream is = assetManger.open("address.db");
                //3.在手机本地创建一个文件，开辟输出流，写入文件
                FileOutputStream fos = new FileOutputStream(file);
                int len = -1;
                byte[] b = new byte[1024];
                while ((len = is.read(b))!=-1){
                    fos.write(b, 0, len);
                }
                //关闭流
                fos.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("copyDB", "Success");
        }
    }


    /*显示是否要升级apk的对话框*/
    private void showUpdateDialog() {
        new AlertDialog.Builder(context)
                .setIcon(R.drawable.ic_logo)
                .setTitle("新版本"+newVersionCode)
                .setMessage(versionDes)
                .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //升级apk
                        downloadApk();
                        ToastUtil.show(context, "下载Apk");
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //进入到HomeActivity界面
                        enterHome();
                    }
                })
                .create()
                .show();

    }
    /*下载最新版Apk*/
    private void downloadApk() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.download(apkUrl, getExternalCacheDir().getAbsolutePath() + "/safe1.apk",
                new RequestCallBack<File>() {
                    //已经下载完毕
                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        installApk();
                    }
                    //访问网络失败
                    @Override
                    public void onFailure(HttpException e, String s) {
                        //ToastUtil.show(context, "下载Apk异常");
                        enterHome();
                    }
                    //正在下载的方法，正在导入
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        //设置进度
                        mDownlaodProgressBar.setVisibility(View.VISIBLE);
                        mDownlaodProgressBar.setMax((int)total);
                        mDownlaodProgressBar.setProgress((int)current);
                        //设置百分比显示
                        if (total==current){
                            ToastUtil.show(context, "下载完成");
                            mProcessTv.setText("100%");
                        }else{
                            mProcessTv.setText(current*100/total+"%");
                        }
                    }
                });

//        RequestParams params = new RequestParams(apkUrl);
//        params.setAutoResume(true);
//        params.setSaveFilePath(getExternalCacheDir().getAbsolutePath() + "/safe.apk");
//        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<Object>() {
//
//
//            @Override
//            public void onSuccess(Object result) {
//                installApk();
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
    }
    /*安装从服务器下载的apk*/
    private void installApk() {
        //调用系统的安装器
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);//查看
        intent.addCategory(Intent.CATEGORY_DEFAULT);//添加要访问的组件类别
        intent.setDataAndType(Uri.fromFile(new File(getExternalCacheDir(), "safe1.apk")),
                "application/vnd.android.package-archive");
        startActivityForResult(intent, 6);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==6&&resultCode==RESULT_OK){
            enterHome();
        }
    }

    /*检测是否存在更新 耗时的操作，开辟子线程*/
    private void checkUpdate() {
        new Thread(){
            public void run() {
                long startTime = System.currentTimeMillis();//开始访问网络的时间
               //访问网络
                Message message = new Message();
                try {
                    URL url = new URL(Constants.SERVER_VERSION_URL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");//请求方式
                    int resultCode = conn.getResponseCode();
                    if (resultCode == HttpURLConnection.HTTP_OK){//访问网络正常
                        InputStream is = conn.getInputStream();//获取输入流
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                        String jsonResult = reader.readLine();
                        //解析json数据
                        JSONObject jsonObject = new JSONObject(jsonResult);
                        //JSONObject jsonObject = new JSONObject(response.toString());
                        newVersionCode = jsonObject.getInt("code");//获取服务端版本号
                        apkUrl = jsonObject.getString("apkurl");//获取服务端apk的url地址
                        versionDes = jsonObject.getString("des");//获取服务端的版本描述

                        //比较服务端的版本号和当前版本号
                        if (newVersionCode > getVersionCode()){
                            //显示一个是否要更新的对话框
                            message.what = MSG_SHOW_DIOLOG;
                        }else{
                           message.what = MSG_ENTER_HOME;//没有新版本，进入home界面
                        }
                    }else{
                        message.what = MSG_SERVER_ERROR;//访问服务端异常，进入HomeActivity界面
                    }
                } catch (Exception e) {
                    message.what = MSG_SERVER_ERROR;//访问服务端异常
                    e.printStackTrace();
                }finally {
                    long dulationTime = System.currentTimeMillis()-startTime;//访问网络的耗时
                    if (dulationTime < 2000){//小于2s，则休眠，显示splashActivity界面 满足2s事件
                        SystemClock.sleep(2000-dulationTime);//休眠
                    }
                    //向主线程发送消息
                    handler.sendMessage(message);
                }
            }
        }.start();
    }
    /*进入主页面：HomeActivity*/
    private void enterHome(){
        Intent intent = new Intent(context, HomeActivity.class);//跳转到HomeActivity
        startActivity(intent);
        finish();
    }

    /*初始化视图界面*/
    private void initView() {
        context = this;
        mVersionTv = (TextView)findViewById(R.id.version_tv);
        mDownlaodProgressBar = (ProgressBar)findViewById(R.id.downlaod_apk_pb);
        mProcessTv = (TextView)findViewById(R.id.process_tv);
        mVersionTv.setText("Version: "+getVersionCode());
    }
    /*获取版本号*/
    private int getVersionCode() {
        PackageManager packageManger = getPackageManager();
        try {
            /**
             * getPackageName():获取当前包的名字
             */
            PackageInfo packageInfo = packageManger.getPackageInfo(getPackageName(), packageManger
                    .GET_ACTIVITIES);
            return packageInfo.versionCode;//取得版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
