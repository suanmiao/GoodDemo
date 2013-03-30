package com.irun.sm.ui.demo.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.irun.sm.ui.demo.utils.ActivityUtils;

public class UiDemoActivity extends ListActivity {

	private Context mContext;
	private PackageManager mPackageManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActivityUtils.requestNotTitleBar(this);
		
		mContext = this;
		
		/*Intent i = new Intent(mContext, ImageActivity.class);
		startActivity(i);
		finish();*/
		
		mPackageManager = getPackageManager();

		SimpleAdapter adapter = new SimpleAdapter(mContext, getListData(),
				android.R.layout.simple_list_item_1, new String[] { "title" },
				new int[] { android.R.id.text1});
		setListAdapter(adapter);
		getListView().setTextFilterEnabled(true);
	}

	private List<Map<String, Object>> getListData() {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN);
		mainIntent.addCategory("android.intent.category.UIDOME");
		List<ResolveInfo> list = mPackageManager.queryIntentActivities(mainIntent, 0);
		final int N = list.size();
		for (int i = 0; i < N; i++) {
			ResolveInfo info = list.get(i);
			CharSequence labelSeq = info.loadLabel(mPackageManager);
			String label = TextUtils.isEmpty(labelSeq) ? info.activityInfo.name
					: labelSeq.toString();
			addItem(data, activityIntent(info.activityInfo.applicationInfo.packageName, info.activityInfo.name), label);
		}
		return data;
	}

	public Intent activityIntent(String pkg, String componentName) {
		Intent intent = new Intent();
		intent.setClassName(pkg, componentName);
		return intent;
	}

	public void addItem(List<Map<String, Object>> data, Intent intent,
			String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", name);
		map.put("intent", intent);
		data.add(map);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Map<String, Object> map = (Map<String, Object>) l.getItemAtPosition(position);
		Intent intent = (Intent) map.get("intent");
		startActivity(intent);
	}
}