package com.bob.googleplay.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.bob.googleplay.R;
import com.bob.googleplay.adapter.AppDetailInfoHolder;
import com.bob.googleplay.bean.AppInfoBean;
import com.bob.googleplay.fragment.LoadingPager;
import com.bob.googleplay.http.AppDetailProtocol;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


public class AppDetailActivity extends AppCompatActivity {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        //加载数据
        mLoadingPager.loadData();
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
        return view;
    }
}
