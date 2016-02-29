package com.bob.googleplay.adapter;

import android.animation.ObjectAnimator;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bob.googleplay.R;
import com.bob.googleplay.bean.AppInfoBean;
import com.bob.googleplay.utils.BitmapHelper;
import com.bob.googleplay.utils.Constans;
import com.bob.googleplay.utils.UIUtils;
import com.bob.googleplay.view.RandomLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.animation.ValueAnimator;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2016/2/29.
 */
//应用安全
public class AppDetailSafeHolder extends BaseHolder<List<AppInfoBean.AppSafeBean>> implements View.OnClickListener {
    @ViewInject(R.id.app_detail_safe_iv_arrow)
    private ImageView mIvArrow;
    @ViewInject(R.id.app_detail_safe_des_container)
    private LinearLayout mDesContainer;
    @ViewInject(R.id.app_detail_safe_pic_container)
    private LinearLayout mPicContainer;

    private boolean isOpened=true;

    @Override
    protected View initView() {
        View view=View.inflate(UIUtils.getContext(), R.layout.item_app_detail_safe,null);
        ViewUtils.inject(this,view);
        //设置点击事件
        view.setOnClickListener(this);
        return view;
    }

    @Override
    protected void refreshUI(List<AppInfoBean.AppSafeBean> datas) {
        //清空
        mPicContainer.removeAllViews();
        mDesContainer.removeAllViews();
        //遍历集合动态加载
        for (int i=0;i<datas.size();i++){
            AppInfoBean.AppSafeBean safeBean = datas.get(i);

            //图片容器
            fillPicContainer(safeBean);
            //描述容器
            fillDesContainer(safeBean);
        }
        //默认关闭
        toggle(false);
    }

    private void fillDesContainer(AppInfoBean.AppSafeBean safeBean) {
        LinearLayout layout=new LinearLayout(UIUtils.getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        int dp=UIUtils.dip2px(10);
        layout.setPadding(dp,dp,dp,dp);

        //加载图标
        ImageView iv=new ImageView(UIUtils.getContext());
        //加载图片
        BitmapHelper.display(iv, Constans.IMAGE_BASE_URL+safeBean.safeDesUrl);
        layout.addView(iv);
        //加载文本
        TextView  tv=new TextView(UIUtils.getContext());
        tv.setText(safeBean.safeDes);
        if(safeBean.safeDesColor==0){
            //正常色
            tv.setTextColor(UIUtils.getColor(R.color.app_detail_safe_normal));
        }else {
            //警告色
            tv.setTextColor(UIUtils.getColor(R.color.app_detail_safe_warning));
        }
        layout.addView(tv);
        //加入
        mDesContainer.addView(layout);
    }

    private void fillPicContainer(AppInfoBean.AppSafeBean safeBean) {
        ImageView iv=new ImageView(UIUtils.getContext());
        //加载图片数据
        BitmapHelper.display(iv, Constans.IMAGE_BASE_URL+safeBean.safeUrl);
        mPicContainer.addView(iv);
    }

    @Override
    public void onClick(View v) {
        if (v==getRootView()){
            toggle(true);
        }
    }

    //开关
    private void toggle(boolean animated) {

        //测量
        mDesContainer.measure(0,0);
        int height=mDesContainer.getMeasuredHeight();

        if (isOpened){
            if (animated){
                //关闭
                int start=height;
                int end=0;

                doAnimation(start, end);
            }else {
                ViewGroup.LayoutParams layoutParams = mDesContainer.getLayoutParams();
                layoutParams.height = 0;
                mDesContainer.setLayoutParams(layoutParams);
            }


        }else {
            if (animated){
                //打开
                int start=0;
                int end=height;
                doAnimation(start,end);
            }else {
                ViewGroup.LayoutParams layoutParams = mDesContainer.getLayoutParams();
                layoutParams.height = height;
                mDesContainer.setLayoutParams(layoutParams);
            }

        }


        //箭头动画
        if (isOpened){
            ObjectAnimator.ofFloat(mIvArrow,"rotation",-180,0).start();
        }else {
            ObjectAnimator.ofFloat(mIvArrow,"rotation",0,180).start();
        }

        //状态切换
        isOpened=!isOpened;
    }



    private void doAnimation(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mDesContainer.getLayoutParams();
                layoutParams.height = value;
                mDesContainer.setLayoutParams(layoutParams);
            }
        });
        animator.start();
    }
}
