package com.bob.googleplay.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.bob.googleplay.R;
import com.bob.googleplay.adapter.AppItemHolder;
import com.bob.googleplay.adapter.AppListAdapter;
import com.bob.googleplay.adapter.BaseHolder;
import com.bob.googleplay.adapter.SuperBaseAdapter;
import com.bob.googleplay.bean.AppInfoBean;
import com.bob.googleplay.factory.ListViewFactory;
import com.bob.googleplay.http.AppProtocol;
import com.bob.googleplay.utils.UIUtils;
import com.bob.googleplay.fragment.LoadingPager.LoadedResult;

import java.util.List;

/**
 * Created by Administrator on 2016/2/26.
 */
//应用页面
public class AppFragment extends BaseFragment {

    private List<AppInfoBean> mDatas;
    private AppProtocol mAppProtocol;


    @Override
    protected View onLoadSuccessView() {
        ListView mListView = ListViewFactory.getListView();
        mListView.setAdapter(new AppAdapter(mListView,mDatas));
        return mListView;
    }

    @Override
    protected LoadingPager.LoadedResult onLoadingData() {
        mAppProtocol=new AppProtocol();
        try {
            mDatas=mAppProtocol.loadData(0);
            return checkData(mDatas);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadedResult.ERROR;
        }
    }

     class AppAdapter extends AppListAdapter {

         public AppAdapter(AbsListView listView, List<AppInfoBean> mDatas) {
             super(listView, mDatas);
         }

         @Override
         protected List<AppInfoBean> onLoadMoreData() throws Exception {
             return mAppProtocol.loadData(mDatas.size());
         }
     }
}
