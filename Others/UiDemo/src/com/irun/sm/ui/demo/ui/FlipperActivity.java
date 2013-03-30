package com.irun.sm.ui.demo.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ViewFlipper;

/***
 * @author huangsm
 * @date 2012-11-2
 * @email huangsanm@gmail.com
 * @desc 滚动activity
 */
public class FlipperActivity extends Activity implements OnClickListener {

	private ViewFlipper mFlipper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flipper_layout);
		
		mFlipper = (ViewFlipper) findViewById(R.id.flipper);
		mFlipper.startFlipping();
		
		findViewById(R.id.text1).setOnClickListener(this);
		findViewById(R.id.text2).setOnClickListener(this);
		findViewById(R.id.text3).setOnClickListener(this);
		findViewById(R.id.text4).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.text1 :
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse("http://www.google.com.hk"));
				startActivity(i);
				break;
			case R.id.text2:
				Intent i1 = new Intent(Intent.ACTION_VIEW);
				i1.setData(Uri.parse("http://www.google.com.hk"));
				startActivity(i1);
				break;
			case R.id.text3:
				Intent i2 = new Intent(Intent.ACTION_VIEW);
				i2.setData(Uri.parse("http://www.google.com.hk"));
				startActivity(i2);
				break;
			case R.id.text4:
				Intent i3 = new Intent(Intent.ACTION_VIEW);
				i3.setData(Uri.parse("http://www.google.com.hk"));
				startActivity(i3);
				break;
		}
	}
	
	
}
