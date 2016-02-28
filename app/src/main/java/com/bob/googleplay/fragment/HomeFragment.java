package com.bob.googleplay.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;


import com.bob.googleplay.adapter.AppItemHolder;
import com.bob.googleplay.adapter.AppListAdapter;
import com.bob.googleplay.adapter.BaseHolder;
import com.bob.googleplay.adapter.HomePictureHolder;
import com.bob.googleplay.adapter.SuperBaseAdapter;
import com.bob.googleplay.bean.AppInfoBean;
import com.bob.googleplay.bean.HomeBean;
import com.bob.googleplay.factory.ListViewFactory;
import com.bob.googleplay.fragment.LoadingPager.LoadedResult;
import com.bob.googleplay.http.HomeProtocol;

import java.util.List;

/**
 * Created by Administrator on 2016/2/19.
 */
//主界面
public class HomeFragment extends BaseFragment {

    //private List<String> mDatas;//假数据模拟
    private List<AppInfoBean> mDatas;//listView对应的数据
    private List<String> mPictures;//轮播图对应的数据
    private  HomeProtocol mProtocol;//网络协议

    @Override
    protected View onLoadSuccessView() {
        ListView mListView = ListViewFactory.getListView();

        //创建轮播Holder
        HomePictureHolder holder=new HomePictureHolder();
        //加头
        mListView.addHeaderView(holder.getRootView());
        //holder设置数据
        holder.setData(mPictures);

        mListView.setAdapter(new HomeAdapter(mListView,mDatas));
        return mListView;
    }

    //永远优先于onLoadSuccessView()，此方法是在线程中执行的
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
//        mDatas=new ArrayList<>();
//        for (int i=0;i<50;i++){
//            mDatas.add(i+"");
//        }

        //去网络加载数据
//        try {
//            HttpUtils utils=new HttpUtils();
//            //method,url,header,params(send异步，新开线程，sendSync同步)
//            String url="http://10.0.3.2:8080/WebInfos/app/homelist0";
//            ResponseStream stream=utils.sendSync(HttpRequest.HttpMethod.GET, url, null);
//            //响应码
//            int statusCode=stream.getStatusCode();
//            if (200==statusCode){
//                //访问成功
//                //获取json字符串
//                String json=stream.readString();
//                //解析JSON
//                Gson gson=new Gson();
//                HomeBean bean=gson.fromJson(json, HomeBean.class);
//                LoadedResult result=checkData(bean);
//                if (result!=LoadedResult.SUCCESS){
//                   return LoadedResult.EMPTY;
//                }
//                result=checkData(bean.list);
//
//                if (result!=LoadedResult.SUCCESS){
//                    return LoadedResult.EMPTY;
//                }
//
//                //拿到数据
//                mDatas=bean.list;
//                mPictures=bean.picture;
//                return result;
//            }else {
//                //访问失败
//                return LoadedResult.ERROR;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            //联网失败
//            return LoadedResult.ERROR;
//        }

        //网络操作的简单抽取
         mProtocol = new HomeProtocol();
        try {
            HomeBean bean=mProtocol.loadData(0);
            LoadedResult result=checkData(bean);
                if (result!=LoadedResult.SUCCESS){
                   return LoadedResult.EMPTY;
                }

                result=checkData(bean.list);

                if (result!=LoadedResult.SUCCESS){
                    return LoadedResult.EMPTY;
                }

                //拿到数据
                mDatas=bean.list;
                mPictures=bean.picture;
                //System.out.println(mDatas.toString());
                System.out.println(mPictures.size());
                return result;
        } catch (Exception e) {
            e.printStackTrace();
            return LoadedResult.ERROR;
        }


    }

    //适配器
    class HomeAdapter extends AppListAdapter{

        public HomeAdapter(AbsListView listView,List<AppInfoBean> mDatas) {
            super(listView,mDatas);
        }
        @Override
        protected List<AppInfoBean> onLoadMoreData() throws Exception {
            return loadMoreData(mDatas.size());
        }
    }

    //分页加载更多
    private List<AppInfoBean> loadMoreData(int index) throws Exception{
        HomeBean bean=mProtocol.loadData(index);
        if (bean==null){
            return null;
        }
        return bean.list;
    }

}
