package com.irun.sm.ui.demo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.adview.AdViewInterface;
import com.adview.AdViewLayout;

/***
 * @author huangsm
 * @date 2012-7-9
 * @email huangsanm@gmail.com
 * @desc 布局方式添加
 */
public class LayoutActivity extends Activity implements AdViewInterface{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout);
		
		
		//AdViewTargeting.setRunMode(RunMode.TEST);
		//AdViewTargeting.setUpdateMode(UpdateMode.EVERYTIME);
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.adLayout);
		AdViewLayout adViewLayout = new AdViewLayout(this, "SDK201207150706098ng5d8fpuvjg0gi");
		adViewLayout.setAdViewInterface(this);
		layout.addView(adViewLayout);
		layout.invalidate();
	}

	@Override
	public void onClickAd() {
		
	}

	@Override
	public void onDisplayAd() {
		
	}
}
