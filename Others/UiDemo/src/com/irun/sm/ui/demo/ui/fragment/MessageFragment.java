package com.irun.sm.ui.demo.ui.fragment;

import com.irun.sm.ui.demo.ui.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/***
 * @author huangsm
 * @date 2013-1-4
 * @email huangsanm@gmail.com
 * @desc 所有信息列表
 */
public class MessageFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		System.out.println("Fragment :: onCreate");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("MessageFragment :: onCreateView");
		return inflater.inflate(R.layout.text_animation, container, false);
	}
}
