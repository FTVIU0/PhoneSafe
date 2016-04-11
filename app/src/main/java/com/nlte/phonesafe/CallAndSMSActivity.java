package com.nlte.phonesafe;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_and_sms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        ViewUtils.inject(this);
        fillData();
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
                blackNumDao  = new BlackNumDao(context);
                mData = blackNumDao.getAllBlackNums();
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
                    adapter.notifyDataSetChanged();//通知数据集发生改变
                }
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

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
        final EditText blackNumEt = (EditText)view.findViewById(R.id.add_num_et);
        Button addBtn = (Button)view.findViewById(R.id.add_btn);
        Button cancelBtn = (Button)view.findViewById(R.id.cancel_btn);
        final RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.mode_rg);
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
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
