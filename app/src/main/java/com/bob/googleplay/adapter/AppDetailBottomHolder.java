package com.bob.googleplay.adapter;


import android.app.DownloadManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import com.bob.googleplay.Manager.DownLoadManager;
import com.bob.googleplay.R;
import com.bob.googleplay.bean.AppInfoBean;
import com.bob.googleplay.bean.DownLoadInfo;
import com.bob.googleplay.utils.UIUtils;
import com.bob.googleplay.view.ProgressButton;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/2/29.
 */

//观察者
public class AppDetailBottomHolder extends BaseHolder<AppInfoBean> implements View.OnClickListener
,DownLoadManager.DownLoadObserver{
    @ViewInject(R.id.app_detail_download_btn_download)
    private ProgressButton mProgressButton;

    private DownLoadInfo mInfo;

    @Override
    protected View initView() {

        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_bottom, null);
        ViewUtils.inject(this,view);
        mProgressButton.setOnClickListener(this);
        mProgressButton.setProgressDrawable(new ColorDrawable(Color.BLUE));
        return view;
    }

    @Override
    protected void refreshUI(AppInfoBean data) {

        //用户提示
        mInfo = DownLoadManager.getInstance().getDownloadInfo(data);
        //根据状态显示信息
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

    //
    public void startObserver(){
        DownLoadManager.getInstance().addObserver(this);
        //用户提示
        mInfo = DownLoadManager.getInstance().getDownloadInfo(mData);
        //根据状态显示信息
        safeRefreshState();
    }

    public void stopObserver(){
        DownLoadManager.getInstance().deleteObserver(this);
    }

    private void refreshState(){
        int state=mInfo.state;
        switch (state){
            case DownLoadManager.STATE_UNDOWNLOAD:
                // 未下载 下载 去下载
                mProgressButton.setText("下载");
                break;
            case DownLoadManager.STATE_DOWNLOADING:
                // 下载中 显示进度条 去暂停下载
                mProgressButton.setProgressEnable(true);
                mProgressButton.setProgress((int) mInfo.progress);
                mProgressButton.setMax((int) mInfo.size);
                int progress = (int) (mInfo.progress * 100f / mInfo.size + 0.5f);
                mProgressButton.setText(progress + "%");
                // 修改进度button的背景
                //mProgressButton.setBackgroundResource(R.drawable.progress_loading_bg);
                break;
            case DownLoadManager.STATE_WAITTING:
                // 等待 等待中... 取消下载
                mProgressButton.setText("等待中...");
                break;
            case DownLoadManager.STATE_PASE:
                // 暂停 继续下载 去下载
                mProgressButton.setText("继续下载");
                break;
            case DownLoadManager.STATE_DOWNLOADED:
                // 下载成功 安装 去安装
                mProgressButton.setText("安装");
                break;
            case DownLoadManager.STATE_FAILED:
                // 下载失败 重试 去下载
                mProgressButton.setText("重试");
                break;
            case DownLoadManager.STATE_INSTALLED:
                // 已安装 打开 去启动
                mProgressButton.setText("打开");
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v==mProgressButton){
            clickProgressButton();
        }
    }

    //行为逻辑操作
    private void clickProgressButton() {
        int state=mInfo.state;
        switch (state){
            case DownLoadManager.STATE_UNDOWNLOAD:
                // 未下载 下载 去下载
                doDownLoad();
                break;
            case DownLoadManager.STATE_DOWNLOADING:
                // 下载中 显示进度条 去暂停下载
                doPause();
                break;
            case DownLoadManager.STATE_WAITTING:
                // 等待 等待中... 取消下载
                doCancel();
                break;
            case DownLoadManager.STATE_PASE:
                // 暂停 继续下载 去下载
                doDownLoad();
                break;
            case DownLoadManager.STATE_DOWNLOADED:
                // 下载成功 安装 去安装
                doInstall();
                break;
            case DownLoadManager.STATE_FAILED:
                // 下载失败 重试 去下载
                doDownLoad();
                break;
            case DownLoadManager.STATE_INSTALLED:
                // 已安装 打开 去启动
               doOpen();
                break;
            default:
                break;
        }
    }

    private void doOpen() {
        DownLoadManager.getInstance().open(mData);
    }

    private void doInstall() {
        DownLoadManager.getInstance().install(mData);
    }

    private void doCancel() {

    }

    private void doPause() {
        DownLoadManager.getInstance().pause(mData);
    }

    //下载
    public void doDownLoad(){
        //把对象传过去
        DownLoadManager.getInstance().downLoad(mData);
    }

    @Override
    public void onDownLoadStateChanged(DownLoadManager manager, DownLoadInfo info) {
        //子线程执行
        this.mInfo=info;
        safeRefreshState();
    }

    @Override
    public void onDownloadProgressChanged(DownLoadManager manager, DownLoadInfo info) {
        this.mInfo=info;
        safeRefreshState();
    }
}
