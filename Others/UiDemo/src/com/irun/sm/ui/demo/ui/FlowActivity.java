package com.irun.sm.ui.demo.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.irun.sm.ui.demo.utils.ActivityUtils;
import com.irun.sm.ui.demo.utils.Callback;
import com.irun.sm.ui.demo.utils.TrafficStatsAgo;

/***
 * @author huangsm
 * @date 2013-1-6
 * @email huangsanm@gmail.com
 * @desc 流量统计 
 	sdk 2.2+
 	static long getMobileRxBytes()//获取通过Mobile连接收到的字节总数，但不包含WiFi
	static long getMobileRxPackets()//获取Mobile连接收到的数据包总数
	static long getMobileTxBytes()//Mobile发送的总字节数
	static long getMobileTxPackets()//Mobile发送的总数据包数
	static long getTotalRxBytes()//获取总的接受字节数，包含Mobile和WiFi等
	static long getTotalRxPackets()//总的接受数据包数，包含Mobile和WiFi等
	static long getTotalTxBytes()//总的发送字节数，包含Mobile和WiFi等
	static long getTotalTxPackets()//发送的总数据包数，包含Mobile和WiFi等
 	static long getUidRxBytes(int uid)//获取某个网络UID的接受字节数
	static long getUidTxBytes(int uid) //获取某个网络UID的发送字节数
	http://blog.csdn.net/zmwell/article/details/7560554
 */
public class FlowActivity extends ListActivity {
	
	static final String INTERNET = "android.permission.INTERNET";

	private List<TrafficItem> mItems = new ArrayList<FlowActivity.TrafficItem>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*TextView tv = new TextView(this);
		setContentView(tv);
		
		StringBuffer text = new StringBuffer("flow:\n");
		text.append("通过Mobile连接收到的字节总数:").append(formatFlow(TrafficStats.getMobileRxBytes())).append("\n");
		text.append("通过Mobile连接收到的数据包总数:").append(formatFlow(TrafficStats.getMobileRxPackets())).append("\n");
		text.append("通过Mobile连接发送的字节总数:").append(formatFlow(TrafficStats.getMobileTxBytes())).append("\n");
		text.append("通过Mobile连接发送的数据包总数:").append(formatFlow(TrafficStats.getMobileTxPackets())).append("\n");
		text.append("总的接收字节数，包含Mobile和WiFi:").append(formatFlow(TrafficStats.getTotalRxBytes())).append("\n");
		text.append("总的接收数据包数，包含Mobile和WiFi:").append(formatFlow(TrafficStats.getTotalRxPackets())).append("\n");
		text.append("总的发送字节数，包含Mobile和WiFi:").append(formatFlow(TrafficStats.getTotalTxBytes())).append("\n");
		text.append("总的发送数据包数，包含Mobile和WiFi:").append(formatFlow(TrafficStats.getTotalTxPackets())).append("\n");
		tv.setText(text.toString());*/
		
		setContentView(R.layout.traffic_layout);
		
		ActivityUtils.doAsync(this, "稍等...", "请稍候，正在加载...", new Callable<List<TrafficItem>>() {
			@Override
			public List<TrafficItem> call() throws Exception {
				return getAppTrafficList();
			}
		}, new Callback<List<TrafficItem>>() {
			public void onCallback(List<TrafficItem> pCallbackValue) {
				mItems.addAll(getAppTrafficList());
				TrafficAdapter adapter = new TrafficAdapter(mItems);
				setListAdapter(adapter);
			};
		});
	}
	
	private List<TrafficItem> getAppTrafficList(){
		final PackageManager pm = getPackageManager();
		List<TrafficItem> trafficList = new ArrayList<TrafficItem>();
		//获取所有安装的package
		List<PackageInfo> infos = 
				pm.getInstalledPackages(PackageManager.GET_PERMISSIONS);
		final int len = infos.size();
		for (int i = 0; i < len; i++) {
			PackageInfo info = infos.get(i);
			String[] perms = info.requestedPermissions;
			if(perms != null && perms.length > 0)
				for (String perm : perms) {
					if(INTERNET.equals(perm)){
						TrafficItem item = new TrafficItem();
						//返回-1表示不支持api
						long traffic = TrafficStats.getUidRxBytes(info.applicationInfo.uid);
						if(traffic == TrafficStats.UNSUPPORTED)
							traffic = TrafficStatsAgo.getUidRxBytes(info.applicationInfo.uid);
						if(traffic == 0)
							continue;
						item.traffic = traffic;
						item.icon = info.applicationInfo.loadIcon(pm);
						item.title = info.applicationInfo.loadLabel(pm);
						item.packageName = info.applicationInfo.packageName;
						trafficList.add(item);
					}
				}
		}
		return trafficList;
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		TrafficItem item = mItems.get(position);
		System.out.println("packageName:" + item.packageName);
		//根据包名启动
		Intent i = getPackageManager().getLaunchIntentForPackage(item.packageName);
		startActivity(i);
	}
	
	class TrafficAdapter extends BaseAdapter {

		private List<TrafficItem> mItems;
		public TrafficAdapter (List<TrafficItem> list){
			mItems = list;
		}
		
		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public Object getItem(int position) {
			return mItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder view;
			if(convertView == null){
				convertView = getLayoutInflater().inflate(R.layout.traffic_item, null);
				view = new ViewHolder();
				view.mIconImageView = (ImageView) convertView.findViewById(R.id.traffic_icon);
				view.mTitleTextView = (TextView) convertView.findViewById(R.id.traffic_title);
				view.mTrafficTextView = (TextView) convertView.findViewById(R.id.traffic_value);
				view.mPercentProgressBar = (ProgressBar) convertView.findViewById(R.id.traffic_progress);
				//total/number
				int max = (int)((float) Float.valueOf(formatFlowValue(TrafficStats.getTotalRxBytes() / mItems.size()))); 
				view.mPercentProgressBar.setMax(max);
				convertView.setTag(view);
			} else {
				view = (ViewHolder) convertView.getTag();
			}
			TrafficItem item = mItems.get(position);
			view.mIconImageView.setImageDrawable(item.icon);
			view.mTitleTextView.setText(item.title);
			view.mTrafficTextView.setText(formatFlowStr(item.traffic));
			view.mPercentProgressBar.setProgress((int) ((float) Float.valueOf(formatFlowValue(item.traffic))));
			return convertView;
		}
		
		class ViewHolder {
			ImageView mIconImageView;
			TextView mTitleTextView;
			TextView mTrafficTextView;
			ProgressBar mPercentProgressBar;
		}
	}
	
	/**
	 * 转换
	 * @param flow
	 * @return
	 */
	public String formatFlowStr(long flow){
		DecimalFormat format = new DecimalFormat("0.00");
		String value = "";
		if(flow >= 1024)
			value = format.format((flow * 0.1f) / 1024) + "KB";
		else
			value = format.format((flow * 0.1f) / (1024 * 1024)) + "M";
		
		return value;
	}
	
	/**
	 * 转换
	 * @param flow
	 * @return
	 */
	public float formatFlowValue(long flow){
		DecimalFormat format = new DecimalFormat("0.00");
		return Float.valueOf(format.format((flow * 0.1f) / (1024)));
	}
	
	class TrafficItem {
		private String packageName;
		private long traffic;
		private Drawable icon;
		private CharSequence title;
	}
}
