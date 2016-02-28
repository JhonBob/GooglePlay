package com.bob.googleplay.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.bob.googleplay.bean.CategoryBean;
import com.bob.googleplay.utils.UIUtils;

/**
 * Created by Administrator on 2016/2/28.
 */
public class CategoryTitleHolder extends BaseHolder<CategoryBean> {
    private TextView mTv;
    private int dp=UIUtils.dip2px(10);

    @Override
    protected View initView() {
        mTv=new TextView(UIUtils.getContext());
        mTv.setBackgroundColor(Color.WHITE);
        mTv.setTextColor(Color.GRAY);
        mTv.setPadding(dp,dp,dp,dp);

        return mTv;
    }

    @Override
    protected void refreshUI(CategoryBean data) {
        mTv.setText(data.title);
    }
}
