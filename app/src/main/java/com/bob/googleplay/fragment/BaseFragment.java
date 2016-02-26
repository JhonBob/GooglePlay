package com.bob.googleplay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.bob.googleplay.fragment.LoadingPager.LoadedResult;

import com.bob.googleplay.utils.UIUtils;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/2/19.
 */
//主页的BaseFragment(基类)
public abstract class BaseFragment extends Fragment {

    private LoadingPager loadingPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 视图共同点：加载中，空页面，错误页面
        // 视图不同点：成功后的界面

        //行为：同时只能显示一个页面，空/错误/成功
        //将共同点抽取出来View 包含：加载中/空/错误/成功

        if (loadingPager==null){

            loadingPager=new LoadingPager(UIUtils.getContext()){
                @Override
                protected View initSuccessView() {
                    return onLoadSuccessView();
                }

                @Override
                protected LoadedResult onLoadData() {
                    return onLoadingData();
                }
            };

        }else {
            ViewParent parent=loadingPager.getParent();
            if (parent!=null && parent instanceof ViewGroup){
                ((ViewGroup)parent).removeView(loadingPager);
            }
        }

        return loadingPager;
    }


    public void loadData(){
        if (loadingPager!=null){
            loadingPager.loadData();
        }

    }

    //检测服务器数据
    protected LoadedResult checkData(Object data){
        if (data==null){
            return LoadedResult.EMPTY;
        }

        if (data instanceof List){
            if (((List) data).size()==0){
                return LoadedResult.EMPTY;
            }
        }

        if (data instanceof Map){
            if (((Map) data).size()==0){
                return LoadedResult.EMPTY;
            }
        }

        return LoadedResult.SUCCESS;
    }

    protected abstract View onLoadSuccessView();
    protected abstract LoadedResult onLoadingData();

}
