package com.irun.sm.ui.demo.ui;

import com.irun.sm.ui.demo.view.MarkImageView;

import android.app.Activity;
import android.os.Bundle;

/***
 * @author huangsm
 * @date 2012-11-21
 * @email huangsanm@gmail.com
 * @desc 标记mark
 * 
 */
public class MarkViewActivity extends Activity {

	private MarkImageView mMarkView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mark_imageview);
		
		mMarkView = (MarkImageView) findViewById(R.id.mark_image);
		mMarkView.setMark(100);
	}
	
}
