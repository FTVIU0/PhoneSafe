package com.nlte.phonesafe;

import android.app.Application;

import org.xutils.x;

/**
 * Created by NLTE on 2016/3/20 0020.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);

    }
}

