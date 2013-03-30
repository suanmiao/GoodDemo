package com.irun.sm.ui.demo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.irun.sm.ui.demo.ui.R;

/***
 * @author huangsm
 * @date 2012-11-7
 * @email huangsanm@gmail.com
 * @desc 滑动的开关
 */
public class SlideButton extends View implements View.OnTouchListener {

	private final static float SLIDE_BUTTON_TOP = 1;
	
	//打开，关闭
	private boolean mModel;
	//游标
	private Bitmap mSlideButton;
	//背景
	private Bitmap mSlideButtonBackground;
	
	private Rect mFlowRect;
	private Rect mSlideRect;
	//滑动位置
	private float mStartSlideX;
	private float mCurrentSlideX;
	//是否在滑动
	private boolean mOnSlide;
	//文字
	private String mOpen;
	private String mClose;
	//字体颜色
	private int mTextColor;
	
	//事件监听
	private OnSlideListener mOnSlideListener;
	
	public SlideButton(Context context) {
		super(context);
		init(context);
	}
	
	public SlideButton(Context context, AttributeSet attr){
		super(context, attr);
		init(context);
	}
	
	private void init(Context context){
		//默认为3g流量
		mModel = true;
		mOnSlide = false;
		mSlideButtonBackground = BitmapFactory.decodeResource(getResources(), R.drawable.monitor_button_bg);
		mSlideButton = BitmapFactory.decodeResource(getResources(), R.drawable.monitor_button);
		mFlowRect = new Rect(0, 0, mSlideButtonBackground.getWidth(), mSlideButtonBackground.getHeight());
		mSlideRect = new Rect(0, 0, mSlideButton.getWidth(), mSlideButton.getHeight());
		mOpen = "打开";
		mClose = "关闭";
		mTextColor = context.getResources().getColor(R.color.slide_text);
		
		setOnTouchListener(this);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		//绘制背景
		canvas.drawBitmap(mSlideButtonBackground, 0, 0, paint);
		float text_y = mSlideButton.getHeight() / 2 + 8;
		paint.setTextSize(22.f);
		//绘制游标
		if(mCurrentSlideX > mSlideButtonBackground.getWidth() / 2){
			float left = mSlideButtonBackground.getWidth() / 2;
			canvas.drawBitmap(mSlideButton, left, SLIDE_BUTTON_TOP, paint);
			
			//绘制文字
			paint.setColor(Color.BLACK);
			float open_x = mSlideButton.getWidth() / 2 - paint.measureText(mOpen) / 2;
			canvas.drawText(mOpen, open_x, text_y, paint);
			
			paint.setColor(mTextColor);
			float close_x = mSlideButtonBackground.getWidth() / 2 + paint.measureText(mOpen);
			canvas.drawText(mClose, close_x, text_y, paint);
		}else{
			canvas.drawBitmap(mSlideButton, 4.f, SLIDE_BUTTON_TOP, paint);
			//绘制文字
			paint.setColor(mTextColor);
			float open_x = mSlideButton.getWidth() / 2 - paint.measureText(mOpen) / 2;
			canvas.drawText(mOpen, open_x, text_y, paint);
			
			paint.setColor(Color.BLACK);
			float close_x = mSlideButtonBackground.getWidth() / 2 + paint.measureText(mOpen);
			canvas.drawText(mClose, close_x, text_y, paint);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN :
				if(event.getX() > mSlideButtonBackground.getWidth()){
					return false;
				}
				mOnSlide = true;
				mCurrentSlideX = mStartSlideX = event.getX();
				break;
			case MotionEvent.ACTION_UP:
				mCurrentSlideX = event.getX();
				mOnSlide = false;
				//是关闭状态
				if(event.getX() > mSlideButtonBackground.getWidth() / 2){
					mModel = false;
				}else{
					mModel = true;
				}
				if(mOnSlideListener != null){
					mOnSlideListener.OnSlide(mModel);
				}
				break;
		}
		//重新调用ondraw
		invalidate();
		return true;
	}
	
	public interface OnSlideListener {
		void OnSlide(boolean model);
	}
}
