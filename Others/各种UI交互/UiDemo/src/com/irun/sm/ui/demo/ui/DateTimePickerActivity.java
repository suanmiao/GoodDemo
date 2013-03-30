package com.irun.sm.ui.demo.ui;

import com.irun.sm.ui.demo.view.DateTimePickerView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

/***
 * @author huangsm
 * @date 2012-6-25
 * @email huangsanm@gmail.com
 * @desc 日历
 */
public class DateTimePickerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datetimepicker_layout);
		
		LinearLayout calendarContent = (LinearLayout) findViewById(R.id.calendar_content); 
        DateTimePickerView dateTimeView = new DateTimePickerView(this, 224, 224);
        calendarContent.addView(dateTimeView);
	}
	
	
}
