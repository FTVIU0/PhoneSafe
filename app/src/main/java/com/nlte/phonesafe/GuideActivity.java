package com.nlte.phonesafe;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.nlte.phonesafe.utils.CacheUtil;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {
    private ViewPager mGuideViewPager;
    private Context context;
    private List<ImageView> mPagerList;
    private Button mGuideEnterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        context = this;
        //获取控件
        mGuideViewPager = (ViewPager)findViewById(R.id.guide_vp);
        mGuideEnterBtn = (Button)findViewById(R.id.guide_enter_btn);

        //初始化ViewPager数据
        initPager();

        //适配器
        mGuideViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mPagerList==null?0:mPagerList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
            //视图实例化
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mPagerList.get(position));
                return mPagerList.get(position);
            }
            //视图销毁
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mPagerList.get(position));
            }
        });

        /*设置ViewPager监听*/
        mGuideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //页面滚动
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //页面选择
            @Override
            public void onPageSelected(int position) {
                if (position == mPagerList.size()-1){
                    //开始体验 出现
                    mGuideEnterBtn.setVisibility(View.VISIBLE);
                }else{
                    mGuideEnterBtn.setVisibility(View.INVISIBLE);
                }
            }
            //页面状态改变
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    /*初始化ViewPager视图页的数据*/
    private void initPager() {
        mPagerList = new ArrayList<ImageView>();
        /*往List添加图片*/
        ImageView iv1 = new ImageView(context);
        iv1.setBackgroundResource(R.drawable.guide_1);
        mPagerList.add(iv1);

        ImageView iv2 = new ImageView(context);
        iv2.setBackgroundResource(R.drawable.guide_2);
        mPagerList.add(iv2);

        ImageView iv3 = new ImageView(context);
        iv3.setBackgroundResource(R.drawable.guide_3);
        mPagerList.add(iv3);
    }
    /*开始体验 按钮点击处理事件*/
    public void systemEnter(View view){
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        CacheUtil.putBoolean(this, "is_first_use", false);
        finish();
    }
}
