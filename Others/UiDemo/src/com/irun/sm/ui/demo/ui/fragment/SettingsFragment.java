package com.irun.sm.ui.demo.ui.fragment;

import java.util.List;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.irun.sm.ui.demo.ui.R;

/***
 * @author huangsm
 * @date 2013-1-4
 * @email huangsanm@gmail.com
 * @desc 设置选项
 */
public class SettingsFragment extends Fragment {

	private ArrayAdapter<String> mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final Resources res = getResources();
		String[] mTitles = res.getStringArray(R.array.setting_items);
		
		mAdapter = new ArrayAdapter<String>(
				getActivity(), 
				R.layout.fragment_setting_item, 
				R.id.item, 
				mTitles);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.part_listview, container, false);
		ListView listView = (ListView) view.findViewById(R.id.part_list);
		listView.setAdapter(mAdapter);
		return view;
	}
}
