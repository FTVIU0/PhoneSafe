package com.nlte.phonesafe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nlte.phonesafe.adapter.ContactAdapter;
import com.nlte.phonesafe.business.ContactsManager;
import com.nlte.phonesafe.entity.ContactInfo;

import java.util.List;

public class ContactActivity extends AppCompatActivity {
    @ViewInject(R.id.contact_lv)
    private ListView mContactLstView;
    private List<ContactInfo> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        /**
         * 通过ListView展示批量数据
         * 1.列表项布局
         * 2.初始化布局：来自内容提供者， 联系人
         * 3.自定义适配器
         * 4。展示数据， 做点击监听列表项
         */
        ViewUtils.inject(this);

        /*2. 获取通信录中联系人的数据*/
        mData = ContactsManager.getAllContactsInfo(this);
        /*3.设置是适配器*/
        mContactLstView.setAdapter(new ContactAdapter(this, mData));
        /*点击监听列表*/
        mContactLstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //当点击列表时，把当前列表项的 电话号码数据返回给调用者
                Intent data = new Intent();
                data.putExtra("num", mData.get(position).getNum());
                setResult(RESULT_OK, data);
                finish();
            }
        });

    }
}
