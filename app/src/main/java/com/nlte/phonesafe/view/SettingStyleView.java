package com.nlte.phonesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nlte.phonesafe.R;
import com.nlte.phonesafe.utils.CacheUtil;

/**自定义组合控件
 * Created by NLTE on 2016/3/22 0022.
 */
public class SettingStyleView extends LinearLayout {
    private TextView mTitleTv;//标题文本控件
    private TextView mDesTv;//描述
    private View rootView;//组合自定义控件界面根节点对象
    private String title;
    private String des_on;

    /*1 把自定义组合控件的xml文件实例化为对象，并且添加到当前对象中，作为当前控件的子控件*/
    /*2 自定义方法：操纵组合控件的自控件*/
    public SettingStyleView(Context context) {
        super(context);
        init();
    }
    //布局xml实例化调用
    public SettingStyleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

        //通过命名空间和属性名来获取属性值
        title = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto",
                "setting_title");
        des_on = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto",
                "setting_des_on");
        //初始化自定义控件属性值
        mTitleTv.setText(title);
        mDesTv.setText(des_on);
    }

    public SettingStyleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    /*初始化自定义组合控件界面*/
    private void init() {
        rootView = View.inflate(getContext(), R.layout.setting_style_view, this);
        mTitleTv = (TextView)rootView.findViewById(R.id.title_tv);
        mDesTv = (TextView)rootView.findViewById(R.id.des_tv);
    }
    /*自定义方法*/
    //设置组合控件标题
    public void setTitle(String title){
        mTitleTv.setText(title);
    }
    //设置组合控件描述
    public void setDes(String des){
        mDesTv.setText(des);
    }
}
