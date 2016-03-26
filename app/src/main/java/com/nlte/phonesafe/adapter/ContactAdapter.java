package com.nlte.phonesafe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nlte.phonesafe.R;
import com.nlte.phonesafe.entity.ContactInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by NLTE on 2016/3/26 0026.
 */
public class ContactAdapter extends BaseAdapter {
    private Context context;//上下文
    private List<ContactInfo> mDate;//要装配的数据
    public ContactAdapter(Context context, List<ContactInfo> date){
        this.context = context;
        this.mDate = date;
    }
    @Override
    public int getCount() {
        return mDate==null?0:mDate.size();
    }

    @Override
    public Object getItem(int position) {
        return mDate == null?null:mDate.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //设置列表项的布局  装配当前列表项的布局
        ContactInfo contactInfo = mDate.get(position);
        if (convertView == null){
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.contact_item, parent, false);
        }
        TextView nameTv = (TextView)convertView.findViewById(R.id.name_tv);
        TextView numTv = (TextView)convertView.findViewById(R.id.num_tv);
        nameTv.setText(contactInfo.getName());
        numTv.setText(contactInfo.getNum());
        return convertView;
    }
}
