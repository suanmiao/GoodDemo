package com.irun.sm.ui.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.irun.sm.ui.demo.ui.R;

/***
 * @author huangsm
 * @email huangsanm@gmail.com
 * @date 2011-11-9
 * @description 
 **/
public class BubbleTextView extends TextView {

	private static final int CORNER_RADIUS = 8;
	private static final int PADDING_H = 5;
	private static final int PADDING_V = 1;
	private final RectF mRectf = new RectF();
	private Paint mPaint;
	private Context mContext;
	
	public BubbleTextView(Context context) {
		super(context);
		init(context);
	}

	public BubbleTextView(Context context, AttributeSet attr) {
		super(context, attr);
		init(context);
	}
	
	public BubbleTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context){
		mContext = context;
		setFocusable(true);
		setPadding(PADDING_H, 0, PADDING_H, PADDING_V);
		//实例化时把画笔设置为抗锯齿
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(mContext.getResources().getColor(R.color.bg_color));
	}
	
	@Override
	protected void drawableStateChanged() {
		invalidate();
		super.drawableStateChanged();
	}
	
	@Override
	public void draw(Canvas canvas) {
		final Layout layout = getLayout();
		final RectF rect = mRectf;
		final int left = getCompoundPaddingLeft();
		final int top = getExtendedPaddingTop();
		rect.set(
				left + layout.getLineLeft(0) - PADDING_H, 
				top + layout.getLineTop(0) - PADDING_V, 
				Math.min(left + layout.getLineRight(0) + PADDING_H, getScrollX() + getRight() - getLeft()), 
				top + layout.getLineBottom(0) + PADDING_V);
		canvas.drawRoundRect(rect, CORNER_RADIUS, CORNER_RADIUS, mPaint);
		super.draw(canvas);
	}
}
