package com.irun.sm.ui.demo.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.irun.sm.ui.demo.ui.R;

/***
 * @author huangsm
 * @date 2012-2-14
 * @email huangsanm@gmail.com
 * @desc 分享
 */
public class ShareAdapter extends BaseAdapter {
	private final static String PACKAGENAME = "com.sina.weibo";

	private Context mContext;
	private PackageManager mPackageManager;
	private Intent mIntent;
	private LayoutInflater mInflater;

	private List<ResolveInfo> mList;
	private List<DisplayResolveInfo> mDisplayResolveInfoList;

	public ShareAdapter(Context context, Intent intent) {
		mContext = context;
		mPackageManager = mContext.getPackageManager();
		mIntent = new Intent(intent);
		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mList = mContext.getPackageManager().queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		//排序
		ResolveInfo.DisplayNameComparator comparator = new ResolveInfo.DisplayNameComparator(
				mPackageManager);
		Collections.sort(mList, comparator);

		mDisplayResolveInfoList = new ArrayList<DisplayResolveInfo>();
		if (mList == null || mList.isEmpty()) {
			mList = new ArrayList<ResolveInfo>();
		}

		final int N = mList.size();
		for (int i = 0; i < N; i++) {
			ResolveInfo ri = mList.get(i);
			CharSequence label = ri.loadLabel(mPackageManager);
			DisplayResolveInfo d = new DisplayResolveInfo(ri, null, null, label, null);
			mDisplayResolveInfoList.add(d);
		}
	}

	@Override
	public int getCount() {
		return mDisplayResolveInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		return mDisplayResolveInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item;
		if(convertView == null) {
			item = mInflater.inflate(R.layout.share_item, null);
		} else {
			item = convertView;
		}
		DisplayResolveInfo info = mDisplayResolveInfoList.get(position);
		
		ImageView i = (ImageView) item.findViewById(R.id.share_item_icon);
		if(info.mDrawable == null){
			i.setImageDrawable(info.mResoleInfo.loadIcon(mPackageManager));
		}else{
			i.setImageDrawable(info.mDrawable);
		}
		
		TextView t = (TextView) item.findViewById(R.id.share_item_text);
		t.setText(info.mLabel);
		return item;
	}
	
	public ResolveInfo getResolveInfo(int index){
		if(mDisplayResolveInfoList == null){
			return null;
		}
		DisplayResolveInfo d = mDisplayResolveInfoList.get(index);
		if(d.mResoleInfo == null){
			return null;
		}
		return d.mResoleInfo;
	}
	
	//获取intent
	public Intent getIntentForPosition(int index) {
		if(mDisplayResolveInfoList == null){
			return null;
		}
		DisplayResolveInfo d = mDisplayResolveInfoList.get(index);
		Intent i = new Intent(d.mIntent == null ? mIntent : d.mIntent);
		i.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
		if(d.mResoleInfo != null){
			ActivityInfo a = d.mResoleInfo.activityInfo;
			i.setComponent(new ComponentName(a.applicationInfo.packageName, a.name));
		}
		return i;
	}
	
	//检查是否已经安装[新浪微博]
	boolean isInstallApplication(Context context, String packageName){
		try {
			mPackageManager
					.getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
		
	}

	/**
	 * ������ vo
	 * @author Administrator
	 */
	class DisplayResolveInfo {
		private Intent mIntent;
		private ResolveInfo mResoleInfo;
		private CharSequence mLabel;
		private Drawable mDrawable;
	
		DisplayResolveInfo(ResolveInfo resolveInfo, Intent intent,
				CharSequence info, CharSequence label, Drawable d) {
			this.mIntent = intent;
			this.mResoleInfo = resolveInfo;
			this.mLabel = label;
			this.mDrawable = d;
		}
	}
}