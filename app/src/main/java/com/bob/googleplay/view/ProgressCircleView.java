package com.bob.googleplay.view;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bob.googleplay.R;
import com.bob.googleplay.utils.UIUtils;

//圆形进度绘制
public class ProgressCircleView extends LinearLayout
{
	private ImageView	mIvIcon;
	private TextView	mTvText;
	private boolean		mProgressEnable;
	private int			mMax;
	private int			mProgress;
	private RectF		oval;
	private Paint		paint	= new Paint();

	public ProgressCircleView(Context context) {
		this(context, null);
	}

	public ProgressCircleView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// 将class和xml进行绑定
		View.inflate(getContext(), R.layout.progress_circle_view, this);

		mIvIcon = (ImageView) findViewById(R.id.progress_iv_icon);
		mTvText = (TextView) findViewById(R.id.progress_tv_text);
	}

	public void setProgressText(String text)
	{
		mTvText.setText(text);
	}

	public void setProgressIcon(int resId)
	{
		mIvIcon.setImageResource(resId);
	}

	public void setProgressEnable(boolean enable)
	{
		this.mProgressEnable = enable;
		invalidate();
	}

	public void setProgress(int progress)
	{
		int measuredHeight = getMeasuredHeight();
		int measuredWidth = getMeasuredWidth();
		System.out.println(measuredWidth + " : " + measuredHeight);
		
		this.mProgress = progress;
		requestLayout();
		invalidate();
	}

	public void setMax(int max)
	{
		this.mMax = max;
	}

	@Override
	public void draw(Canvas canvas)
	{
		super.draw(canvas);

		System.out.println("#######调用了draw");
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);// 画孩子--》imageview，textView
		System.out.println("#######调用了ondraw");
		if (mProgressEnable)
		{
			System.out.println("#######画圆形进度");

			// 画圆形进度条

			float left = mIvIcon.getLeft();
			float top = mIvIcon.getTop();
			float right = mIvIcon.getRight();
			float bottom = mIvIcon.getBottom();

			if (oval == null)
			{
				oval = new RectF(left, top, right, bottom);
			}
			float startAngle = -90;// 开始的角度
			float sweepAngle = 0;// 扫过的角度
			if (mMax == 0)
			{
				sweepAngle = mProgress * 360f / 100;
			}
			else
			{
				sweepAngle = mProgress * 360f / mMax;
			}

			boolean useCenter = false;// 设置不画中间,只画弧度
			paint.setAntiAlias(true);
			paint.setColor(Color.BLUE);
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(UIUtils.dip2px(3));
			canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint);

		}

	}
}
