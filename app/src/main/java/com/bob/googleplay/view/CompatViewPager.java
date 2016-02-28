package com.bob.googleplay.view;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/2/27.
 */
//针对2.3适配
public class CompatViewPager extends ViewPager {
    private float downX;
    private float downY;

    public CompatViewPager(Context context) {
        super(context);
    }

    public CompatViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.HONEYCOMB){
            switch (ev.getAction()){
                case MotionEvent.ACTION_DOWN:
                    //请求不拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                    downX=ev.getX();
                    downY=ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //左右移动，请求父容器不拦截，上下移动，拦截
                    float moveX=ev.getX();
                    float moveY=ev.getY();
                    float diffX=moveX-downY;
                    float diffY=moveY-downY;
                    if (Math.abs(diffX)>Math.abs(diffY)){
                        //左右
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }else {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
