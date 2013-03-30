package com.irun.sm.ui.demo.ui;

import java.util.ArrayList;
import java.util.List;

import com.irun.sm.ui.demo.view.DragGridView;
import com.irun.sm.ui.demo.view.DragGridView.DropViewListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

/***
 * @author huangsm
 * @date 2012-7-20
 * @email huangsanm@gmail.com
 * @desc 拖拽的activity
 */
public class DragGridViewActivity extends Activity implements DropViewListener {

	private Context mContext;
	private DragGridView mDragGrid;
	private DragGridAdapter mDragGridAdapter;
	private List<Integer> mThumbs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drag_gridview);
		mContext = this;
		
		mThumbs = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			if(i % 2 == 0)
				mThumbs.add(R.drawable.ic_launcher_biz);
			if(i % 3 == 0)
				mThumbs.add(R.drawable.ic_launcher);
		}
		
		mDragGridAdapter = new DragGridAdapter(mThumbs);
		mDragGrid = (DragGridView) findViewById(R.id.drag_gridview);
		mDragGrid.setAdapter(mDragGridAdapter);
		mDragGrid.setDropViewListener(this);
	}
	
	class DragGridAdapter extends BaseAdapter {

		private List<Integer> list;
		public DragGridAdapter(List<Integer> lt){
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
			View view = LayoutInflater.from(mContext).inflate(R.layout.gridview_item, null);
			ImageView iv = (ImageView) view.findViewById(R.id.imageview);
			iv.setImageResource(list.get(position));
			return view;
		}
		
		private void insert(int position, Integer items){
			list.remove(items);
			list.add(position, items);
		}
	}

	@Override
	public void drog(int from, int to) {
		Toast.makeText(mContext, from + ":" + to, Toast.LENGTH_LONG).show();
		mDragGridAdapter.insert(to, (Integer)mDragGridAdapter.getItem(from));
		mDragGridAdapter.notifyDataSetChanged();
	}
}
