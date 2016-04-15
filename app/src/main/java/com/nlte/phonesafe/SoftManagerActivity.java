package com.nlte.phonesafe;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nlte.phonesafe.business.SoftManager;
import com.nlte.phonesafe.entity.SoftInfo;

import java.util.ArrayList;
import java.util.List;

public class SoftManagerActivity extends AppCompatActivity {
    private Context mContext;
    private SoftManager mSoftManager;//软件管理业务类
    private List<SoftInfo> mdata;//数据集
    private List<SoftInfo> userSoftInfo;//用户程序列表
    private List<SoftInfo> systemSoftInfo;//系统程序列表
    private SoftManagerAdapter adapter;//适配器

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
        //填充数据
        fillData();
        //设置listview滚动监听
        mSoftManagerLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem <= userSoftInfo.size()){
                    countTv.setText("用户程序("+userSoftInfo.size()+")");
                }else {
                    countTv.setText("系统程序("+systemSoftInfo.size()+")");
                }
            }
        });

        //点击列表项监听
        mSoftManagerLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0||position==userSoftInfo.size()+1){
                    return;
                }
                /**弹窗显示信息
                 * contentView: 对话框内容视图
                 */
                View view1 = View.inflate(mContext, R.layout.popup_window, null);
                PopupWindow popupWindow = new PopupWindow(
                        view1,
                        ViewGroup.LayoutParams.WRAP_CONTENT, //要显示的宽度
                        ViewGroup.LayoutParams.WRAP_CONTENT);//要显示的高度
                /*
                * 以下坠的方式显示弹窗
                * anchor：锚点*/
                popupWindow.showAsDropDown(view);
            }
        });

    }

    //数据
    private void fillData() {
        new AsyncTask<String, Integer, String>(){
            @Override
            protected String doInBackground(String... params) {
                mdata = mSoftManager.getAllSofts();
                //遍历应用程序列表
                for (SoftInfo info:mdata){
                    if (info.isUser()){//判断是否为用户程序
                        userSoftInfo.add(info);
                    }else {
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
                if (adapter == null){
                    adapter = new SoftManagerAdapter();
                    mSoftManagerLv.setAdapter(adapter);//设置适配器
                }else {
                    adapter.notifyDataSetChanged();//通知数据发生改变
                }
                super.onPostExecute(s);
            }
        }.execute();
    }
    private class SoftManagerAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mdata==null?0:mdata.size()+2;
        }

        @Override
        public Object getItem(int position) {
            return mdata==null?0:mdata.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (position == 0){
                TextView userSoftTv = new TextView(mContext);
                userSoftTv.setText("用户程序(" + userSoftInfo.size() + ")");
                userSoftTv.setTextSize(20);
                userSoftTv.setBackgroundColor(Color.GRAY);
                return userSoftTv;
            }else if (position==userSoftInfo.size()+1){
                TextView systemSoftTv = new TextView(mContext);
                systemSoftTv.setText("系统程序(" + systemSoftInfo.size() + ")");
                systemSoftTv.setTextSize(20);
                systemSoftTv.setBackgroundColor(Color.GRAY);
                return systemSoftTv;
            }
            SoftInfo softInfo = null;
            if (position < userSoftInfo.size()+1){
                softInfo = userSoftInfo.get(position-1);
            }else {
                softInfo = systemSoftInfo.get(position-userSoftInfo.size()-2);
            }
            convertView = LayoutInflater.from(mContext).inflate(R.layout.soft_manager_item, parent, false);
            //通过父控件引用子控件
            ImageView iconIv = (ImageView)convertView.findViewById(R.id.icon_iv);
            TextView nameTv = (TextView) convertView.findViewById(R.id.soft_name_tv);
            TextView versionTv = (TextView)convertView.findViewById(R.id.version_name_tv);
            TextView isSdcardTv = (TextView)convertView.findViewById(R.id.is_sdcard_tv);
            //对控件进行赋值
            iconIv.setImageDrawable(softInfo.getIcon());
            nameTv.setText(softInfo.getName());
            String version = softInfo.getVersionName();
            if (version!=null&&version.length()>3){
                version = version.substring(0,3);
            }
            versionTv.setText(version);
            if (softInfo.isSdcard()){
                isSdcardTv.setText("外部存储");
            }else {
                isSdcardTv.setText("内部存储");
            }
            return convertView;
        }
    }
}
