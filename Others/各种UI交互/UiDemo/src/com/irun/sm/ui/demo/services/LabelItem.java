package com.irun.sm.ui.demo.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.irun.sm.ui.demo.ui.R;

/***
 * @author huangsm
 * @date 2012-8-29
 * @email huangsanm@gmail.com
 * @desc 标签
 */
public class LabelItem implements ListItems {

	private String mLabel;
	public LabelItem(String label){
		mLabel = label;
	}
	
	@Override
	public int getLayout() {
		return R.layout.label_layout;
	}

	@Override
	public boolean isClickable() {
		return false;
	}

	@Override
	public View getView(Context context, View convertView, LayoutInflater inflater) {
		convertView = inflater.inflate(getLayout(), null);
		TextView title = (TextView) convertView;
		title.setText(mLabel);
		return convertView;
	}

}
