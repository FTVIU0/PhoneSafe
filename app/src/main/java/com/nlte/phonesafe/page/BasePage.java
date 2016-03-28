package com.nlte.phonesafe.page;

import android.content.Context;
import android.view.View;

/**
 * Created by NLTE on 2016/3/29 0029.
 */
public abstract class BasePage {
    public Context context;
    public View rootView;
    public BasePage(Context context){
        this.context = context;
        rootView = initView();//构造该对象，初始化界面
    }
    public abstract View initView();//初始化界面
    public abstract void initdata();//初始化数据
    public View getRootView(){
        return rootView;
    }
}

