package com.irun.sm.ui.demo.ui;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/***
 * @author huangsm
 * @date 2012-7-4
 * @email huangsanm@gmail.com
 * @desc activitygroup实现底部
 */
public class TabActivity extends ActivityGroup implements OnClickListener {

	private LinearLayout mLayout;
	private RadioGroup mRadioGroup;
	private HorizontalScrollView mHorizontalScrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_group);
		
		mLayout = (LinearLayout) findViewById(R.id.container);
		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.scrollView);
		
		RadioButton tab1 = (RadioButton) findViewById(R.id.tab1);
		tab1.setOnClickListener(this);
		tab1.setId(1);
		
		RadioButton tab2 = (RadioButton) findViewById(R.id.tab2);
		tab2.setOnClickListener(this);
		tab2.setId(2);
		
		RadioButton tab3 = (RadioButton) findViewById(R.id.tab3);
		tab3.setOnClickListener(this);
		tab3.setId(3);
		
		RadioButton tab4 = (RadioButton) findViewById(R.id.tab4);
		tab4.setOnClickListener(this);
		tab4.setId(4);
		
		RadioButton tab5 = (RadioButton) findViewById(R.id.tab5);
		tab5.setOnClickListener(this);
		tab5.setId(5);
		
		RadioButton tab6 = (RadioButton) findViewById(R.id.tab6);
		tab6.setOnClickListener(this);
		tab6.setId(6);
		
		RadioButton tab7 = (RadioButton) findViewById(R.id.tab7);
		tab7.setOnClickListener(this);
		tab7.setId(7);

		//默认
		mRadioGroup.check(1);
		startActivityGroup(1);
	}

	@Override
	public void onClick(View v) {
		startActivityGroup(v.getId());
	}

	private void startActivityGroup(int id) {
		View view = null;
		mLayout.removeAllViews();
		switch (id) {
			case 1:
				mRadioGroup.check(1);
				view = getLocalActivityManager()
						.startActivity("tab1", new Intent(this, Tab1Activity.class)).getDecorView();
				break;
			case 2:
				mRadioGroup.check(2);
				view = getLocalActivityManager()
						.startActivity("tab2", new Intent(this, Tab2Activity.class)).getDecorView();
				break;
			case 3:
				mRadioGroup.check(3);
				view = getLocalActivityManager()
						.startActivity("tab3", new Intent(this, Tab3Activity.class)).getDecorView();
				break;
			case 4:
				mRadioGroup.check(4);
				view = getLocalActivityManager()
						.startActivity("tab4", new Intent(this, Tab3Activity.class)).getDecorView();
				break;
			case 5:
				mRadioGroup.check(5);
				view = getLocalActivityManager()
						.startActivity("tab5", new Intent(this, Tab3Activity.class)).getDecorView();
				break;
			case 6:
				mRadioGroup.check(6);
				view = getLocalActivityManager()
						.startActivity("tab6", new Intent(this, Tab3Activity.class)).getDecorView();
				break;
			case 7:
				mRadioGroup.check(7);
				view = getLocalActivityManager()
						.startActivity("tab7", new Intent(this, Tab3Activity.class)).getDecorView();
				break;
		}
		final int totalWidth = mRadioGroup.getWidth();
		final int childWidth = totalWidth / mRadioGroup.getChildCount();
		final int index = mRadioGroup.getCheckedRadioButtonId() - 1;
		final int childLeft = mRadioGroup.getChildAt(index).getLeft() - mHorizontalScrollView.getScrollX();
		final int centerLine = mHorizontalScrollView.getWidth() / 2;
		if(childLeft > centerLine){
			mHorizontalScrollView.smoothScrollBy(childWidth, 0);
		}else{
			if(childLeft < (centerLine - childWidth)){
				mHorizontalScrollView.smoothScrollBy(-childWidth, 0);
			}
		}
		System.out.println("x:" + mHorizontalScrollView.getScrollX());
		mLayout.addView(view);
	}
	
}
