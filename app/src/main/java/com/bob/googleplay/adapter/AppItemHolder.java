package com.bob.googleplay.adapter;

import android.view.View;
import android.widget.TextView;

import com.bob.googleplay.R;
import com.bob.googleplay.utils.UIUtils;

/**
 * Created by Administrator on 2016/2/23.
 */

//首页，应用，游戏页面的holder
public class AppItemHolder extends BaseHolder<String> {

    private TextView tv1;
    private TextView tv2;
    @Override
    protected View initView() {
        View view=View.inflate(UIUtils.getContext(), R.layout.item_tmp,null);
        tv1= (TextView) view.findViewById(R.id.tmp_tv_1);
        tv2= (TextView) view.findViewById(R.id.tmp_tv_2);
        return view;
    }

    @Override
    protected void refreshUI(String data) {
        //给View设置数据
        tv1.setText("头"+data);
        tv2.setText("内容"+data);
    }
}
