package com.irun.sm.ui.demo.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/***
 * @author huangsm
 * @date 2012-7-9
 * @email huangsanm@gmail.com
 * @desc 添加广告adview
 */
public class AdViewActivity extends Activity {

	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adview_layout);
		
		mContext = this;
		findViewById(R.id.ad_layout).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(mContext, LayoutActivity.class);
				startActivity(i);
			}
		});
		
		findViewById(R.id.ad_code).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(mContext, CodeActivity.class);
				startActivity(i);
			}
		});
		
		findViewById(R.id.ad_xml).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(mContext, XMLAdViewActivity.class);
				startActivity(i);
			}
		});
	}

}
