package com.irun.sm.ui.demo.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

/***
 * @author huangsm
 * @date 2012-10-9
 * @email huangsanm@gmail.com
 * @desc 使用support里面的viewpage
 * 需要导入[\extras\android\compatibility\]android-support-vX.jar:x为版本号
 */
public class SupportViewPage extends Activity {

	private final static int[] mThumb = {
		R.drawable.ktjt1, R.drawable.ktjt2, R.drawable.ktjt3, 
		R.drawable.ktjt4, R.drawable.ktjt5  
	};
	private ViewPager mViewPager;
	private PagerImageAdapter mImageAdapter;
	private List<ImageView> mList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.support_viewpage);
		mViewPager = (ViewPager) findViewById(R.id.support_viewpage);
		mList = new ArrayList<ImageView>(5);
		for (int i = 0; i < mThumb.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setImageResource(mThumb[i]);
			mList.add(iv);
		}
		mImageAdapter = new PagerImageAdapter();
		mViewPager.setAdapter(mImageAdapter);
		//viewpage
		mViewPager.setCurrentItem(0);
	}
	
	//数据填充
	class PagerImageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mList.size();
		}
		
		//初始化第一屏
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((ViewPager) container).addView(mList.get(position), 0);
			return mList.get(position);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView(mList.get(position));
		}
	}
}
