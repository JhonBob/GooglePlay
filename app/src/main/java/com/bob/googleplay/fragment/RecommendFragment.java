package com.bob.googleplay.fragment;


import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.bob.googleplay.http.RecommendProtocol;
import com.bob.googleplay.utils.UIUtils;
import com.bob.googleplay.view.ShakeListener;
import com.bob.googleplay.view.StellarMap;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/2/28.
 */

//推荐界面
public class RecommendFragment extends BaseFragment {
    private RecommendProtocol mRecommendProtocol;
    private List<String> mDatas;

    private StellarMap rootView;
    private  ShakeListener listener;
    private RecommendAdapter mAdapter;

    @Override
    protected View onLoadSuccessView() {
        mAdapter=new RecommendAdapter();
        rootView=new StellarMap(UIUtils.getContext());
        //设置样式
        int dp = UIUtils.dip2px(20);
        rootView.setPadding(dp,dp,dp,dp);
        //设置数据
        rootView.setAdapter(mAdapter);
        //设置随机摆放区域（切块）
        rootView.setRegularity(15,20);
        //默认选中0页
        rootView.setGroup(0,true);
        //设置摇一摇
        listener=new ShakeListener(UIUtils.getContext());
        listener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                ////摇一摇回掉
                int currentGroup = rootView.getCurrentGroup();
                if (currentGroup==mAdapter.getGroupCount()-1){
                    currentGroup=0;
                }else {
                    currentGroup++;
                }
                rootView.setGroup(currentGroup,true);
            }
        });
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (listener!=null){
            listener.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (listener!=null){
            listener.pause();
        }
    }

    @Override
    protected LoadingPager.LoadedResult onLoadingData() {
        mRecommendProtocol=new RecommendProtocol();
        try {
            mDatas=mRecommendProtocol.loadData(0);
            return checkData(mDatas);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.ERROR;
        }
    }


    class RecommendAdapter implements StellarMap.Adapter{
        //定义数据条数
        private int PRE_PAGE_SIZE=15;

        //有几页数据
        @Override
        public int getGroupCount() {
            if (mDatas!=null){
                int size=mDatas.size();
                int count=size/PRE_PAGE_SIZE;
                if (size%PRE_PAGE_SIZE>0){
                    count++;
                }
                return count;
            }
            return 0;
        }

        //第几页数据有几条
        @Override
        public int getCount(int group) {
            if (mDatas!=null){
                int size=mDatas.size();
                //最后一页
                if (group==getGroupCount()-1){
                    if (size%PRE_PAGE_SIZE>0){
                        //最后一页有多余，但不够一页
                        return size%PRE_PAGE_SIZE;
                    }else {
                        return PRE_PAGE_SIZE;
                    }
                }
                return PRE_PAGE_SIZE;
            }
            return 0;
        }

        //提供View的显示
        @Override
        public View getView(int group, int position, View convertView) {
            int index=PRE_PAGE_SIZE*group+position;
            String data=mDatas.get(index);
            //返回随机大小，随机颜色的textView
            TextView tv=new TextView(UIUtils.getContext());
            //设置数据
            tv.setText(data);
            //设置颜色
            Random random=new Random();
            int alpha=0xff;
            int red=random.nextInt(170)+30;
            int green=random.nextInt(170)+30;
            int blue=random.nextInt(170)+30;
            int argb= Color.argb(alpha, red, green, blue);
            tv.setTextColor(argb);
            //设置大小
            int size=UIUtils.dip2px(random.nextInt(20)+30);//14sp-->24sp
            tv.setTextSize(size);
            return tv;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return 0;
        }
    }
}
