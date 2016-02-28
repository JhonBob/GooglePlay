package com.bob.googleplay.fragment;

import android.content.Loader;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.bob.googleplay.adapter.AppItemHolder;
import com.bob.googleplay.adapter.AppListAdapter;
import com.bob.googleplay.adapter.BaseHolder;
import com.bob.googleplay.adapter.SuperBaseAdapter;
import com.bob.googleplay.bean.AppInfoBean;
import com.bob.googleplay.factory.ListViewFactory;
import com.bob.googleplay.http.GameProtocol;
import com.bob.googleplay.fragment.LoadingPager.LoadedResult;

import java.util.List;

/**
 * Created by Administrator on 2016/2/27.
 */
//游戏界面
public class GameFragment extends BaseFragment{

    private GameProtocol mGameProtocol;
    private List<AppInfoBean> mDatas;




    @Override
    protected View onLoadSuccessView() {
        ListView mListView = ListViewFactory.getListView();
        mListView.setAdapter(new GameAdapter(mListView,mDatas));
        return mListView;
    }

    @Override
    protected LoadedResult onLoadingData() {
        mGameProtocol=new GameProtocol();
        try {
            mDatas=mGameProtocol.loadData(0);
            return checkData(mDatas);
        } catch (Exception e) {
            e.printStackTrace();
           return LoadedResult.ERROR;
        }

    }


    class GameAdapter extends AppListAdapter {

        public GameAdapter(AbsListView listView, List<AppInfoBean> mDatas) {
            super(listView, mDatas);
        }

        @Override
        protected List<AppInfoBean> onLoadMoreData() throws Exception {
            return mGameProtocol.loadData(mDatas.size());
        }
    }
}
