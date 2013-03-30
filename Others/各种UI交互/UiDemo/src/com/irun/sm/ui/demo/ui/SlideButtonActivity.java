package com.irun.sm.ui.demo.ui;

import com.irun.sm.ui.demo.view.SlideButton;

import android.app.Activity;
import android.os.Bundle;

/***
 * @author huangsm
 * @date 2012-11-7
 * @email huangsanm@gmail.com
 * @desc slide button
 */
public class SlideButtonActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SlideButton slide = new SlideButton(this);
		setContentView(slide);
	}
}
