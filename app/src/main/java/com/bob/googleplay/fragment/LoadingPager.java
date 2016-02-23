package com.bob.googleplay.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.bob.googleplay.Manager.ThreadManager;
import com.bob.googleplay.R;
import com.bob.googleplay.utils.UIUtils;

/**
 * Created by Administrator on 2016/2/19.
 */
//调度分配处理
public  abstract class LoadingPager extends FrameLayout {

    private final static int STATE_NONE=0;//默认
    private final static int STATE_LOADING=1;//加载中
    private final static int STATE_EMPTY=2;//空
    private final static int STATE_ERROR=3;//错误
    private final static int STATE_SUCCESS=4;//加载成功

    private View mLoadingView;
    private View mEmptyView;
    private View mErrorView;
    private View mSuccessView;
    private View mRetryView;

    private int mCurrentState=STATE_NONE;

    public LoadingPager(Context context) {
        super(context);
        initView();
    }

    public LoadingPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    //枚举的构造函数必须是private
    public enum LoadedResult{
        EMPTY(STATE_EMPTY),ERROR(STATE_ERROR),SUCCESS(STATE_SUCCESS);

        private int state;
        private LoadedResult(int state)
        {
            this.state=state;
        }

        public int getState() {
            return state;
        }
    }

    private void initView(){
        //加载（加载中/空/错误/成功）

        //1.加载中
        if (mLoadingView==null){
            mLoadingView=View.inflate(getContext(), R.layout.pager_loading,null);
            addView(mLoadingView);
        }

        //2.空页面
        if (mEmptyView==null){
            mEmptyView=View.inflate(getContext(), R.layout.pager_empty,null);
            addView(mEmptyView);
        }

        //3.错误页面
        if (mErrorView==null){
            mErrorView=View.inflate(getContext(), R.layout.pager_error,null);
            addView(mErrorView);
            //处理点击重试
            mRetryView=mErrorView.findViewById(R.id.error_btn_retry);
            mRetryView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadData();
                }
            });
        }

        //成功页面等数据加载成功后添加
        safeUpdateUI();
    }

    private void upstateUI() {
        mLoadingView.setVisibility(mCurrentState==STATE_NONE || mCurrentState == STATE_LOADING ? VISIBLE : GONE);
        mEmptyView.setVisibility(mCurrentState==STATE_EMPTY?VISIBLE:GONE);
        mErrorView.setVisibility(mCurrentState==STATE_ERROR?VISIBLE:GONE);

        if (mCurrentState==STATE_SUCCESS && mSuccessView==null){
            //需要成功创造的View
            mSuccessView=initSuccessView();
            addView(mSuccessView);
        }

        //成功的view
        if(mSuccessView!=null){
            mSuccessView.setVisibility(mCurrentState==STATE_SUCCESS?VISIBLE:GONE);
        }
    }

    private void safeUpdateUI(){
        UIUtils.post(new Runnable() {
            @Override
            public void run() {
                upstateUI();
            }
        });
    }

    //加载数据
    public void loadData(){
        //如果成功就不去加载，如果正在加载说明已开了线程
        if (mCurrentState!=STATE_SUCCESS && mCurrentState!=STATE_LOADING){
            mCurrentState=STATE_LOADING;
            safeUpdateUI();
            //交由线程池管理任务
            ThreadManager.getLongPool().execute(new LoadDataTask());
        }

    }

    class LoadDataTask implements Runnable{
        @Override
        public void run() {
            //加载数据成功没有
            LoadedResult result=onLoadData();
            //view切换
            mCurrentState=result.getState();
            //子线程中的UI操作
            safeUpdateUI();
        }
    }

    //子类实现
    protected abstract View initSuccessView();
    protected abstract LoadedResult onLoadData();
}
