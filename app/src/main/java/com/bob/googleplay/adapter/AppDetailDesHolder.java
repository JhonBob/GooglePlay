package com.bob.googleplay.adapter;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bob.googleplay.R;
import com.bob.googleplay.bean.AppInfoBean;
import com.bob.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by Administrator on 2016/2/29.
 */
//应用描述
public class AppDetailDesHolder extends BaseHolder<AppInfoBean> implements View.OnClickListener {
    @ViewInject(R.id.app_detail_des_tv_des)
    private TextView mTvDes;
    @ViewInject(R.id.app_detail_des_tv_author)
    private TextView mTvAuthor;
    @ViewInject(R.id.app_detail_des_iv_arrow)
    private ImageView mIvArrow;

    private boolean isOpened=true;
    private int mDesHeight;
    private int mDesWidth;

    @Override
    protected View initView() {
        final View view=View.inflate(UIUtils.getContext(), R.layout.item_app_detail_des,null);
        ViewUtils.inject(this,view);
        view.setOnClickListener(this);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mDesWidth = mTvDes.getMeasuredWidth();
                mDesHeight = mTvDes.getMeasuredHeight();
                toggle(false);
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        return view;
    }

    @Override
    protected void refreshUI(AppInfoBean data) {
        mTvDes.setText(data.des);
        mTvAuthor.setText("作者:" + data.author);
    }

    @Override
    public void onClick(View v) {
        if (v==getRootView()){
            toggle(true);
        }
    }

    private void toggle(boolean animated) {

//        mTvDes.measure(0, 0);
//        int allHeight = mTvDes.getMeasuredHeight();
        int shortHeight = getShortHeight(mData.des);

        if (isOpened){
            int start=0;//大
            int end=0;//小

            if (shortHeight>mDesHeight){
                start=shortHeight;
                end=mDesHeight;
            }else {
                start=mDesHeight;
                end=shortHeight;
            }


            if (animated){
                doAnimation(start, end);
            }else {
                ViewGroup.LayoutParams params = mTvDes.getLayoutParams();
                params.height=end;
                mTvDes.setLayoutParams(params);
            }


        }else {
            //小到大
            int start=0;//小
            int end=0;//大

            if (shortHeight<mDesHeight){
                start=shortHeight;
                end=mDesHeight;
            }else {
                start=mDesHeight;
                end=shortHeight;
            }

            if (animated){
                doAnimation(start,end);
            }else {
                ViewGroup.LayoutParams params = mTvDes.getLayoutParams();
                params.height=end;
                mTvDes.setLayoutParams(params);
            }

        }

        //箭头动画
        if (isOpened){
            ObjectAnimator.ofFloat(mIvArrow, "rotation", -180, 0).start();
        }else {
            ObjectAnimator.ofFloat(mIvArrow,"rotation",0,180).start();
        }

        isOpened=!isOpened;

    }



    private void doAnimation(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                //给TextView设置高
                ViewGroup.LayoutParams params = mTvDes.getLayoutParams();
                params.height=value;
                mTvDes.setLayoutParams(params);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                View rootView=getRootView();
                ScrollView scrollView=null;
                ViewParent parent = rootView.getParent();
                if (parent!=null && parent instanceof ViewGroup){
                    while (true){
                        parent=parent.getParent();
                        if (parent!=null && parent instanceof ScrollView){
                            scrollView= (ScrollView) parent;

                            break;
                        }

                        if (parent==null){
                            break;
                        }
                    }

                    //让scrollView滚动到底部
                    if (scrollView != null) {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    private int getShortHeight(String des){
        TextView tv=new TextView(UIUtils.getContext());
        tv.setText(des);
        tv.setLines(3);
        tv.measure(View.MeasureSpec.makeMeasureSpec(mDesWidth, View.MeasureSpec.EXACTLY)
        ,0);
        return tv.getMeasuredHeight();
    }
}
