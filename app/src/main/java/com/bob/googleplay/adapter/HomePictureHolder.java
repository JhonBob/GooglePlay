package com.bob.googleplay.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bob.googleplay.R;
import com.bob.googleplay.utils.BitmapHelper;
import com.bob.googleplay.utils.Constans;
import com.bob.googleplay.utils.UIUtils;
import com.bob.googleplay.view.CompatViewPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Administrator on 2016/2/27.
 */

//轮播图Holder
public class HomePictureHolder extends BaseHolder<List<String>> implements ViewPager.OnPageChangeListener{

    @ViewInject(R.id.item_home_picture_pager)
    private CompatViewPager mPager;
    @ViewInject(R.id.item_home_picture_container_indicator)
    private LinearLayout mPointContainer;

    private List<String> mPictures;
    private AutoSwitchTask mAutoSwitchTask;

    @Override
    protected View initView() {
        View view=View.inflate(UIUtils.getContext(), R.layout.item_home_picture,null);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    protected void refreshUI(List<String> data) {
        this.mPictures=data;
        //设置Adapter
        mPager.setAdapter(new HomePictureAdapter());
        //容器加点
        mPointContainer.removeAllViews();
        for (int i=0;i<data.size();i++){
            View view =new View(UIUtils.getContext());
            view.setBackgroundResource(R.drawable.indicator_normal);
            //dp-->px
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(UIUtils.dip2px(6),UIUtils.dip2px(6));
            if (i!=0){
                params.leftMargin=UIUtils.dip2px(8);
                params.bottomMargin=UIUtils.dip2px(8);
            }else {
                view.setBackgroundResource(R.drawable.indicator_selected);
            }
            mPointContainer.addView(view,params);

        }

        //设置ViewPager的监听
        mPager.setOnPageChangeListener(this);
        //无限轮播左划处理
        int middle=Integer.MAX_VALUE/2;
        //多出的
        int extra=middle % mPictures.size();
        mPager.setCurrentItem(middle - extra);

        //自动轮播
        if (mAutoSwitchTask==null){
            mAutoSwitchTask=new AutoSwitchTask();
        }
        mAutoSwitchTask.start();

        //触碰监听
        mPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //轮播停止
                        mAutoSwitchTask.stop();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        mAutoSwitchTask.start();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        mAutoSwitchTask.start();
                        break;
                }
                return false;
            }
        });

    }

    //轮播任务
    class AutoSwitchTask implements Runnable{

        //触发
        public void start(){
            stop();
            UIUtils.postDelayed(this,2000);
        }
        public void stop(){
            UIUtils.removeCallbacks(this);
        }

        @Override
        public void run() {
            //让ViewPager选中下一个
            int item=mPager.getCurrentItem();
            mPager.setCurrentItem(++item);
            //持续调用
            UIUtils.postDelayed(this, 2000);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //无限轮播POSITION处理
        position=position%mPictures.size();
        //选中
        int childCount = mPointContainer.getChildCount();
        for (int i=0;i<childCount;i++){
            View childAt = mPointContainer.getChildAt(i);
            childAt.setBackgroundResource(i==position?R.drawable.indicator_selected:R.drawable.indicator_normal);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }




    class HomePictureAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            if (mPictures!=null){
                //return mPictures.size();
                //无限轮播
                return Integer.MAX_VALUE;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position=position%mPictures.size();
            ImageView iv=new ImageView(UIUtils.getContext());
            //数据
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(R.drawable.ic_default);
            BitmapHelper.display(iv, Constans.IMAGE_BASE_URL + mPictures.get(position));
            System.out.print(Constans.IMAGE_BASE_URL + mPictures.get(position));
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
