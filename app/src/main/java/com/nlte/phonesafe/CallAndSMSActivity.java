package com.nlte.phonesafe;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nlte.phonesafe.adapter.BlackNumAdapter;
import com.nlte.phonesafe.db.dao.BlackNumDao;
import com.nlte.phonesafe.entity.BlackNuminfo;
import com.nlte.phonesafe.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class CallAndSMSActivity extends AppCompatActivity {
    @ViewInject(R.id.blackNum_lstview)
    private ListView blackNumLstView;
    @ViewInject(R.id.progress_pb)
    private ProgressBar progressBar;
    private Context context;
    private List<BlackNuminfo> mData;//要装配的黑名单列表项数据
    private BlackNumDao blackNumDao;
    private BlackNumAdapter adapter;
    private static final int LIMIT = 10;//查询记录的限制，最多为LIMIT条
    private static int startIndex =0;//起始点
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_and_sms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        ViewUtils.inject(this);
        fillData();
        blackNumLstView.setOnScrollListener(new AbsListView.OnScrollListener() {
            /**
             * 当滚动状态改变时，回调该方法
             * view：ListView
             * scrollState：滚动的状态
             * SCROLL_STATE_FLING: 快速滚动 ，惯性滚动，手指不触摸屏幕
             * SCROLL_STATE_IDLE:闲置状态，停顿状态 ，没有滚动，停止
             * SCROLL_STATE_TOUCH_SCROLL:触摸滚动，慢速滚动
             */
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 依据滚动的状态来分批加载数据
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    //当处于停顿状态加载数据
                    int lastVisiblePosition = blackNumLstView.getLastVisiblePosition();//取得最后一个可见的数据项
                    int size = mData.size();
                    if (lastVisiblePosition == size-1){
                        startIndex = LIMIT + startIndex;
                        //分批加载数据
                        fillData();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void fillData() {
        /*异步任务类*/
        new AsyncTask<String, Integer, String>() {
            //在执行工作线程之前调用，用于访问工作线程之前的初始化操作
            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            //在后台，在子线程，在工作线程运行
            @Override
            protected String doInBackground(String... params) {
                blackNumDao = new BlackNumDao(context);
                if (mData==null){
                    mData = blackNumDao.getPartBlackNums(LIMIT, startIndex);
                }else {
                    if (blackNumDao.getPartBlackNums(LIMIT, startIndex)!= null){
                        mData.addAll(blackNumDao.getPartBlackNums(LIMIT, startIndex));
                    }
                }
                return null;
            }

            //在工作线程执行完后
            @Override
            protected void onPostExecute(String s) {
                progressBar.setVisibility(View.INVISIBLE);
                //适配器
                if (adapter == null){
                    adapter = new BlackNumAdapter(context, mData);
                    blackNumLstView.setAdapter(adapter);
                }else {
                    adapter.setData(mData);
                    adapter.notifyDataSetChanged();//通知数据集发生改变
                }
            }
        }.execute();
    }
    //创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //菜单项监听处理事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                showAddDialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //展示添黑名单对话框
    private void showAddDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_blacknum_add, null);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        dialog.show();
        //获取控件
        final EditText blackNumEt = (EditText)view.findViewById(R.id.add_num_et);
        Button addBtn = (Button)view.findViewById(R.id.add_btn);
        Button cancelBtn = (Button)view.findViewById(R.id.cancel_btn);
        final RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.mode_rg);
        //添加按钮点击监听事件
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mode = -1;
                String blackNum = blackNumEt.getText().toString().trim();
                blackNumDao = new BlackNumDao(context);
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                switch (checkedRadioButtonId){
                    case R.id.all_rb:
                        mode = Constants.ALL;
                        break;
                    case R.id.call_rb:
                        mode = Constants.CALL;
                        break;
                    case R.id.sms_rb:
                        mode = Constants.SMS;
                        break;
                    default:
                        break;
                }
                blackNumDao.add(blackNum, mode);
                mData.add(new BlackNuminfo(blackNum, mode));
                /*notifyDataSetChanged()
                 *添加或者修改数据，notifyDataSetChanged()可以在修改适配器绑定的数组后，
                 *不用重新刷新Activity，通知Activity更新ListView
                 * */
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        //取消按钮点击监听事件
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
