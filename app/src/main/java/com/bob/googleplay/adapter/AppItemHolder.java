package com.bob.googleplay.adapter;

import android.app.DownloadManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bob.googleplay.Manager.DownLoadManager;
import com.bob.googleplay.R;
import com.bob.googleplay.bean.AppInfoBean;
import com.bob.googleplay.bean.DownLoadInfo;
import com.bob.googleplay.utils.BitmapHelper;
import com.bob.googleplay.utils.Constans;
import com.bob.googleplay.utils.StringUtils;
import com.bob.googleplay.utils.UIUtils;
import com.bob.googleplay.view.ProgressCircleView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/2/23.
 */

//首页，应用，游戏页面的holder
public class AppItemHolder extends BaseHolder<AppInfoBean> implements View.OnClickListener {

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
    @ViewInject(R.id.item_appinfo_pcv_progress)
    private ProgressCircleView mCircleView;

    private DownLoadInfo mInfo;


    @Override
    protected View initView() {
        View view=View.inflate(UIUtils.getContext(), R.layout.item_app_info,null);
//        tv1= (TextView) view.findViewById(R.id.tmp_tv_1);
//        tv2= (TextView) view.findViewById(R.id.tmp_tv_2);
        //使用注入
        ViewUtils.inject(this,view);

        mCircleView.setOnClickListener(this);
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
        checkState();


    }

    public void checkState() {
        mInfo = DownLoadManager.getInstance().getDownloadInfo(mData);
        safeRefreshState();
    }


    private void safeRefreshState(){
        UIUtils.post(new Runnable() {
            @Override
            public void run() {
                refreshState();
            }
        });
    }

    private void refreshState(){
        int state=mInfo.state;
        switch (state){
            case DownLoadManager.STATE_UNDOWNLOAD:
                // 未下载 下载 去下载
                mCircleView.setProgressText("下载");
                mCircleView.setProgressIcon(R.drawable.ic_download);
                break;
            case DownLoadManager.STATE_DOWNLOADING:
                // 下载中 显示进度条 去暂停下载
                mCircleView.setProgressEnable(true);
                mCircleView.setProgress((int) mInfo.progress);
                mCircleView.setMax((int) mInfo.size);
                int progress = (int) (mInfo.progress * 100f / mInfo.size + 0.5f);
                mCircleView.setProgressText(progress + "%");
                mCircleView.setProgressIcon(R.drawable.ic_pause);
                // 修改进度button的背景
                //mProgressButton.setBackgroundResource(R.drawable.progress_loading_bg);
                break;
            case DownLoadManager.STATE_WAITTING:
                // 等待 等待中... 取消下载
                mCircleView.setProgressText("等待中...");
                mCircleView.setProgressIcon(R.drawable.ic_pause);
                break;
            case DownLoadManager.STATE_PASE:
                // 暂停 继续下载 去下载
                mCircleView.setProgressText("继续下载");
                mCircleView.setProgressIcon(R.drawable.ic_download);
                break;
            case DownLoadManager.STATE_DOWNLOADED:
                // 下载成功 安装 去安装
                mCircleView.setProgressText("安装");
                mCircleView.setProgressIcon(R.drawable.ic_resume);
                break;
            case DownLoadManager.STATE_FAILED:
                // 下载失败 重试 去下载
                mCircleView.setProgressText("重试");
                mCircleView.setProgressIcon(R.drawable.ic_redownload);
                break;
            case DownLoadManager.STATE_INSTALLED:
                // 已安装 打开 去启动
                mCircleView.setProgressText("打开");
                mCircleView.setProgressIcon(R.drawable.ic_install);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mCircleView)
        {
            clickProgressButton();
        }
    }

    private void clickProgressButton()
    {
        int state = mInfo.state;

        switch (state)
        {
            case DownLoadManager.STATE_UNDOWNLOAD:
                // 未下载 下载 去下载
                doDownload();
                break;
            case DownLoadManager.STATE_DOWNLOADING:
                // 下载中 显示进度条 去暂停下载
                doPause();
                break;
            case DownLoadManager.STATE_WAITTING
                    :
                // 等待 等待中... 取消下载
                doCancel();
                break;
            case DownLoadManager.STATE_PASE:
                // 暂停 继续下载 去下载
                doDownload();
                break;
            case DownLoadManager.STATE_DOWNLOADED:
                // 下载成功 安装 去安装
                doInstall();
                break;
            case DownLoadManager.STATE_FAILED:
                // 下载失败 重试 去下载
                doDownload();
                break;
            case DownLoadManager.STATE_INSTALLED:
                // 已安装 打开 去启动
                doOpen();
                break;
            default:
                break;
        }
    }


    // 下载
    private void doDownload()
    {
        Toast.makeText(UIUtils.getContext(), "下载应用", Toast.LENGTH_SHORT).show();
        DownLoadManager.getInstance().downLoad(mData);
    }

    private void doPause()
    {
        DownLoadManager.getInstance().pause(mData);
    }

    private void doCancel()
    {
       // DownLoadManager.getInstance().
    }

    private void doInstall()
    {
        DownLoadManager.getInstance().install(mData);
    }

    private void doOpen()
    {
        DownLoadManager.getInstance().open(mData);
    }

//    public void setDownloadInfo(DownLoadInfo info) {
//        this.mInfo = info;
//        safeRefreshState();
//    }

    public void setDownloadInfo(DownLoadInfo info)
    {
        if (info.packageName.equals(mData.packageName))
        {
            this.mInfo = info;
            safeRefreshState();
        }
    }
}
