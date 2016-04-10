package com.nlte.phonesafe;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_and_sms);
        context = this;
        ViewUtils.inject(this);
        blackNumDao  = new BlackNumDao(context);
        mData = blackNumDao.getAllBlackNums();
        blackNumLstView.setAdapter(new BlackNumAdapter(context, mData));
    }
}
