package com.nlte.phonesafe;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nlte.phonesafe.adapter.BlackNumAdapter;
import com.nlte.phonesafe.db.dao.BlackNumDao;
import com.nlte.phonesafe.entity.BlackNuminfo;

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
                }
            }
        }.execute();
    }

}
