package com.irun.sm.ui.demo.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.irun.sm.ui.demo.ui.R;

/***
 * @author huangsm
 * @date 2012-12-26
 * @email huangsanm@gmail.com
 * @desc 滚动的直方图
 */
public class ScrollHistogramView extends View {

	private final static String TAG = "ScrollHistogramView";
	
	//背景线间隔
	private int mBgLineInterval;
	//柱状之间的间隔
	private int mDateInterval;
	//宽度
	private int mWidth;
	//高度
	private int mHeight;
	
	//数据源
	private int[] mData;
	
	public ScrollHistogramView(Context context, String data, int width, int height) {
		super(context);
		mData = convertData(data);
		
		final Resources res = context.getResources();
		mBgLineInterval = res.getDimensionPixelSize(R.dimen.bg_line_interval);
		mDateInterval = res.getDimensionPixelSize(R.dimen.date_interval);
		mWidth = mData.length == 0 ? width : mData.length * mDateInterval;
		
		//绘制数据源
		Log.i(TAG, "len:" + mData.length);
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		Rect rect = new Rect(0, 0, mWidth, mHeight);
		drawBackground(canvas, rect);
	}
	
	/**
	 * 绘制背景
	 * @param canvas
	 */
	private void drawBackground(Canvas c, Rect r){
		final int bgColor = 0xFFF7F7F7;
        
	}
	
	
	
	/**
	 * 转换数据
	 * @param data[float1,float1,float1,float1,null,null,float1,...]
	 * @return
	 */
	private int[] convertData(String data){
		int[] arrays;
		if(TextUtils.isEmpty(data)){
			arrays = new int[0];
		}else{
			String[] strs = data.split(",");
			arrays = new int[strs.length];
			for (int i = 0; i < strs.length; i++) {
				arrays[i] = TextUtils.isEmpty(strs[i]) ? 0 : (int)((float)Float.valueOf(strs[i]));
			}
		}
		return arrays;
	}
}
