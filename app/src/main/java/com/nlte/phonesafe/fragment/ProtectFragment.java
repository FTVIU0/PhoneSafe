package com.nlte.phonesafe.fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nlte.phonesafe.R;
import com.nlte.phonesafe.utils.CacheUtil;

public class ProtectFragment extends Fragment {
    @ViewInject(R.id.safe_num_tv)
    private TextView safeNumTv;//安全号码的文本框
    @ViewInject(R.id.protected_iv)
    private ImageView lockIv;//加锁图片
    @OnClick(R.id.resetup)
    public void resetup(View view){//对重新设置 按钮 ，设置点击监听事件
        //进入到设置片段
        ProtectSetupFragment protectSetupFragment = new ProtectSetupFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.contentActivity, protectSetupFragment, "protectSetupFragment")
                .commit();
    }
    private Context context;
    private View rootView;//片段的界面视图的根节点对象
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_protect, container, false);
        ViewUtils.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        //初始化手机安全号码
        safeNumTv.setText(CacheUtil.getString(context, CacheUtil.SAFE_NUM));
        safeNumTv.setEnabled(false);//不可用

        //初始化是否 开启手机防盗功能
        if(CacheUtil.getBoolean(context, CacheUtil.IS_PROTECT)){
            lockIv.setImageResource(R.drawable.lock);
        }else{
            lockIv.setImageResource(R.drawable.unlock);
        }
        super.onStart();
    }
}
