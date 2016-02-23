package com.bob.googleplay.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/2/23.
 */
public abstract class SuperBaseAdapter<T> extends BaseAdapter{

    private List<T> mDatas;

    public SuperBaseAdapter(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        if (mDatas!=null){
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (mDatas!=null){
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;

        if (convertView==null){
            holder=getItemHolder();
            convertView=holder.getRootView();
        }
        else {
            holder=(BaseHolder)convertView.getTag();
        }

          T data=mDatas.get(position);
          holder.setData(data);

        return convertView;
    }

    protected abstract BaseHolder<T> getItemHolder();
}
