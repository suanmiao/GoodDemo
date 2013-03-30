package com.irun.sm.ui.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

/***
 * @author huangsm
 * @date 2012-6-20
 * @email huangsanm@gmail.com
 * @desc 时间控件
 */
public class DateTimePickerView extends View {

	private final static String[] mWEEKS = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
	private Context mContext;
	
	private Paint mPaintWeek;
	private Paint mPaintDate;
	private int mGridWidth;
	private int mGridHeight;
	public DateTimePickerView(Context context, int width, int height) {
		super(context);
		mContext = context;
		setLayoutParams(new ViewGroup.LayoutParams(width, height));
		
		mGridWidth = width;// / 7;//mContext.getResources().getDimensionPixelSize(R.dimen.cell_width);
		mGridHeight = height;// / 7;//mContext.getResources().getDimensionPixelSize(R.dimen.cell_height);
		
		mPaintWeek = new Paint();
		mPaintWeek.setColor(Color.WHITE);
		mPaintWeek.setAntiAlias(true);
		mPaintWeek.setFakeBoldText(true);
		mPaintWeek.setTextSize(15);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//super.onDraw(canvas);
		System.out.println("w:" + mGridWidth + ",h:" + mGridHeight);
		//绘制星期
		int week_left = 0;
		for (int i = 0; i < mWEEKS.length; i++) {
			canvas.drawText(mWEEKS[i], week_left, mGridHeight, mPaintWeek);
			week_left += mGridWidth;
		}
		
		//绘制日期
		
	}
}
