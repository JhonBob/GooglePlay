package com.bob.googleplay.adapter;

import android.view.View;

import com.bob.googleplay.R;
import com.bob.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/2/26.
 */

//加载更多的holder
public class LoadMoreHolder extends BaseHolder<Integer> {

    public static final int STATE_LOADING=0;
    public static final int STATE_ERROR=1;
    public static final int STATE_EMPTY=2;

    @ViewInject(R.id.item_loadmore_container_loading)
    private View mLoadingView;
    @ViewInject(R.id.item_loadmore_container_retry)
    private View mErrorView;

    @Override
    protected View initView() {
        View view=View.inflate(UIUtils.getContext(), R.layout.item_load_more,null);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    protected void refreshUI(Integer data) {
        switch (data){
            case STATE_LOADING:
                mLoadingView.setVisibility(View.VISIBLE);
                mErrorView.setVisibility(View.GONE);
                break;
            case STATE_ERROR:
                mLoadingView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.VISIBLE);
                break;
            case STATE_EMPTY:
                mLoadingView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.GONE);
                break;
        }
    }

    //获取当前状态
    public int  getCurrentState() {
        return mData;
    }
}
