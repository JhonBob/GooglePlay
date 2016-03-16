package com.bob.googleplay.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bob.googleplay.http.HotProtocol;
import com.bob.googleplay.utils.DrawableUtils;
import com.bob.googleplay.utils.UIUtils;
import com.bob.googleplay.view.FlowLayout;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/2/27.
 */
//排行界面
public class HotFragment extends BaseFragment {
    private HotProtocol mHotProtocol;
    private List<String> mDatas;
    @Override
    protected View onLoadSuccessView() {
        ScrollView rootView=new ScrollView(UIUtils.getContext());
        //加载流氏布局
        FlowLayout layout=new FlowLayout(UIUtils.getContext());
        layout.setSpace(UIUtils.dip2px(5),UIUtils.dip2px(5));
        layout.setPadding(UIUtils.dip2px(5),UIUtils.dip2px(5),UIUtils.dip2px(5),UIUtils.dip2px(5));
        rootView.addView(layout);
        int padd=UIUtils.dip2px(10);
        //加数据
        for (int i=0;i<mDatas.size();i++){
            String data=mDatas.get(i);
            TextView tv=new TextView(UIUtils.getContext());
            tv.setText(data);
            tv.setTextSize(UIUtils.dip2px(15));
            tv.setTextColor(Color.WHITE);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(padd, padd, padd, padd);
            tv.setClickable(true);


            Random random=new Random();

            int shape=GradientDrawable.RECTANGLE;
            int radius=UIUtils.dip2px(10);
            int alpha=0xff;//0--255
            int red=random.nextInt(170)+40;//30--200
            int green=random.nextInt(170)+40;
            int blue=random.nextInt(170)+40;
            int argb=Color.argb(alpha,red,green,blue);

//            GradientDrawable gd=new GradientDrawable();
//            gd.setShape(shape);
//            gd.setCornerRadius(radius);
//            gd.setColor(argb);

            GradientDrawable normalBg= DrawableUtils.getShape(shape,radius,argb);
            GradientDrawable pressedBg=DrawableUtils.getShape(shape,radius,Color.RED);

            StateListDrawable selector=DrawableUtils.getSelector(normalBg,pressedBg);
//            selector.addState(new int[]{android.R.attr.state_pressed},pressedBg);
//            selector.addState(new int[]{},normalBg);

            tv.setBackgroundDrawable(selector);

            layout.addView(tv);
        }
        return rootView;
    }

    @Override
    protected LoadingPager.LoadedResult onLoadingData() {
        mHotProtocol=new HotProtocol();
        try {
            mDatas=mHotProtocol.loadData(0);
            return checkData(mDatas);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.ERROR;
        }
    }
}
