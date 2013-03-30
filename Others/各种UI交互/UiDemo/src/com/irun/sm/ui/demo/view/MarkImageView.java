package com.irun.sm.ui.demo.view;

import com.irun.sm.ui.demo.ui.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/***
 * @author huangsm
 * @date 2012-11-21
 * @email huangsanm@gmail.com
 * @desc 标记的imageview
 */
public class MarkImageView extends ImageView {

	private final static int PADDING = 30;
	private String mText;
	private String mPercent = "";
	private Paint mPaint;
	
	public MarkImageView(Context context) {
		super(context);
		
	}
	
	public MarkImageView(Context context, AttributeSet attr) {
		super(context, attr);
		
		TypedArray a = context.obtainStyledAttributes(attr, R.styleable.MarkView);
		
		mText = a.getString(R.styleable.MarkView_text);
		
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.WHITE);
		mPaint.setTextSize(22);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		float x = getWidth() / 2 - mPaint.measureText(mText) / 2;
		canvas.drawText(mText, x, getHeight() / 2 - PADDING, mPaint);
		canvas.drawText(mPercent, x, getHeight() / 2, mPaint);
	}
	
	public void setMark(int values){
		mPercent = "72%";
		invalidate();
	}
}
