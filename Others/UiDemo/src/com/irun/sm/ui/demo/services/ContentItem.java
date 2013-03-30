package com.irun.sm.ui.demo.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.irun.sm.ui.demo.ui.R;
import com.irun.sm.ui.demo.vo.Item;

/***
 * @author huangsm
 * @date 2012-8-29
 * @email huangsanm@gmail.com
 * @desc 内容
 */
public class ContentItem implements ListItems {

	private Item mItem;
	public ContentItem(Item item){
		mItem = item;
	}
	
	@Override
	public int getLayout() {
		return R.layout.content_layout;
	}

	@Override
	public boolean isClickable() {
		return true;
	}

	@Override
	public View getView(Context context, View convertView, LayoutInflater inflater) {
		convertView = inflater.inflate(getLayout(), null);
		ImageView iv = (ImageView) convertView.findViewById(R.id.content_image);
		iv.setImageResource(mItem.getResid());
		TextView tv = (TextView) convertView.findViewById(R.id.content_text);
		tv.setText(mItem.getTitle());
		return convertView;
	}
}
