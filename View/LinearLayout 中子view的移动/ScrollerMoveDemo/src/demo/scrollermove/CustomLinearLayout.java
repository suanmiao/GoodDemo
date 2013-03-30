package demo.scrollermove;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class CustomLinearLayout extends LinearLayout {
	
	// ===========================================================
	// Constants
	// ===========================================================

	private static final String TAG = "CustomLinearLayout";
	
	// ===========================================================
	// Fields
	// ===========================================================
	
	private int mLastX;
	private int mLastY;
	
	private OnLogListener mOnLogListener;

	// ===========================================================
	// Constructors
	// ===========================================================

	public CustomLinearLayout(Context context) {
		super(context);
	}

	public CustomLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void setOnLogListener(OnLogListener pLogListener) {
		mOnLogListener = pLogListener;
	}
	
	private void updateLog() {
		if (mOnLogListener != null) {
			mOnLogListener.setOutput("\n x = " + getLeft() 
					+ "\n y = " + getTop()
					+ "\n scroll X = " + getScrollX()
					+ "\n scroll Y = " + getScrollY());
		}
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		final int action = event.getAction();

		final int x = (int) event.getX();
		final int y = (int) event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:

			mLastX = x;
			mLastY = y;

			break;

		case MotionEvent.ACTION_MOVE:
			
			updateLog();
			
			int deltaX = mLastX - x;
			int deltaY = mLastY - y;

			// 移动视图到制定位置
			scrollBy(deltaX, deltaY);

			mLastX = x;
			mLastY = y;

			break;

		default:
			break;
		}

		// 不再传递Touch事件
		return true;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		Log.e(TAG, "onMeasure");
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		
		Log.e(TAG, "onLayout");
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Log.e(TAG, "onDraw");
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
	/**
	 *  用于输出日志
	 */
	public interface OnLogListener {
		public void setOutput(String pOutput);
	}

}
