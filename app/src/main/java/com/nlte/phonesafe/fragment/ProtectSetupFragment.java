package com.nlte.phonesafe.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nlte.phonesafe.R;

public class ProtectSetupFragment extends Fragment {
    private Context context;
    private View rootView;

    @ViewInject(R.id.title_bar)
    private TextView titleBar;//标题
    @ViewInject(R.id.setup_vp)
    private ViewPager mViewPager;//视图页，用来加载四个设置步骤页面
    @ViewInject(R.id.dot_lly)
    private LinearLayout mDotlly;//四个点的容器， 线性布局
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
        rootView = inflater.inflate(R.layout.fragment_protect_setup1, container, false);
        ViewUtils.inject(this, rootView);

        /*加载设置页面步骤*/
        //1. 初始化标题

        //2.加载ViewPager

        //3. 设置四个点
        return rootView;
    }

}
