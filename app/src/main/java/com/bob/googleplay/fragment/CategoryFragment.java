package com.bob.googleplay.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.bob.googleplay.adapter.BaseHolder;
import com.bob.googleplay.adapter.CategoryItemHolder;
import com.bob.googleplay.adapter.CategoryTitleHolder;
import com.bob.googleplay.adapter.SuperBaseAdapter;
import com.bob.googleplay.bean.CategoryBean;
import com.bob.googleplay.factory.ListViewFactory;
import com.bob.googleplay.http.CategoryProtocol;

import java.util.List;

/**
 * Created by Administrator on 2016/2/28.
 */

//分类页面
public class CategoryFragment extends BaseFragment {
    private CategoryProtocol mCategoryProtocol;
    private List<CategoryBean> mDates;
    @Override
    protected View onLoadSuccessView() {
        ListView mListView = ListViewFactory.getListView();
        mListView.setAdapter(new CategoryAdapter(mListView,mDates));
        return mListView;
    }

    @Override
    protected LoadingPager.LoadedResult onLoadingData() {
        mCategoryProtocol=new CategoryProtocol();
        try {
            mDates = mCategoryProtocol.loadData(0);
            System.out.println(mDates.toString());
            return checkData(mDates);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.ERROR;
        }
    }

    class CategoryAdapter extends SuperBaseAdapter<CategoryBean>{

        public CategoryAdapter(AbsListView listView, List<CategoryBean> mDatas) {
            super(listView, mDatas);
        }

        @Override
        protected BaseHolder<CategoryBean> getItemHolder(int position) {
            CategoryBean bean = mDates.get(position);
            if (bean.isTitle){
                return new CategoryTitleHolder();
            }else {
                return  new CategoryItemHolder();
            }
        }

        @Override
        protected boolean hasLoadMore() {
            return false;
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount()+1;
        }

        @Override
        protected int getNormalItemViewType(int position) {
            CategoryBean bean = mDates.get(position);
            if (bean.isTitle){
                return super.getNormalItemViewType(position);
            }else {
                //连贯类型
                return super.getNormalItemViewType(position)+1;
            }

        }
    }

}
