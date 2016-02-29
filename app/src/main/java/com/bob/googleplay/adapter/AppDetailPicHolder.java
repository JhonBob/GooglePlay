package com.bob.googleplay.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bob.googleplay.R;
import com.bob.googleplay.utils.BitmapHelper;
import com.bob.googleplay.utils.Constans;
import com.bob.googleplay.utils.UIUtils;
import com.bob.googleplay.view.RatioLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Administrator on 2016/2/29.
 */
public class AppDetailPicHolder extends BaseHolder<List<String>>{

    @ViewInject(R.id.app_detail_pic_iv_container)
    private LinearLayout mContainer;


    @Override
    protected View initView() {
        View view=View.inflate(UIUtils.getContext(), R.layout.item_app_detail_pic,null);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    protected void refreshUI(List<String> data) {
        //清空
        mContainer.removeAllViews();
        for (int i=0;i<data.size();i++){
            String url=data.get(i);

//            RatioLayout layout=new RatioLayout(UIUtils.getContext());
//            layout.setRatio(0.6f);
//            layout.setRelative(RatioLayout.RELATIVE_WIDTH);

            ImageView iv=new ImageView(UIUtils.getContext());
            //动态加载
            BitmapHelper.display(iv, Constans.IMAGE_BASE_URL + url);
//            //添加iv
//            layout.addView(iv);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            if (i!=0){
                params.leftMargin=UIUtils.dip2px(20);
            }

            mContainer.addView(iv,params);
        }
    }

}
