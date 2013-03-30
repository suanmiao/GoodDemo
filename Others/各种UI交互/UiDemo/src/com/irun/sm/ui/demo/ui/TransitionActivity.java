package com.irun.sm.ui.demo.ui;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.widget.ImageView;

/***
 * @author huangsm
 * @date 2012-11-8
 * @email huangsanm@gmail.com
 * @desc transitioin 淡入淡出动画
 */
public class TransitionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final Resources res = getResources();
		TransitionDrawable transition = (TransitionDrawable) res.getDrawable(R.drawable.transition);
		transition.startTransition(2500);
		ImageView iv = new ImageView(this);
		iv.setImageDrawable(transition);
		setContentView(iv);
	}
}
