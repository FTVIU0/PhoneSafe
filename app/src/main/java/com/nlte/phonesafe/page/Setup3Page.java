package com.nlte.phonesafe.page;

import android.content.Context;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.nlte.phonesafe.R;

/**
 * Created by NLTE on 2016/3/29 0029.
 */
public class Setup3Page extends BasePage {
    public Setup3Page(Context context){
        super(context);
    }
    @Override
    public View initView() {
        rootView = View.inflate(context, R.layout.fragment_protect_setup3, null);
        ViewUtils.inject(this, rootView);
        return rootView;
    }

    @Override
    public void initdata() {

    }
}
