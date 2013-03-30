package com.irun.sm.ui.demo.ui;

import java.util.Calendar;

import com.irun.sm.ui.demo.view.ScrollHistogramView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;

/***
 * @author huangsm
 * @date 2012-12-26
 * @email huangsanm@gmail.com
 * @desc 滚动的histogram
 */
public class ScrollHistogramActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Calendar cal = Calendar.getInstance();
		int maxDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		StringBuffer strb = new StringBuffer();
		for (int i = 0; i < maxDayOfMonth; i++) {
			float value = (float) (Math.random() * 100);
			strb.append(value).append(",");
		}
		String data = strb.substring(0, strb.length() - 1);
		System.out.println("data:" + data);
		
		//滚动view
		Display d = getWindowManager().getDefaultDisplay();
		ScrollHistogramView histogramView = new ScrollHistogramView(this, data, d.getWidth(), d.getHeight());
		setContentView(histogramView);
	}
}
