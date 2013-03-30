package com.irun.sm.ui.demo.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.irun.sm.ui.demo.ui.R;

/***
 * @author huangsm
 * @date 2012-11-20
 * @email huangsanm@gmail.com
 * @desc 滚动view
 */
public class AutoHorizontalScrollView extends FrameLayout {

	private static final String TAG = "AutoHorizontalScrollView";
	
	private static final int MSG = 1;
	private static final int DEFAULT_INTERVAL = 3000;
	
	private int mWhichChild;
	private int mInterval;
    private boolean mAutoStart = false;
    private boolean mRunning = false;
    private boolean mStarted = false;
    private boolean mVisible = false;
    private boolean mUserPresent = true;
	
    private Animation mInAnimation;
    private Animation mOutAnimation;
    
    private OnItemChangeListener mOnItemChangeListener;
    private Context mContext;
	public AutoHorizontalScrollView(Context context) {
		super(context);
		setMeasureAllChildren(true);
		mContext = context;
	}

	public AutoHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoHorizontalScrollView);
		
		mInterval = a.getInteger(R.styleable.AutoHorizontalScrollView_Interval, DEFAULT_INTERVAL);
		mAutoStart = a.getBoolean(R.styleable.AutoHorizontalScrollView_autoStart, false);
		
		int animation = a.getResourceId(R.styleable.AutoHorizontalScrollView_inAnimation, 0);
		if(animation > 0){
			setInAnimation(context, animation);
		}
		
		animation = a.getResourceId(R.styleable.AutoHorizontalScrollView_outAnimation, 0);
		if(animation > 0){
			setOutAnimation(context, animation);
		}
		a.recycle();
		
		setMeasureAllChildren(true);
		mContext = context;
	}
	
	public void setInterval(int delayed){
		mInterval = delayed;
	}
	
	/**
	 * 显示childview
	 * @param childIndex
	 */
	private void setDisplayedChild(int whichChild){
		mWhichChild = whichChild;
		final int childCount = getChildCount();
		if(whichChild >= childCount){
			mWhichChild = 0;
		}else if(whichChild < 0){
			mWhichChild = childCount - 1;
		}
		boolean hasFocus = getFocusedChild() != null;
		if(hasFocus){
			requestFocus(FOCUS_FORWARD);
		}
		showChildView(mWhichChild);
	}
	
	private void showChildView(int childIndex){
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);
			if(i == childIndex){
				if(mInAnimation != null){
					child.startAnimation(mInAnimation);
				}
				child.setVisibility(View.VISIBLE);
			} else {
				if(mOutAnimation != null && child.getVisibility() == View.VISIBLE){
					child.startAnimation(mOutAnimation);
				}else if(child.getAnimation() == mInAnimation){
					child.clearAnimation();
				}
				child.setVisibility(View.GONE);
			}
		}
	}
	
	private final BroadcastReceiver mRecevier = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if(Intent.ACTION_SCREEN_OFF.equals(action)){
				mUserPresent = false;
				updateState();
			}else if(Intent.ACTION_USER_PRESENT.equals(action)){
				mUserPresent = true;
				updateState();
			}
		}
	};
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		//注册通知
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_USER_PRESENT);
		mContext.registerReceiver(mRecevier, filter);
		
		if(mAutoStart){
			start();
		}
	}
	
	/**
	 * start
	 */
	public void start(){
		mStarted = true;
		updateState();
	}
	
	/**
	 * stop
	 */
	public void stop(){
		mStarted = false;
		updateState();
	}
	
	private void updateState(){
		boolean running = mStarted && mVisible && mUserPresent;
		if(running != mRunning){
			if(running){
				showChildView(mWhichChild);
				Message msg = mHandler.obtainMessage(MSG);
				mHandler.sendMessageDelayed(msg, mInterval);
			}else{
				mHandler.removeMessages(MSG);
			}
			mRunning = running;
		}
		Log.d(TAG, "mstarted:" + mStarted + ",mVisible:" + mVisible + ",mUserPresent:" + mUserPresent);
	}
	
	/**
	 * 循环给自己发送消息
	 */
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == MSG){
				showNext();
				Log.d(TAG, "mWhichChild:" + mWhichChild);
				if(mOnItemChangeListener != null){
					mOnItemChangeListener.onItemChange(mWhichChild);
				}
				msg = obtainMessage(MSG);
				sendMessageDelayed(msg, mInterval);
			}
		}
	};
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mVisible = false;
		mContext.unregisterReceiver(mRecevier);
	}
	
	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
		mVisible = visibility == VISIBLE;
		updateState();
	}
	
	/**
	 * 获取当前显示的view
	 * @return
	 */
	public int getDisplayedChild(){
		return mWhichChild;
	}
	
	public View getCurrentView(){
		return getChildAt(mWhichChild);
	}
	
	/**
	 * next
	 */
	public void showNext(){
		setDisplayedChild(mWhichChild + 1);
	}
	
	/**
	 * previous
	 */
	public void showPrevious(){
		setDisplayedChild(mWhichChild - 1);
	}

	/**
	 * 显示时的动画
	 * @param context
	 * @param resource
	 */
	public void setInAnimation(Context context, int resource){
		setInAnimation(AnimationUtils.loadAnimation(context, resource));
	}
	
	public void setInAnimation(Animation inAnim){
		mInAnimation = inAnim;
	}
	
	/**
	 * 隐藏时的动画
	 * @param context
	 * @param resource
	 */
	public void setOutAnimation(Context context, int resource){
		setOutAnimation(AnimationUtils.loadAnimation(context, resource));
	}
	
	public void setOutAnimation(Animation outAnim){
		mOutAnimation = outAnim;
	}
	
	@Override
	public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
		super.addView(child, index, params);
		if(getChildCount() == 1){
			child.setVisibility(View.VISIBLE);
		} else {
			child.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void removeAllViews() {
		super.removeAllViews();
		mWhichChild = 0;
	}
	
	@Override
	public void removeView(View view) {
		final int index = indexOfChild(view);
		if(index >= 0){
			removeViewAt(index);
		}
	}
	
	@Override
	public void removeViewAt(int index) {
		super.removeViewAt(index);
		final int childCount = getChildCount();
		if(childCount == 0){
			mWhichChild = 0;
		} else if(mWhichChild >= childCount){
			setDisplayedChild(mWhichChild - 1);
		} else if(mWhichChild == index){
			setDisplayedChild(mWhichChild);
		}
	}
	
	public interface OnItemChangeListener {
		void onItemChange(int childIndex);
	}
	
	public void setOnItemChangeListener(OnItemChangeListener listener){
		mOnItemChangeListener = listener;
	}
}
