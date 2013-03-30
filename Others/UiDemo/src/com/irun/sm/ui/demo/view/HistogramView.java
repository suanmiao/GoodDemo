package com.irun.sm.ui.demo.view;

import java.text.NumberFormat;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import com.irun.sm.ui.demo.vo.HistogramItem;

/***
 * @author huangsm
 * @date 2012-10-30
 * @email huangsanm@gmail.com
 * @desc 绘制直方图
 */
public class HistogramView extends View {

	private List<HistogramItem> mDataSet;
	private int mHeight;
	private int mWidth;
    public HistogramView(Context context, List<HistogramItem> dataSet, int width, int height) {
        super(context);
        this.mDataSet = dataSet;
        this.mWidth = width;
        this.mHeight = height;
        
        if(mDataSet == null || mDataSet.isEmpty()){
        	throw new RuntimeException("please initialize dataset!");
        }
    }
    
    public HistogramView(Context context, AttributeSet attr){
    	super(context, attr);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
    	//draw background
    	Paint mPaint = new Paint();
    	mPaint.setColor(Color.BLUE);
    	mPaint.setStrokeWidth(2);
    	canvas.drawRect(0, 0, mWidth, mHeight, mPaint);
    	
    	mPaint.setColor(Color.WHITE);
    	mPaint.setStrokeWidth(0);
    	canvas.drawRect(2, 2, mWidth, mHeight, mPaint);
    	
    	//draw x/y axis
    	int xOffset = (int) (mWidth * 0.1);
    	int yOffset = (int) (mHeight * 0.1);
    	mPaint.setColor(Color.BLACK);
    	mPaint.setStrokeWidth(2);
    	canvas.drawLine(2 + xOffset, 2, 2 + xOffset, mHeight - 2 - yOffset, mPaint);
    	canvas.drawLine(mWidth - 2, mHeight - 2 - yOffset, 2 + xOffset, mHeight - 2 - yOffset, mPaint);
    	
    	final int length = mDataSet.size();
    	final int xpadding = 10;
    	//draw x axsi
    	final int xUnit = (mWidth - 2 - xOffset) / length;
    	for (int i = 0; i < length; i++) {
    		String text = mDataSet.get(i).getTitle();
    		//y指的是bottom的坐标
			canvas.drawText(text, xOffset + 2 + xpadding + xUnit * i, mHeight - yOffset + 10, mPaint);
		}
    	
    	//draw x axsi
    	int yUnit = 22;
    	int unitValue = (mHeight - 2 - yOffset) / yUnit;
    	mPaint.setStyle(Style.STROKE);
    	mPaint.setColor(Color.LTGRAY);
    	mPaint.setStrokeWidth(1);
    	mPaint.setPathEffect(new DashPathEffect(new float[]{1, 4}, 0));
    	for (int i = 0; i < 20; i++) {
			canvas.drawLine(
					2 + xOffset, 
					mHeight - 2 - yOffset - (unitValue * (i+1)), 
					mWidth - 2, 
					mHeight - 2 - yOffset - (unitValue * (i+1)), 
					mPaint);
		}
    	//还原绘制圆点
    	mPaint.setColor(Color.BLACK);  
    	mPaint.setStyle(Style.STROKE);  
    	mPaint.setStrokeWidth(0);  
    	mPaint.setPathEffect(null);
    	
    	float max = 0.f, min = 0.f;
    	for (int i = 0; i < length; i++) {
    		HistogramItem item = mDataSet.get(i);
			if(item.getValue() > max){
				max = item.getValue();
			}
			if(item.getValue() < min){
				min = item.getValue();
			}
		}
    	//draw y axis
    	//设置最大和最小小数点位数
    	NumberFormat numberFormat = NumberFormat.getInstance();
    	numberFormat.setMaximumFractionDigits(2);
    	numberFormat.setMinimumFractionDigits(2);
    	float yMarkers = (max - min) / yUnit;
    	for (int i = 0; i < 20; i++) {
			float markerValue = yMarkers * (i + 1);
			canvas.drawText(
					 numberFormat.format(markerValue), 
					 3, 
					 mHeight - 2 - yOffset - (unitValue * (i+1)), 
					 mPaint);
		}
    	//start draw dataset
    	mPaint.setStyle(Style.FILL);
    	mPaint.setStrokeWidth(0);
    	int barWidth = (int) (xUnit / Math.pow(length, 2));
    	int interval = barWidth / 2;
    	for (int i = 0; i < length; i++) {
    		HistogramItem item = mDataSet.get(i);
    		int startPosition = xOffset + 2 + xpadding + xUnit * i;
    		System.out.println("startPosition:" + startPosition);
    		int barHeight = (int) ((item.getValue() / yMarkers) * unitValue);
    		System.out.println("height:" + barHeight);
    		mPaint.setColor(item.getColor());
    		float left = startPosition + barWidth * i + interval * i;
    		float top = mHeight - 2 - yOffset - barHeight;
    		float right = startPosition + barWidth * i + interval * i + barWidth + 10;
    		float bottom = mHeight- 2 - yOffset;
    		canvas.drawRect(left, top, right, bottom, mPaint);
		}
    }
    
}
