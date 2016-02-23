package com.bob.googleplay.adapter;

import android.view.View;

/**
 * Created by Administrator on 2016/2/23.
 */

//view的持有者的基类（MVC Controller）
public abstract class BaseHolder<T> {
    //提供具体的View
    protected View mRootView;//根视图
    protected T mData;//数据
    
    public BaseHolder(){
        mRootView=initView();
        mRootView.setTag(this);
    }

    //实现View的布局
    protected abstract View initView();


    public View getRootView(){
        return mRootView;
    }

    //设置数据
    public void setData(T data) {
        //保存数据
        this.mData=data;
        //通过数据改变UI
        refreshUI(data);
    }

    //让子类来刷新
    protected abstract void refreshUI(T data);
}
