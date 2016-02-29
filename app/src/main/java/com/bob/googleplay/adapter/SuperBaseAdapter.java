package com.bob.googleplay.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bob.googleplay.Manager.ThreadManager;
import com.bob.googleplay.utils.Constans;
import com.bob.googleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/2/23.
 */

//抽取Adapter
public abstract class SuperBaseAdapter<T> extends BaseAdapter implements AdapterView.OnItemClickListener{

    //类型
    private final static int TYPE_LOAD_MORE=0;
    private final static int TYPE_NORMAL_ITEM=1;

    private List<T> mDatas;

    private LoadMoreHolder mLoadMoreHolder;
    private LoadMoreTask mLoadMoreTask;
    private AbsListView mListView;

    public SuperBaseAdapter(AbsListView listView,List<T> mDatas) {
        this.mDatas = mDatas;
        this.mListView=listView;
        mListView.setOnItemClickListener(this);

    }




    @Override
    public int getCount() {
        if (mDatas!=null){
            return mDatas.size()+1;//多加一个item
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (mDatas!=null){
            return mDatas.get(position);
        }
        return null;
    }

    //类型个数
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount()+1;//2种样式
    }

    //当前的type,返回的类型必须从0开始，而却需要累加
    @Override
    public int getItemViewType(int position) {
        if(getCount()-1==position){
            //当前为加载更多
            return TYPE_LOAD_MORE;
        }
        return getNormalItemViewType(position);
    }

    //父类默认额外的Item就只有一种，孩子可复写添加
    protected  int getNormalItemViewType(int position){
        return TYPE_NORMAL_ITEM;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;

        if (convertView==null){
            if (getItemViewType(position)==TYPE_LOAD_MORE){
                //加载更多的holder
                holder=getLoadMoreHolder();
            }else {
                //普通的holder
                holder=getItemHolder(position);
            }
            convertView=holder.getRootView();
        }
        else {
            holder=(BaseHolder)convertView.getTag();
        }

        if (getItemViewType(position)==TYPE_LOAD_MORE){
            //加载更多的holder数据
            if (hasLoadMore()){
                //去网络加载
                preformLoadMoreData();
            }else {
                mLoadMoreHolder.setData(LoadMoreHolder.STATE_EMPTY);
            }

        }else {
            //普通的holder数据
            T data=mDatas.get(position);
            holder.setData(data);

        }
        return convertView;
    }





    private void preformLoadMoreData() {
        //去网络加载数据，加载到mDates
        if (mLoadMoreTask!=null){
            //正在加载
            return;
        }

        System.out.println("加载中......");

        mLoadMoreTask=new LoadMoreTask();
        mLoadMoreHolder.setData(LoadMoreHolder.STATE_LOADING);
        ThreadManager.getLongPool().execute(mLoadMoreTask);
    }


    //默认具备加载更多，子类可复写
    protected boolean hasLoadMore(){
        return true;
    }

    private BaseHolder getLoadMoreHolder() {
        if (mLoadMoreHolder==null){
            mLoadMoreHolder=new LoadMoreHolder();
        }
        return mLoadMoreHolder;
    }


    //任务
    class LoadMoreTask implements Runnable{
        @Override
        public void run() {
            List<T> mMoreDatas=null;
            //默认
            int state=LoadMoreHolder.STATE_LOADING;
            try {
                //去网络加载数据，加载到mDates
                mMoreDatas=onLoadMoreData();
                Thread.sleep(2000);
                //数据为空,服务器没数据了
                if (mMoreDatas==null || mMoreDatas.size()==0){
                    state=LoadMoreHolder.STATE_EMPTY;
                    return;
                }
                int size=mMoreDatas.size();
                //数据不为空
                //小于页的数据量，服务器没数据了
                if (size< Constans.PAGE_SIZE){
                    state=LoadMoreHolder.STATE_EMPTY;
                }else {
                    //等于页数据，服务器有可能有更多数据
                    state=LoadMoreHolder.STATE_LOADING;
                }
            }catch (Exception e){
                e.printStackTrace();
                state=LoadMoreHolder.STATE_ERROR;
            }

            //状态传递
            final int currentState=state;
            final List<T> loadMoreDatas=mMoreDatas;
            UIUtils.post(new Runnable() {
                @Override
                public void run() {
                    if (currentState==LoadMoreHolder.STATE_ERROR){
                        Toast.makeText(UIUtils.getContext(),"服务器异常",Toast.LENGTH_SHORT).show();
                    }
                    //更新UI，Adapter
                    mLoadMoreHolder.setData(currentState);
                    if (loadMoreDatas!=null){
                        mDatas.addAll(loadMoreDatas);
                        notifyDataSetChanged();
                    }

                }
            });

            //执行完成
            mLoadMoreTask=null;

        }
    }

    //让孩子去复写（回掉）
    protected List<T> onLoadMoreData() throws Exception{
        return null;
    }

    protected abstract BaseHolder<T> getItemHolder(int position);


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //position指的是ListView的（含头），不是Adapter的,有头时要重计算position
        if (mListView instanceof ListView){
            //头数量，有几个减几
            int count= ((ListView) mListView).getHeaderViewsCount();
            //System.out.println(count);
            position=position-count;
        }


        if (getItemViewType(position)==TYPE_LOAD_MORE){
            //点击加载更多
            int currentState = mLoadMoreHolder.getCurrentState();
            if (currentState==LoadMoreHolder.STATE_ERROR){
                preformLoadMoreData();
            }
        }else {
            //点击普通item
            onNormalItemClick(parent,view, position, id);
        }
    }

    //交由孩子去复写
    protected void onNormalItemClick(AdapterView<?> parent, View view, int position, long id){}
}
