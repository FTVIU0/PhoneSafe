package com.nlte.phonesafe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nlte.phonesafe.R;
import com.nlte.phonesafe.db.dao.BlackNumDao;
import com.nlte.phonesafe.entity.BlackNuminfo;

import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * Created by NLTE on 2016/4/10 0010.
 */
public class BlackNumAdapter extends BaseAdapter {
    private BlackNumDao blackNumDao;
    private List<BlackNuminfo> mData;//要装配的黑名单列表项数据
    private Context context;
    //构造函数
    public BlackNumAdapter(Context context, List<BlackNuminfo> mData) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //设置列表项   装配数据
        BlackNuminfo blackNuminfo = mData.get(position);
        HolderView holderView = null;
        if (convertView == null){
            holderView = new HolderView();
            convertView = LayoutInflater.from(context).inflate(R.layout.black_num_item, parent, false);
            holderView.numTv = (TextView)convertView.findViewById(R.id.num_tv);
            holderView.modeTv = (TextView)convertView.findViewById(R.id.mode_tv);
            holderView.deleteIv = (ImageView)convertView.findViewById(R.id.delete_iv);

            convertView.setTag(holderView);
        }else {
            holderView = (HolderView)convertView.getTag();
        }
        // 设置 黑名单号码
        holderView.numTv.setText(blackNuminfo.getNum());
        //拦截方式
        int mode = blackNuminfo.getMode();
        switch (mode){
            case BlackNumDao.ALL:
                holderView.modeTv.setText("全部拦截");
                break;
            case BlackNumDao.CALL:
                holderView.modeTv.setText("电话拦截");
                break;
            case BlackNumDao.SMS:
                holderView.modeTv.setText("短信拦截");
                break;
            default:
                break;
        }
        return convertView;
    }

    //用HodlerView来引用要操作的控件 取代findViewById
    private static class HolderView{
        TextView numTv;
        TextView modeTv;
        ImageView deleteIv;
    }
}

