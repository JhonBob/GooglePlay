package com.bob.googleplay.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.bob.googleplay.activity.AppDetailActivity;
import com.bob.googleplay.bean.AppInfoBean;
import com.bob.googleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/2/28.
 */
public class AppListAdapter extends SuperBaseAdapter<AppInfoBean> {
    private List<AppInfoBean> mDatas;

    public AppListAdapter(AbsListView listView, List<AppInfoBean> mDatas) {
        super(listView, mDatas);
        this.mDatas=mDatas;
    }

    @Override
    protected BaseHolder<AppInfoBean> getItemHolder(int position) {
        return new AppItemHolder();
    }

    @Override
    protected void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        //跳转到应用详情界面
        Context context= UIUtils.getContext();
        Intent intent=new Intent(context, AppDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(AppDetailActivity.KEY_PACKAGENAME,mDatas.get(position).packageName);
        context.startActivity(intent);
    }
}
