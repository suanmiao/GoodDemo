package com.irun.sm.ui.demo.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabWidget;

import com.irun.sm.ui.demo.ui.R;

/***
 * @author huangsm
 * @date 2013-1-11
 * @email huangsanm@gmail.com
 * @desc page tab
 */
public class FragmentPageTabActivity extends FragmentActivity {

	private TabHost mTabHost;
	private ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_tab_pager);
		
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		
		mViewPager = (ViewPager) findViewById(R.id.pager);
		
		TabPagerAdapter tabAdapter = new TabPagerAdapter(this, mTabHost, mViewPager);
		
		RelativeLayout app = (RelativeLayout) getLayoutInflater().inflate(R.layout.app_tab_layout, null);
		tabAdapter.addTabs(mTabHost.newTabSpec("Apps").setIndicator(app), AppsFragment.class, null);

		RelativeLayout contacts = (RelativeLayout) getLayoutInflater().inflate(R.layout.contacts_tab_layout, null);
		tabAdapter.addTabs(mTabHost.newTabSpec("Contact").setIndicator(contacts), ContactsFragment.class, null);

		RelativeLayout message = (RelativeLayout) getLayoutInflater().inflate(R.layout.message_tab_layout, null);
		tabAdapter.addTabs(mTabHost.newTabSpec("Message").setIndicator(message), MessageFragment.class, null);
		
		if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());
    }
    
	/**
	 * 适配
	 * @author huangsm
	 */
	public static class TabPagerAdapter extends FragmentPagerAdapter 
		implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

		private final TabHost mTabHost;
		private final Context mContext;
		private final ViewPager mViewPage;
		private List<TabInfo> mTabs = new ArrayList<TabInfo>();
		
		//tab info
		static final class TabInfo {
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(String _tag, Class<?> _clss, Bundle _args) {
				tag = _tag;
				clss = _clss;
				args = _args;
			}
		}
		
		//tab context
		static class DummyTabFactory implements TabContentFactory {
			private final Context mContext;
			
			DummyTabFactory(Context context){
				mContext = context;
			}
			
			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumHeight(0);
				v.setMinimumWidth(0);
				return v;
			}
		}
		
		public TabPagerAdapter(FragmentPageTabActivity activity, TabHost tabHost, ViewPager pager) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mTabHost = tabHost;
			mViewPage = pager;
			
			mTabHost.setOnTabChangedListener(this);
			mViewPage.setAdapter(this);
			mViewPage.setOnPageChangeListener(this);
		}
		
		//添加tab
		public void addTabs(TabHost.TabSpec tabSpec, Class<?> item, Bundle params){
			tabSpec.setContent(new DummyTabFactory(mContext));
			String tag = tabSpec.getTag();
			
			TabInfo info = new TabInfo(tag, item, params);
			mTabs.add(info);
			mTabHost.addTab(tabSpec);
			notifyDataSetChanged();
		}

		//设置viewpage选中
		@Override
		public void onTabChanged(String tabId) {
			int position = mTabHost.getCurrentTab();
			mViewPage.setCurrentItem(position);
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			return Fragment.instantiate(mContext, info.clss.getName(), info.args);
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int position) {
			TabWidget widget = mTabHost.getTabWidget();
			//获取前一个view的focus
			int oldFocusability = widget.getDescendantFocusability();
			//取消focus
			widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
			//激活当前
			mTabHost.setCurrentTab(position);
			//设置focus
			widget.setDescendantFocusability(oldFocusability);
		}		
	}
	
}