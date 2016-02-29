package com.bob.googleplay.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Administrator on 2016/2/29.
 */
public class ProgressButton extends Button {


    private int			mProgress;
    private int			mMax;
    private Drawable	mProgressDrawable;
    private boolean		mProgressEnable;

    public ProgressButton(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public void setProgressEnable(boolean enable)
    {
        this.mProgressEnable = enable;
    }

    public void setProgress(int progress)
    {
        this.mProgress = progress;
        invalidate();
    }

    public void setMax(int max)
    {
        this.mMax = max;
    }

    public void setProgressDrawable(Drawable drawable)
    {
        this.mProgressDrawable = drawable;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if (mProgressEnable)
        {
            // 先画进度

            int width = getMeasuredWidth();
            int right = 0;
            if (mMax == 0)
            {
                right = (int) (width * mProgress / 100f + 0.5f);
            }
            else
            {
                right = (int) (width * mProgress * 1f / mMax + 0.5f);
            }

            // 设置矩形
            mProgressDrawable.setBounds(0, 0, right, getMeasuredHeight());
            // 画的操作
            mProgressDrawable.draw(canvas);
        }

        // 再画文本
        super.onDraw(canvas);// 画文本
    }
}
