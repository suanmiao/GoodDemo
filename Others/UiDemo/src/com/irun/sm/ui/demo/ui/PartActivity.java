package com.irun.sm.ui.demo.ui;

import java.util.ArrayList;
import java.util.List;

import com.irun.sm.ui.demo.services.ContentItem;
import com.irun.sm.ui.demo.services.LabelItem;
import com.irun.sm.ui.demo.services.ListItems;
import com.irun.sm.ui.demo.vo.Item;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

/***
 * @author huangsm
 * @date 2012-8-28
 * @email huangsanm@gmail.com
 * @desc 显示一栏一栏的listview
 */
public class PartActivity extends Activity {

	private ListView mListView;
	private List<ListItems> mListItems;
	
	private Context mContext;
	private LayoutInflater mInflater;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.part_listview);
		mListView = (ListView) findViewById(R.id.part_list);
		mContext = this;
		mInflater = LayoutInflater.from(mContext);
		mListItems =  new ArrayList<ListItems>();
		
		//初始化数据
		LabelItem label1 = new LabelItem("Label");
		mListItems.add(label1);
		
		Item item1 = new Item();
		item1.setResid(R.drawable.ic_launcher);
		item1.setTitle(getString(R.string.app_name));
		ContentItem content1 = new ContentItem(item1);
		mListItems.add(content1);
		
		for (int i = 0; i < 3; i++) {
			LabelItem label = new LabelItem("类别" + (i + 1));
			mListItems.add(label);
			
			for (int j = 0; j < 3; j++) {
				Item item = new Item();
				item.setResid(R.drawable.ic_launcher_biz);
				item.setTitle("Content" + (i + 1));
				ContentItem content = new ContentItem(item);
				mListItems.add(content);
			}
		}
		
		//设置adapter
		PartAdapter adapter = new PartAdapter();
		mListView.setAdapter(adapter);
	}
	
	class PartAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mListItems.size();
		}

		@Override
		public Object getItem(int position) {
			return mListItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public boolean isEnabled(int position) {
			return mListItems.get(position).isClickable();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return mListItems.get(position).getView(mContext, convertView, mInflater);
		}
	}
}
