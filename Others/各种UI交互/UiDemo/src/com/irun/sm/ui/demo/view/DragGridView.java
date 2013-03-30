package com.irun.sm.ui.demo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.irun.sm.ui.demo.ui.R;

/***
 * @author huangsm
 * @date 2012-7-20
 * @email huangsanm@gmail.com
 * @desc 可拖拽的gridview
 */
public class DragGridView extends GridView {
	private final float mAlpha = 0.9f;

	private ImageView mDragImageView;
	private int mStartPos;
	private int mCurrentPos;
	private int mScaledTouchSlop;
	//当前位置距离边界的位置
	private int mDragOffsetX;
	private int mDragOffSetY;
	//移动的位置
	private int mDragPointX;
	private int mDragPointY;
	//边界
	private int mUpperBound;
    private int mLowerBound;
    
    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;
    private Context mContext;
    
    private DropViewListener mDropViewListener;
    
	public DragGridView(Context context) {
		super(context);
		mContext = context;
		mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		mScaledTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
	}

	public DragGridView(Context context, AttributeSet attr) {
		super(context, attr);
		mContext = context;
		mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		mScaledTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
	}
	
	public DragGridView(Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);
		mContext = context;
		mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		mScaledTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
	}
	
	
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				final int x = (int) ev.getX();
				final int y = (int) ev.getY();
				final int itemNum = pointToPosition(x, y);
				if(AdapterView.INVALID_POSITION == itemNum){
					break;
				}
				final ViewGroup item = (ViewGroup) getChildAt(itemNum - getFirstVisiblePosition());
				mDragPointX = x - item.getLeft();
				mDragPointY = y - item.getTop();
				mDragOffsetX = ((int) ev.getRawX()) - x;
				mDragOffSetY = ((int) ev.getRawY()) - y;
				
				//长按
				item.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						//计算边界
						final int height = getHeight();
						mUpperBound = Math.min(y - mScaledTouchSlop, height / 3);
						mLowerBound = Math.max(y + mScaledTouchSlop, height * 2 / 3);
						mCurrentPos = mStartPos = itemNum;
						
						item.setDrawingCacheEnabled(true);
						Bitmap bitmap = Bitmap.createBitmap(item.getDrawingCache());
						startDragging(bitmap, x, y);
						return true;
					}
				});
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	private void startDragging(Bitmap bitmap, int x, int y){
		stopDragging();
		
		mParams = new WindowManager.LayoutParams();
		mParams.gravity = Gravity.TOP | Gravity.LEFT;
		mParams.x = x - mDragPointX + mDragOffsetX;
		mParams.y = y - mDragPointY + mDragOffSetY;
		mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
				| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
		mParams.format = PixelFormat.TRANSLUCENT;
		mParams.windowAnimations = 0;
		
		ImageView imageView = new ImageView(mContext);
		imageView.setImageBitmap(bitmap);
		imageView.setBackgroundResource(R.drawable.tab_item_bg);
		imageView.setPadding(0, 0, 0, 0);
		mWindowManager.addView(imageView, mParams);
		mDragImageView = imageView;
	}
	
	private void stopDragging(){
		if(mDragImageView != null){
			mWindowManager.removeView(mDragImageView);
			mDragImageView.setImageDrawable(null);
			mDragImageView = null;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(mDragImageView != null && mCurrentPos != INVALID_POSITION && mDropViewListener != null){
			switch (ev.getAction()) {
				case MotionEvent.ACTION_UP:
					//int y = (int) ev.getY();
					stopDragging();
					//数据交换
					if(mCurrentPos >= 0 && mCurrentPos < getCount()){
						mDropViewListener.drog(mStartPos, mCurrentPos);
					}
					break;
				case MotionEvent.ACTION_MOVE:
					int x = (int) ev.getX();
					int y = (int) ev.getY();
					dragView(x, y);
					if (y >= getHeight() / 3) {
			            mUpperBound = getHeight() / 3;
			        }
			        if (y <= getHeight() * 2 / 3) {
			            mLowerBound = getHeight() * 2 / 3;
			        }
					 int speed = 0;
					if (y > mLowerBound) {
                        if (getLastVisiblePosition() < getCount() - 1) {
                            speed = y > (getHeight() + mLowerBound) / 2 ? 16 : 4;
                        } else {
                            speed = 1;
                        }
                    } else if (y < mUpperBound) {
                        speed = y < mUpperBound / 2 ? -16 : -4;
                        if (getFirstVisiblePosition() == 0
                                && getChildAt(0).getTop() >= getPaddingTop()) {
                            speed = 0;
                        }
                    }
                    if (speed != 0) {
                        smoothScrollBy(speed, 30);
                    }
					break;
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}
	
	private void dragView(int x, int y){
		if(mDragImageView != null){
			mParams.alpha = mAlpha;
			mParams.y = y - mDragPointY + mDragOffSetY;
			mParams.x = x - mDragPointX + mDragOffsetX;
			mWindowManager.updateViewLayout(mDragImageView, mParams);
		}
		int tempPosition = pointToPosition(x, y);
		if(tempPosition != INVALID_POSITION){
			mCurrentPos = tempPosition;
		}
		if(y < mUpperBound || y > mLowerBound){
			setSelection(mCurrentPos);
		}
	}
	
	public void setDropViewListener(DropViewListener listener){
		this.mDropViewListener = listener;
	}
	
	public interface DropViewListener {
		void drog(int from, int to);
	}
}
