package com.bob.googleplay.adapter;


import android.text.TextUtils;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bob.googleplay.R;
import com.bob.googleplay.bean.CategoryBean;
import com.bob.googleplay.utils.BitmapHelper;
import com.bob.googleplay.utils.Constans;
import com.bob.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.zip.DataFormatException;

/**
 * Created by Administrator on 2016/2/28.
 */
public class CategoryItemHolder extends BaseHolder<CategoryBean> {

    @ViewInject(R.id.item_category_icon_1)
    private ImageView mIv1;
    @ViewInject(R.id.item_category_icon_2)
    private ImageView mIv2;
    @ViewInject(R.id.item_category_icon_3)
    private ImageView mIv3;

    @ViewInject(R.id.item_category_name_1)
    private TextView mTv1;
    @ViewInject(R.id.item_category_name_2)
    private TextView mTv2;
    @ViewInject(R.id.item_category_name_3)
    private TextView mTv3;

    @Override
    protected View initView() {
        View view=View.inflate(UIUtils.getContext(), R.layout.item_category,null);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    protected void refreshUI(CategoryBean data) {
        //设置数据
        mTv1.setText(data.name1);
        mTv2.setText(data.name2);
        mTv3.setText(data.name3);
        //设置图片
        mIv1.setImageResource(R.drawable.ic_default);
        mIv2.setImageResource(R.drawable.ic_default);
        mIv3.setImageResource(R.drawable.ic_default);

        display(mIv1, data.url1);
        display(mIv2,data.url2);
        display(mIv3, data.url3);

    }

    private void display(ImageView iv,String url){
        if (!TextUtils.isEmpty(url)){
            ViewParent parent = iv.getParent();
            if (parent!=null && parent instanceof View){
                ((View) parent).setVisibility(View.VISIBLE);
            }
            BitmapHelper.display(iv, Constans.IMAGE_BASE_URL + url);
        }else {
            //影藏对应的View
            ViewParent parent = iv.getParent();
            if (parent!=null && parent instanceof View){
                ((View) parent).setVisibility(View.INVISIBLE);
            }
        }
    }



    @OnClick(R.id.item_category_item_1)
    public void clickItem1(View view){
        Toast.makeText(UIUtils.getContext(), mData.name1,Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.item_category_item_2)
    public void clickItem2(View view){
        Toast.makeText(UIUtils.getContext(), mData.name2,Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.item_category_item_3)
    public void clickItem3(View view){
        Toast.makeText(UIUtils.getContext(), mData.name3,Toast.LENGTH_SHORT).show();
    }

}
