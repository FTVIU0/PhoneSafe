package com.nlte.phonesafe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nlte.phonesafe.R;

/**
 * Created by NLTE on 2016/3/21 0021.
 */
public class HomeAdapter extends BaseAdapter {
    private Context context;
    // 图片的资源id数组
    private int[] iconsId = { R.drawable.safe, R.drawable.callmsgsafe,
            R.drawable.app, R.drawable.taskmanager, R.drawable.netmanager,
            R.drawable.trojan, R.drawable.sysoptimize, R.drawable.atools,
            R.drawable.settings };
    //功能项名称的数组
    private String[] names = { "手机防盗", "通信卫士", "软件管理", "进程管理", "流量统计", "手机杀毒",
            "缓存清理", "高级工具", "设置中心" };
    public HomeAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.home_gridview_item, parent,
                false);
        // 功能名称文本框
        TextView nameTv = (TextView)itemView.findViewById(R.id.name_tv);
        ImageView iconIv = (ImageView)itemView.findViewById(R.id.icon_iv);
        //装配数据
        nameTv.setText(names[position]);
        iconIv.setImageResource(iconsId[position]);

        return itemView;
    }
}
