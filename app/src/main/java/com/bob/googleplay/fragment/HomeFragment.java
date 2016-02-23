package com.bob.googleplay.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.ListView;

import com.bob.googleplay.adapter.AppItemHolder;
import com.bob.googleplay.adapter.BaseHolder;
import com.bob.googleplay.adapter.SuperBaseAdapter;
import com.bob.googleplay.fragment.LoadingPager.LoadedResult;
import com.bob.googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/19.
 */
//主界面
public class HomeFragment extends BaseFragment {

    private List<String> mDatas;//假数据模拟

    @Override
    protected View onLoadSuccessView() {
        ListView mListView=new ListView(UIUtils.getContext());
        mListView.setCacheColorHint(Color.TRANSPARENT);
        mListView.setSelector(android.R.color.transparent);
        mListView.setDividerHeight(0);
        mListView.setScrollingCacheEnabled(false);

        mListView.setAdapter(new HomeAdapter(mDatas));

        return mListView;
    }

    //永远优先于onLoadSuccessView()
    @Override
    protected LoadedResult onLoadingData(){
        //模拟随机数
//        LoadedResult[] results=new LoadedResult[]{
//                LoadedResult.EMPTY,LoadedResult.ERROR,LoadedResult.SUCCESS
//        };
//
//        Random rdm=new Random();
//        try{
//            Thread.sleep(1000);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return results[rdm.nextInt(results.length)];

        //模拟假数据
        mDatas=new ArrayList<>();
        for (int i=0;i<50;i++){
            mDatas.add(i+"");
        }

        //去网络加载数据

        return LoadedResult.SUCCESS;
    }

    //适配器
    class HomeAdapter extends SuperBaseAdapter<String>{

        public HomeAdapter(List<String> mDatas) {
            super(mDatas);
        }

        @Override
        protected BaseHolder<String> getItemHolder() {
            return new AppItemHolder();
        }
    }

}
