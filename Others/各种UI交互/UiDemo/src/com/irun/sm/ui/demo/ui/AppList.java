package com.irun.sm.ui.demo.ui;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import com.irun.sm.ui.demo.utils.BaseActivity;
import com.irun.sm.ui.demo.utils.Callback;
import com.irun.sm.ui.demo.view.AppPanel;
import com.irun.sm.ui.demo.view.ScrollPanel;

/***
 * @author huangsm
 * @email huangsanm@gmail.com
 * @date 2011-11-11
 * @description 
 **/
public class AppList extends BaseActivity{

	private Context mCtx;
	
	//保存所有app
	private List<ResolveInfo> mList;
	private PackageManager mPackageManager;
	private ScrollPanel layout;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCtx = this;
		//初始化
		mPackageManager = getPackageManager();
		
		//异步加载
		doAsync(R.string.title_resource, R.string.message_resource, new Callable<List<ResolveInfo>>() {
			@Override
			public List<ResolveInfo> call() throws Exception {
				//查询所有app
				Intent i = new Intent(Intent.ACTION_MAIN, null);
				i.addCategory(Intent.CATEGORY_LAUNCHER);
				mList = mPackageManager.queryIntentActivities(i, 0);
				//排序
				Collections.sort(mList, new ResolveInfo.DisplayNameComparator(mPackageManager));
				return mList;
			}
		}, new Callback<List<ResolveInfo>>() {
			@Override
			public void onCallback(List<ResolveInfo> pCallbackValue) {
				layout = new ScrollPanel(mCtx);
				//初始化
				AppPanel panel = new AppPanel(mCtx, mPackageManager, pCallbackValue);
				layout.addView(panel);
				setContentView(layout);
			}
		});
	}
	
	/*
	  private final static int PAGE_SIZE = 30;
	  private void init(){
		final int totalPage = (int)Math.ceil(mList.size() / 1.0 / PAGE_SIZE);
		Log.i(TAG, "totalPage:" + totalPage);
		for (int i = 0; i < totalPage; i++) {
			GridView grid = new GridView(mCtx);
			grid.setNumColumns(6);
			grid.setAdapter(new AppAdapter(mCtx, i));
			grid.setOnItemClickListener(this);
			layout.addView(grid);
		}
	}*/
	/*
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ResolveInfo info = mList.get(position);
		//包名
		String name = info.activityInfo.packageName;
		//程序入口app
		String activity = info.activityInfo.name;
		ComponentName component = new ComponentName(name, activity);
		Intent i = new Intent();
		i.setComponent(component);
		startActivity(i);
	}
	
	//适配器
	public class AppAdapter extends BaseAdapter {
		private List<ResolveInfo> list;
    	private Context mCtx;
    	public AppAdapter(Context context, int page){
    		this.mCtx = context;
    		
    		list = new ArrayList<ResolveInfo>();
    		//设置一页包含多少个app
    		int i = page * PAGE_SIZE;
    		int total = i + PAGE_SIZE;
    		while (i < mList.size() && i < total) {
				list.add(mList.get(i));
				i ++;
			}
    	}
		public int getCount() {
			return list.size();
		}
		public Object getItem(int position) {
			return list.get(position);
		}
		public long getItemId(int position) {
			return position;
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			ResolveInfo info = list.get(position);
			ViewHolder holder;
			if(convertView == null){
				holder = new ViewHolder();
				
				View view = LayoutInflater.from(mCtx).inflate(R.layout.app_item, null);
				holder.imageView = (ImageView) view.findViewById(R.id.imageView);
				holder.textView = (TextView) view.findViewById(R.id.textView);
				
				view.setTag(holder);
				convertView = view;
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.imageView.setImageDrawable(info.loadIcon(mPackageManager));
			holder.textView.setText(info.loadLabel(mPackageManager));
			return convertView;
		}
    }
	
	public class ViewHolder {
		private ImageView imageView;
		private TextView textView;
	}*/
}
