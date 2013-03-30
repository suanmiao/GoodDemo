package com.irun.sm.ui.demo.ui;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.irun.sm.ui.demo.view.DragListView;
import com.irun.sm.ui.demo.view.DragListView.DropViewListener;

/***
 * @author huangsm
 * @date 2012-7-11
 * @email huangsanm@gmail.com
 * @desc 可拖动的listview
 * http://www.cnblogs.com/qianxudetianxia/archive/2011/06/12/2068761.html
 * 
 */
public class DragListViewActivity extends Activity implements DropViewListener {

	private /*TouchInterceptor*/DragListView mListView;
	
	private Context mContext;
	private List<String> arrays;
	private ListAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drag_listview);
		
		arrays = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			arrays.add("Item " + i);
		}
		
		mContext = this;
		
		mAdapter = new ListAdapter(arrays);
		
		mListView = (DragListView) findViewById(R.id.listview);
		mListView.setAdapter(mAdapter);
		mListView.setDropViewListener(this);
		//mListView.setDragListener(this);
		//mListView.setDropListener(this);
		//mListView.setRemoveListener(this);
	}
	
	class ListAdapter extends BaseAdapter {

		private List<String> list;
		public ListAdapter(List<String> lt){
			list = lt;
		}
		
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder ;
			if(convertView == null){
				convertView = (View) LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
				holder = new ViewHolder();
				holder.mTextView = (TextView) convertView.findViewById(R.id.item_label);
				holder.mImageView = (ImageView) convertView.findViewById(R.id.item_image);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.mTextView.setText(list.get(position));
			holder.mImageView.setImageResource(R.drawable.part);
			return convertView;
		}
		
		private void insert(int position, String items){
			list.remove(items);
			list.add(position, items);
		}
		
		class ViewHolder {
			TextView mTextView;
			ImageView mImageView;
		}
	}

	/*@Override
	public void drop(int from, int to) {
		mAdapter.insert(to, mAdapter.getItem(from).toString());
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void drag(int from, int to) {
		
	}

	@Override
	public void remove(int which) {
		arrays.remove(which);
	}*/

	@Override
	public void drop(int from, int to) {
		mAdapter.insert(to, mAdapter.getItem(from).toString());
		mAdapter.notifyDataSetChanged();
	}
}
