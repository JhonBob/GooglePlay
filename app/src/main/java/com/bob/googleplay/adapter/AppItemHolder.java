package com.bob.googleplay.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bob.googleplay.R;
import com.bob.googleplay.bean.AppInfoBean;
import com.bob.googleplay.utils.BitmapHelper;
import com.bob.googleplay.utils.Constans;
import com.bob.googleplay.utils.StringUtils;
import com.bob.googleplay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/2/23.
 */

//首页，应用，游戏页面的holder
public class AppItemHolder extends BaseHolder<AppInfoBean> {

//    private TextView tv1;
//    private TextView tv2;
    @ViewInject(R.id.item_appinfo_iv_icon)
    private ImageView mIvIcon;
    @ViewInject(R.id.item_appinfo_rb_stars)
    private RatingBar mRbStar;
    @ViewInject(R.id.item_appinfo_tv_des)
    private TextView mTvDes;
    @ViewInject(R.id.item_appinfo_tv_size)
    private TextView mTvSize;
    @ViewInject(R.id.item_appinfo_tv_title)
    private TextView mTvTitle;


    @Override
    protected View initView() {
        View view=View.inflate(UIUtils.getContext(), R.layout.item_app_info,null);
//        tv1= (TextView) view.findViewById(R.id.tmp_tv_1);
//        tv2= (TextView) view.findViewById(R.id.tmp_tv_2);
        //使用注入
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    protected void refreshUI(AppInfoBean data) {
        //给View设置数据
//        tv1.setText("头"+data);
//        tv2.setText("内容"+data);
        //设置数据
        mTvDes.setText(data.des);
        mTvSize.setText(StringUtils.formatFileSize(data.size));
        mTvTitle.setText(data.name);
        mRbStar.setRating(data.stars);

        mIvIcon.setImageResource(R.drawable.ic_default);//默认
        //需要去网络获取
        String url= Constans.IMAGE_BASE_URL+data.iconUrl;
        BitmapHelper.display(mIvIcon, url);
    }
}
