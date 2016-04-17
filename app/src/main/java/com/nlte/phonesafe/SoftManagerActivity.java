package com.nlte.phonesafe;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nlte.phonesafe.business.SoftManager;
import com.nlte.phonesafe.entity.SoftInfo;
import com.nlte.phonesafe.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class SoftManagerActivity extends AppCompatActivity implements View.OnClickListener{
    private Context mContext;
    private SoftManager mSoftManager;//软件管理业务类
    private List<SoftInfo> mdata;//数据集
    private List<SoftInfo> userSoftInfo;//用户程序列表
    private List<SoftInfo> systemSoftInfo;//系统程序列表
    private SoftManagerAdapter adapter;//适配器
    private PopupWindow mPopupWindow;//弹窗
    private LinearLayout mPwView;//弹窗的界面视图
    private AnimationSet mAnimationSet;//动画集合
    private SoftInfo softInfo;

    @ViewInject(R.id.soft_lv)
    private ListView mSoftManagerLv;//软件Info管理的ListView
    @ViewInject(R.id.count_tv)
    private TextView countTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_manager);
        ViewUtils.inject(this);
        mContext = this;
        mSoftManager = new SoftManager(mContext);
        userSoftInfo = new ArrayList<SoftInfo>();
        systemSoftInfo = new ArrayList<SoftInfo>();
        initPopupWindows();
        //填充数据
        fillData();
        //设置listview滚动监听
        mSoftManagerLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                hidePopupWindows();
                if (firstVisibleItem <= userSoftInfo.size()) {
                    countTv.setText("用户程序(" + userSoftInfo.size() + ")");
                } else {
                    countTv.setText("系统程序(" + systemSoftInfo.size() + ")");
                }
            }
        });

        //点击列表项监听
        mSoftManagerLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 || position == userSoftInfo.size() + 1) {
                    return;
                }
                if (position<=userSoftInfo.size()){
                    softInfo = userSoftInfo.get(position-1);
                }else {
                    softInfo = systemSoftInfo.get(position-userSoftInfo.size()-2);
                }
                //消失弹窗
                //hidePopupWindows();
                /**弹窗显示信息
                 * contentView: 对话框内容视图
                 */
                mPopupWindow = new PopupWindow(
                        mPwView,
                        ViewGroup.LayoutParams.WRAP_CONTENT, //要显示的宽度
                        ViewGroup.LayoutParams.WRAP_CONTENT);//要显示的高度

                // 默认弹窗没有背景，假如要实现弹创消失获者实现动画效果，则需要设置背景
                mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//背景透明
                mPopupWindow.setFocusable(true);//设置获取焦点。即有弹窗显示，则获取焦点，失去焦点，则弹窗消失
                //mPopupWindow.update();//焦点更新

                /*
                * 以下坠的方式显示弹窗
                * 即在View位置为锚点向下坠
                * anchor：锚点*/
                mPopupWindow.showAsDropDown(view, 50, -view.getHeight());
                //执行动画
                //mPwView.startAnimation(mAnimationSet);
            }
        });

    }

    //弹窗消失
    private void hidePopupWindows() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }


    /*初始化弹窗试图*/
    private void initPopupWindows() {
        //1，初始化弹窗视图
        mPwView = (LinearLayout) View.inflate(mContext, R.layout.popup_window, null);
        /*//2.弹窗界面的动画设置
        mAnimationSet = new AnimationSet(true);//是否共享特效
        //缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0, 1,
                0, 1,
                Animation.ABSOLUTE, 0,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(5000);
        scaleAnimation.setFillAfter(true);
        //scaleAnimation.setInterpolator(new BounceInterpolator());//弹簧效果

        //透明度变化动画
        *//*AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);
        scaleAnimation.setInterpolator(new BounceInterpolator());//弹簧效果*//*

        //添加动画到集合中
        mAnimationSet.addAnimation(scaleAnimation);
        //mAnimationSet.addAnimation(alphaAnimation);*/
        //初始化弹窗的点击监听事件，通过父控件来引用子控件
        int childCount = mPwView.getChildCount();//获取子控件的个数
        for (int i = 0; i<childCount; i++){
            mPwView.getChildAt(i).setOnClickListener(this);//获取点击的控件
        }
    }

    //数据
    private void fillData() {
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                mdata = mSoftManager.getAllSofts();
                //清除集合中的数据
                userSoftInfo.clear();
                systemSoftInfo.clear();
                //遍历应用程序列表
                for (SoftInfo info : mdata) {
                    if (info.isUser()) {//判断是否为用户程序
                        userSoftInfo.add(info);
                    } else {
                        systemSoftInfo.add(info);
                    }
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                //LIstView适配数据
                if (adapter == null) {
                    adapter = new SoftManagerAdapter();
                    mSoftManagerLv.setAdapter(adapter);//设置适配器
                } else {
                    adapter.notifyDataSetChanged();//通知数据发生改变
                }
                super.onPostExecute(s);
            }
        }.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.run_tv:
                startAPK();
                break;
            case R.id.unstall_tv:
                uninstallAPK();
                break;
            case R.id.share_tv:
                shareInfo();
                break;
            case R.id.detail_tv:
                //detailInfo();
                break;
            default:
                break;
        }
    }

    /*分享*/
    private void shareInfo() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Android 学习");//假如分享文本需要附加该属性
        startActivity(intent);
    }

    /*获取应用详细信息*/
    private void detailInfo() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.setData(Uri.parse("package:"+softInfo.getPackageName()));
        startActivity(intent);
    }

    /*启动应用
    * 通过包管理器获取某个包的启动入口MAIN, 再执行该意图*/
    private void startAPK() {
        PackageManager pm = getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(softInfo.getPackageName());
        if (intent!=null){//当运行的意图为空，则不执行，应有有些服务不能在界面上启动，且权限不够
            startActivity(intent);
        }else {
            ToastUtil.show(mContext, "不可启动");
        }
    }

    /*调用系统卸载器  卸载APK*/
    private void uninstallAPK() {
        if (getPackageName().equals(softInfo.getPackageName())){
            ToastUtil.show(mContext, "你傻啊");
        }else if (!softInfo.isUser()){
            ToastUtil.show(mContext, "没有权限删除系统应用");
        }else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:"+softInfo.getPackageName()));
            startActivityForResult(intent, 8);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ToastUtil.show(mContext, "requestCode: "+requestCode+" resultCode: "+resultCode);
        if (requestCode==8){
            fillData();//刷新数据，确保卸载后数据同步展示在列表上
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*构建适配器*/
    private class SoftManagerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mdata == null ? 0 : mdata.size() + 2;
        }

        @Override
        public Object getItem(int position) {
            return mdata == null ? 0 : mdata.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (position == 0) {
                TextView userSoftTv = new TextView(mContext);
                userSoftTv.setText("用户程序(" + userSoftInfo.size() + ")");
                userSoftTv.setTextSize(20);
                userSoftTv.setBackgroundColor(Color.GRAY);
                return userSoftTv;
            } else if (position == userSoftInfo.size() + 1) {
                TextView systemSoftTv = new TextView(mContext);
                systemSoftTv.setText("系统程序(" + systemSoftInfo.size() + ")");
                systemSoftTv.setTextSize(20);
                systemSoftTv.setBackgroundColor(Color.GRAY);
                return systemSoftTv;
            }
            SoftInfo softInfo = null;
            if (position < userSoftInfo.size() + 1) {
                softInfo = userSoftInfo.get(position - 1);
            } else {
                softInfo = systemSoftInfo.get(position - userSoftInfo.size() - 2);
            }
            convertView = LayoutInflater.from(mContext).inflate(R.layout.soft_manager_item, parent, false);
            //通过父控件引用子控件
            ImageView iconIv = (ImageView) convertView.findViewById(R.id.icon_iv);
            TextView nameTv = (TextView) convertView.findViewById(R.id.soft_name_tv);
            TextView versionTv = (TextView) convertView.findViewById(R.id.version_name_tv);
            TextView isSdcardTv = (TextView) convertView.findViewById(R.id.is_sdcard_tv);
            //对控件进行赋值
            iconIv.setImageDrawable(softInfo.getIcon());
            nameTv.setText(softInfo.getName());
            String version = softInfo.getVersionName();
            if (version != null && version.length() > 3) {
                version = version.substring(0, 3);
            }
            versionTv.setText(version);
            if (softInfo.isSdcard()) {
                isSdcardTv.setText("外部存储");
            } else {
                isSdcardTv.setText("内部存储");
            }
            return convertView;
        }
    }

    @Override
    protected void onDestroy() {
        hidePopupWindows();
        super.onDestroy();
    }
}
