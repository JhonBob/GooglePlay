package com.bob.googleplay.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

import com.bob.googleplay.R;
import com.bob.googleplay.adapter.AppDetailBottomHolder;
import com.bob.googleplay.adapter.AppDetailDesHolder;
import com.bob.googleplay.adapter.AppDetailInfoHolder;
import com.bob.googleplay.adapter.AppDetailPicHolder;
import com.bob.googleplay.adapter.AppDetailSafeHolder;
import com.bob.googleplay.adapter.BaseHolder;
import com.bob.googleplay.bean.AppInfoBean;
import com.bob.googleplay.fragment.LoadingPager;
import com.bob.googleplay.http.AppDetailProtocol;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


public class AppDetailActivity extends BaseActivity {
    public static final String KEY_PACKAGENAME ="packageName" ;
    private LoadingPager mLoadingPager;
    private AppDetailProtocol mProtocol;
    private AppInfoBean mDatas;

    @ViewInject(R.id.app_detail_container_bottom)
    private FrameLayout mContainerBottom;
    @ViewInject(R.id.app_detail_container_info)
    private FrameLayout mContainerInfo;
    @ViewInject(R.id.app_detail_container_safe)
    private FrameLayout mContainerSafe;
    @ViewInject(R.id.app_detail_container_pic)
    private FrameLayout mContainerPic;
    @ViewInject(R.id.app_detail_container_des)
    private FrameLayout mContainerDes;

    @Override
    protected void initData() {
        //加载数据
        mLoadingPager.loadData();
    }

    @Override
    protected void initActionBar() {
        //初始化ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setIcon(R.drawable.ic_launcher);
        }
    }

    @Override
    protected void initView() {
        mLoadingPager= new LoadingPager(this) {
            @Override
            protected View initSuccessView() {
                return onSuccessView();
            }

            @Override
            protected LoadedResult onLoadData() {
                return performLoadingData();
            }
        };
        setContentView(mLoadingPager);
    }


    private LoadingPager.LoadedResult performLoadingData()
    {
        String packageName=getIntent().getStringExtra(KEY_PACKAGENAME);
        //实现加载数据
        mProtocol=new AppDetailProtocol(packageName);
        try {
            mDatas = mProtocol.loadData(0);
            System.out.println(mDatas);
            if (mDatas==null){
                return LoadingPager.LoadedResult.EMPTY;
            }
            return LoadingPager.LoadedResult.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.ERROR;
        }
    }

    private View onSuccessView(){
        View view=View.inflate(this,R.layout.activity_app_detail,null);
        //注入
        ViewUtils.inject(this,view);
        //挂载holder
        //1.信息部分
        AppDetailInfoHolder infoHolder=new AppDetailInfoHolder();
        mContainerInfo.addView(infoHolder.getRootView());
        infoHolder.setData(mDatas);
        //2.安全部分
        AppDetailSafeHolder safeHolder=new AppDetailSafeHolder();
        mContainerSafe.addView(safeHolder.getRootView());
        safeHolder.setData(mDatas.safe);
        //3.图片信息
        AppDetailPicHolder picHolder=new AppDetailPicHolder();
        mContainerPic.addView(picHolder.getRootView());
        picHolder.setData(mDatas.screen);
        //4.描述信息
        AppDetailDesHolder desHolder=new AppDetailDesHolder();
        mContainerDes.addView(desHolder.getRootView());
        desHolder.setData(mDatas);
        //5.下载部分
        AppDetailBottomHolder bottomHolder=new AppDetailBottomHolder();
        mContainerBottom.addView(bottomHolder.getRootView());
        bottomHolder.setData(mDatas);

        return view;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            //如果返回true,自己响应
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
