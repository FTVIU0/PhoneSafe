package com.nlte.phonesafe.com.nlte.phonesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**自定义TextView
 * Created by NLTE on 2016/3/21 0021.
 */
public class FocusableTextView extends TextView {
    //在代码中调用new TextView(context)
    public FocusableTextView(Context context) {
        super(context);
    }
    //在布局中把xml控件转换为对象，则调用该构造方法
    public FocusableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public FocusableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //默认返回的是假，不获取焦点
    @Override
    public boolean isFocused() {
        return true;
    }
}
