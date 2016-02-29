package com.bob.googleplay.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.bob.googleplay.R;

/**
 * Created by Administrator on 2016/2/27.
 */
public class RatioLayout extends FrameLayout {

    public static final int RELATIVE_WIDTH=0;
    public static final  int RELATIVE_HEIGHT=1;

    //通过宽高比确定高度
    private float mRatio;
    private int mRelative=RELATIVE_WIDTH;

    public RatioLayout(Context context) {
        this(context, null);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        mRatio = ta.getFloat(R.styleable.RatioLayout_ratio, 0);
        mRelative=ta.getInt(R.styleable.RatioLayout_relative,RELATIVE_WIDTH);
        ta.recycle();
    }

    public void setRatio(float ratio){
        this.mRatio=ratio;
    }

    public void setRelative(int relative){
        this.mRelative=relative;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //父容器的宽度
        int widSize=MeasureSpec.getSize(widthMeasureSpec);
        //宽度模式
        int widMode=MeasureSpec.getMode(widthMeasureSpec);
        //父容器的高度
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        //高度模式
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);


        //是否是精确值
        if (widMode==MeasureSpec.EXACTLY && mRatio!=0 && mRelative==RELATIVE_WIDTH){
            //获得高度
            int width=widSize-getPaddingLeft()-getPaddingRight();
            int height= (int) (widSize/mRatio+0.5f);
            //设置孩子,精确生长
            int childWidthMeasureSpec=MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY);
            int childHeightMeasureSpec=MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);

            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);
            //给自己设置
            setMeasuredDimension(widSize, height + getPaddingLeft() + getPaddingBottom());
        }else if (heightMode==MeasureSpec.EXACTLY && mRatio!=0 && mRelative==RELATIVE_HEIGHT){
            int height=heightSize-getPaddingTop()-getPaddingBottom();
            int width= (int) (height*mRatio+0.5f);
            //设置孩子,精确生长
            int childWidthMeasureSpec=MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY);
            int childHeightMeasureSpec=MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);
            //给自己设置
            setMeasuredDimension(width+getPaddingLeft()+getPaddingRight(), heightSize);
        }else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        }

}
