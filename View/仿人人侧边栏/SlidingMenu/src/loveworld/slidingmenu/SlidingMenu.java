package loveworld.slidingmenu;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;


public class SlidingMenu extends ViewGroup {

	// ===========================================================
	// Constants
	// ===========================================================

	private View mRightView;
	private View mLeftView;
	private ScrollRunnable mScrollRunnable;
	private int mTouchSlop;
	
	// ��¼����λ�ã������жϵ�ǰ����ʱ����������
	private int mInterceptMotionX = 0;
	
	// ��¼һ���ƶ�λ�ã����ڼ����ƶ�ƫ����
	private int mLastX;
	
	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public SlidingMenu(Context context) {
		super(context);
		initSlidingMenu(context);
	}

	public SlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSlidingMenu(context);
	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initSlidingMenu(context);
	}

	// ===========================================================
	// Public Methods
	// ===========================================================

	public void addLeftView(View leftView) {
		mLeftView = leftView;
		// ����Touch�ַ����ƣ���ʹ�Ҳ���ͼ��ס��ǰ��ͼ
		// ֻҪVISIBLE״̬�������Ƚ��յ�Touch Event
		mLeftView.setVisibility(View.INVISIBLE);
		addView(leftView);
	}
	
	/**
	 * �ṩ�Ҳ���ʾ��ͼ
	 * 
	 * @param rightView
	 */
	
	public void addRightView(View rightView) {
		mRightView = rightView;
		
		addView(rightView);
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		measureChildren(widthMeasureSpec, heightMeasureSpec);

		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		
		setMeasuredDimension(widthSize, heightSize);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		
		Log.e("onlayout", "onlayout"+changed);
		if (!changed) {
			return;
		}
		
		
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View childView = getChildAt(i);
			
			int measuredWidth = childView.getMeasuredWidth();
			int measuredHeight = childView.getMeasuredHeight();
			
			childView.layout(l, 0, l + measuredWidth, measuredHeight);
		}
	}
	
	
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		
		final int actioin = ev.getAction();
		final int x = (int) ev.getX();
		
		switch (actioin) {
		case MotionEvent.ACTION_DOWN:
			mInterceptMotionX = x;
			break;

		case MotionEvent.ACTION_MOVE:
			final int deltaX = x - mInterceptMotionX;
			final int distance = Math.abs(deltaX);
			// �������������Ҳ���ͼ����Ϊ���Ҳ���ͼ���ƶ�
			// �����ƶ�����һ�����룬�����Լ��������Ķ�
			
			if ( canSliding(ev) && distance > mTouchSlop * 2) {

				// ��Ϊ��ʼֵ
				mLastX = x;
				if (mScrollRunnable != null) {
					mScrollRunnable.endScroll();
					mScrollRunnable = null;
				}
				
				// ����Touch Event ���ɵ�ǰViewGruop onTouchEvent����
				return true;
			}
			
			break;
			
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:	
			mInterceptMotionX = 0;
			break;
		}
		
		return false;
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		final int x = (int) event.getX();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			
			scrollIfNeed(x);
			return true;
			
		case MotionEvent.ACTION_UP:
			
			autoScrollIfNeed(x);
			break;
		}
		
		return false;
	}



	
	// ===========================================================
	// Private Methods
	// ===========================================================

	private void initSlidingMenu(Context context) {
		final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
	}
	
	/**
	 * ��ǰ��ָ���λ���Ƿ����Ҳ���ͼ������
	 * 
	 * @param event
	 * @return true ���Թ���
	 */
	private boolean canSliding(MotionEvent event) {
		
		final int scrolledXInt = (int) (event.getX() + getScrollX());
		final int scrolledYInt = (int) (event.getY() + getScrollY());
		
		Rect frame = new Rect();
		mRightView.getHitRect(frame);
		if (frame.contains(scrolledXInt, scrolledYInt)) {
			return true;
		}
		
		return false;
	}
	
	
	private void scrollIfNeed(final int x) {
		// �������ϴε�ƫ����
		int deltaX = x - mLastX;
		
		// �����ƶ�����
		if (x != mLastX) {
			// ��ʾ
			if (mLeftView.getVisibility() != View.VISIBLE) {
				mLeftView.setVisibility(View.VISIBLE);
			}
			
			int l = mRightView.getLeft();
			int t = mRightView.getTop();
			int b = mRightView.getBottom();
			
			// �Ҳ���ͼ�Ļ�������ֻ���������ͼ��Χ�ڻ���
			int rightViewLeft = Math.max(mLeftView.getLeft(), l + deltaX);
			rightViewLeft = Math.min(mLeftView.getRight(), rightViewLeft);
			
			// ��������ָ����
			mRightView.layout(rightViewLeft, t, rightViewLeft + mRightView.getWidth(), b);
		}
		
		// �����������
		if (mRightView.getLeft() == mLeftView.getLeft()) {
			mLeftView.setVisibility(View.INVISIBLE);
		}
		
		// ��¼��ǰֵ���´μ���		
		mLastX = x;
	}
	
	
	private void autoScrollIfNeed(final int x) {
		mScrollRunnable = new ScrollRunnable();
		
		// �����жϻ�������
		final int deltaX = x - mInterceptMotionX;
		// x�����������ε�������ָ���µ��ֵ��С��0˵������ָ���󻬶�
		boolean moveLeft = deltaX <= 0;
		
		// �������볬�������ͼһ�룬�Ż�������ָ�������
		final int distance = Math.abs(deltaX);
		if (distance < mLeftView.getWidth() / 2) {
			// ����������ȥ
			moveLeft = !moveLeft;
		}
		
		// �����Զ�����
		mScrollRunnable.startScroll(moveLeft);
	}

	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
	private class ScrollRunnable implements Runnable {
		// ���������࣬�ṩ��ʼλ�ã��ƶ�ƫ�ƣ��ƶ���ʱ�䣬���Ի�ȡÿ�ι�������
		private Scroller mScroller = new Scroller(getContext());
		
		@Override
		public void run() {
			final Scroller scroller = mScroller;
			// �������ƫ�ƣ������Ƿ���Խ��Ź���
			boolean more = scroller.computeScrollOffset();
			// ������ȡ��Ҫ��������λ��
			final int x = scroller.getCurrX();
			
			if (more) {
				// ���ֶ��������õķ�����ͬ
				scrollIfNeed(x);
				// ��ǰ���߳��Ѿ�ִ���꣬������Ҫ���Ź���
				// ���԰ѵ�ǰRunnable�ٴ���ӵ���Ϣ������
				post(this);
			} else {
				// ����Ҫ����
				endScroll();
			}
			
		}
		
		
		private void startScroll(boolean moveLeft) {
			// ����ǰ���ó�ʼֵ
			mLastX = mRightView.getLeft();
			
			int dx = 0;
			
			// �����ƶ��ܾ���
			if (moveLeft) {
				// ��ǰ������ͼ���߽����
				dx = mLeftView.getLeft() - mRightView.getLeft();
			} else {
				// ���Ҳ�߽�
				dx = mLeftView.getRight() - mRightView.getLeft();
			}
			
			// ��ʼ����
			mScroller.startScroll(mRightView.getLeft(), 0, dx, 0, 300);
			// �ѵ�ǰRunnable��ӵ���Ϣ������
			post(this);
		}
		
		private void endScroll() {
			// ����Ϣ�����аѵ�ǰRunnableɾ��ֹͣ����
			removeCallbacks(this);
		}
		
	}
	

	
}
