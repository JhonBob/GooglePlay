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
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/2/28.
 */
public class AppDetailInfoHolder extends BaseHolder<AppInfoBean> {

    @ViewInject(R.id.app_detail_info_iv_icon)
    private ImageView  mIvIcon;
    @ViewInject(R.id.app_detail_info_rb_star)
    private RatingBar mRatingBar;
    @ViewInject(R.id.app_detail_info_tv_downloadnum)
    private TextView mTvDownloadNum;
    @ViewInject(R.id.app_detail_info_tv_name)
    private TextView mTvName;
    @ViewInject(R.id.app_detail_info_tv_size)
    private TextView mTvSize;
    @ViewInject(R.id.app_detail_info_tv_time)
    private TextView mTvTime;
    @ViewInject(R.id.app_detail_info_tv_version)
    private TextView mTvVersion;

    @Override
    protected View initView() {
        View view=View.inflate(UIUtils.getContext(), R.layout.item_app_detail_info,null);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    protected void refreshUI(AppInfoBean data) {
        //设置数据
        mRatingBar.setRating(data.stars);
        mTvDownloadNum.setText(UIUtils.getString(R.string.app_detail_info_downloadnum,data.downloadNum));
        mTvName.setText(data.name);
        mTvSize.setText(UIUtils.getString(R.string.app_detail_info_size,StringUtils.formatFileSize(data.size)));
        mTvTime.setText(UIUtils.getString(R.string.app_detail_info_time,data.date));
        mTvVersion.setText(UIUtils.getString(R.string.app_detail_info_version,data.version));

        BitmapHelper.display(mIvIcon, Constans.IMAGE_BASE_URL+data.iconUrl);
    }
}
