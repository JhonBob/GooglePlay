package com.bob.googleplay.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.bob.googleplay.Manager.DownLoadManager;
import com.bob.googleplay.activity.AppDetailActivity;
import com.bob.googleplay.bean.AppInfoBean;
import com.bob.googleplay.bean.DownLoadInfo;
import com.bob.googleplay.utils.UIUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Administrator on 2016/2/28.
 */
public class AppListAdapter extends SuperBaseAdapter<AppInfoBean> implements DownLoadManager.DownLoadObserver{
    private List<AppInfoBean> mDatas;
    private List<AppItemHolder> mHolders=new LinkedList<>();

    public AppListAdapter(AbsListView listView, List<AppInfoBean> mDatas) {
        super(listView, mDatas);
        this.mDatas=mDatas;
    }

    @Override
    protected BaseHolder<AppInfoBean> getItemHolder(int position) {
        AppItemHolder holder=new AppItemHolder();
        mHolders.add(holder);
        return holder;
    }

    @Override
    protected void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        //跳转到应用详情界面
        Context context= UIUtils.getContext();
        Intent intent=new Intent(context, AppDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(AppDetailActivity.KEY_PACKAGENAME, mDatas.get(position).packageName);
        context.startActivity(intent);
    }

    public void startObserver(){
        DownLoadManager.getInstance().addObserver(this);
        //刷新状态
        checkStates();
    }

    private void checkStates()
    {
        ListIterator<AppItemHolder> iterator = mHolders.listIterator();
        while (iterator.hasNext())
        {
            AppItemHolder holder = iterator.next();
            holder.checkState();
        }
    }

    private void pushStateChanged(DownLoadInfo info)
    {
        ListIterator<AppItemHolder> iterator = mHolders.listIterator();
        while (iterator.hasNext())
        {
            AppItemHolder holder = iterator.next();
            holder.setDownloadInfo(info);
        }
    }



    public void stopObserver(){
        DownLoadManager.getInstance().deleteObserver(this);
    }

    @Override
    public void onDownLoadStateChanged(DownLoadManager manager, DownLoadInfo info) {
        pushStateChanged(info);
    }

    @Override
    public void onDownloadProgressChanged(DownLoadManager manager, DownLoadInfo info) {
        pushStateChanged(info);
    }
}
