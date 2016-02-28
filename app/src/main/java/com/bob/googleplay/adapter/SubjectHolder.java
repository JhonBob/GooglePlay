package com.bob.googleplay.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bob.googleplay.R;
import com.bob.googleplay.bean.SubjectBean;
import com.bob.googleplay.utils.BitmapHelper;
import com.bob.googleplay.utils.Constans;
import com.bob.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/2/27.
 */
public class SubjectHolder extends BaseHolder<SubjectBean> {

    @ViewInject(R.id.item_subject_iv_icon)
    private ImageView mIvIcon;
    @ViewInject(R.id.item_subject_iv_title)
    private TextView mTitle;

    @Override
    protected View initView() {
        View view=View.inflate(UIUtils.getContext(), R.layout.item_subject,null);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    protected void refreshUI(SubjectBean data) {
        mTitle.setText(data.des);
        mIvIcon.setImageResource(R.drawable.ic_default);
        BitmapHelper.display(mIvIcon, Constans.IMAGE_BASE_URL+data.url);
    }
}
