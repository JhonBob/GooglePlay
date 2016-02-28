package com.bob.googleplay.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.bob.googleplay.adapter.BaseHolder;
import com.bob.googleplay.adapter.SubjectHolder;
import com.bob.googleplay.adapter.SuperBaseAdapter;
import com.bob.googleplay.bean.SubjectBean;
import com.bob.googleplay.factory.ListViewFactory;
import com.bob.googleplay.http.SubjectProtocol;

import java.util.List;

/**
 * Created by Administrator on 2016/2/27.
 */

//专题页面
public class SubjectFragment extends BaseFragment {

    private SubjectProtocol mSubjectProtocol;
    private List<SubjectBean> mDatas;

    @Override
    protected View onLoadSuccessView() {
        ListView mListView = ListViewFactory.getListView();
        mListView.setAdapter(new SubjectAdapter(mListView,mDatas));
        return mListView;
    }

    @Override
    protected LoadingPager.LoadedResult onLoadingData() {
        mSubjectProtocol=new SubjectProtocol();
        try {
            mDatas = mSubjectProtocol.loadData(0);
            return checkData(mDatas);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.ERROR;
        }
    }


    private class SubjectAdapter extends SuperBaseAdapter<SubjectBean>{


        public SubjectAdapter(AbsListView listView, List<SubjectBean> mDatas) {
            super(listView, mDatas);
        }

        @Override
        protected BaseHolder<SubjectBean> getItemHolder(int position) {
            return new SubjectHolder();
        }

        @Override
        protected List<SubjectBean> onLoadMoreData() throws Exception {
            return mSubjectProtocol.loadData(mDatas.size());
        }
    }
}
