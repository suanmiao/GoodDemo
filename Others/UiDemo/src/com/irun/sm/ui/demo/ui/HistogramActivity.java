package com.irun.sm.ui.demo.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;

import com.irun.sm.ui.demo.view.HistogramView;
import com.irun.sm.ui.demo.vo.HistogramItem;

/***
 * @author huangsm
 * @date 2012-10-30
 * @email huangsanm@gmail.com
 * @desc 绘制直方图
 */
public class HistogramActivity extends Activity {
    
	private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        
        //数据源
        List<HistogramItem> dataSet = new ArrayList<HistogramItem>();
        for (int i = 0; i < 10; i++) {
        	String title = "item" + i;
        	float value = (float) (Math.random() * 100);
        	HistogramItem item1 = new HistogramItem(title, value, Color.BLUE);
        	dataSet.add(item1);
		}
        
        Display d = getWindowManager().getDefaultDisplay();
        HistogramView view = new HistogramView(mContext, dataSet, d.getWidth(), d.getHeight());
        setContentView(view);
    }
    
    
}
