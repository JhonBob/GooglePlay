package com.bob.googleplay.adapter;

import android.view.View;

import com.bob.googleplay.R;
import com.bob.googleplay.bean.AppInfoBean;
import com.bob.googleplay.utils.UIUtils;

/**
 * Created by Administrator on 2016/2/29.
 */
public class AppDetailBottomHolder extends BaseHolder<AppInfoBean> {
    @Override
    protected View initView() {

        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_bottom, null);

        return view;
    }

    @Override
    protected void refreshUI(AppInfoBean data) {

    }
}
