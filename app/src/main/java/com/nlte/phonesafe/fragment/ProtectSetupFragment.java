package com.nlte.phonesafe.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.view.PagerAdapter;
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
import com.nlte.phonesafe.page.BasePage;
import com.nlte.phonesafe.page.Setup1Page;
import com.nlte.phonesafe.page.Setup2Page;
import com.nlte.phonesafe.page.Setup3Page;
import com.nlte.phonesafe.page.Setup4Page;

import java.util.ArrayList;
import java.util.List;

public class ProtectSetupFragment extends Fragment {
    private Context context;
    private View rootView;
    private List<BasePage> pageList;

    @ViewInject(R.id.title_bar)
    private TextView titleBar;//标题
    private String[] titleArray = {"1.显示", "2.绑定", "安全号码", "设置完成"};
    @ViewInject(R.id.setup_vp)
    private ViewPager mViewPager;//视图页，用来加载四个设置步骤页面
    @ViewInject(R.id.dot_lly)
    private LinearLayout mDotlly;//四个点的容器， 线性布局
    private int preDotPosition;
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
        rootView = inflater.inflate(R.layout.fragment_protect_setup, container, false);
        ViewUtils.inject(this, rootView);

        /*加载设置页面步骤*/
        //1. 初始化标题
        titleBar.setText(titleArray[0]);

        //2.加载ViewPager
        initPage();
        //装配数据
        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return pageList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(pageList.get(position).getRootView());
                return pageList.get(position).getRootView();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

        });
        //对ViewPager做监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //1. 加载数据页的数据 要调用BasePage.initData（）
                pageList.get(position).initdata();
                //2.设置标题
                titleBar.setText(titleArray[position]);
                //3.四个点的更新状态
                mDotlly.getChildAt(position).setEnabled(false);//设置当前点的状态
                mDotlly.getChildAt(preDotPosition).setEnabled(true);//设置当前点的状态
                preDotPosition= position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //3. 设置四个点
        return rootView;
    }

    //初始化数据
    private void initPage() {
        pageList = new ArrayList<BasePage>();
        BasePage page1 = new Setup1Page(context);
        BasePage page2 = new Setup2Page(context);
        BasePage page3 = new Setup3Page(context);
        BasePage page4 = new Setup4Page(context);

        pageList.add(page1);
        pageList.add(page2);
        pageList.add(page3);
        pageList.add(page4);
    }

}
