package com.irun.sm.ui.demo.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/***
 * @author huangsm
 * @date 2012-8-29
 * @email huangsanm@gmail.com
 * @desc 接口
 */
public interface ListItems {

	public int getLayout();
	
	public boolean isClickable();
	
	public View getView(Context context, View convertView, LayoutInflater inflater);
	
}
