package com.irun.sm.ui.demo.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.irun.sm.ui.demo.ui.R;

/***
 * @author huangsm
 * @date 2013-1-4
 * @email huangsanm@gmail.com
 * @desc apps列表
 */
public class AppsFragment extends Fragment {

	private Activity mActivity;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
		System.out.println("AppsFragment :: onCreate");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		System.out.println("AppsFragment :: onCreateView...");
		
		View view = inflater.inflate(R.layout.text_animation, container, false);
		Button btn = (Button) view.findViewById(R.id.btn_test);
		btn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
				dialog.setTitle("提示");
				dialog.create().show();
			}
		});
		return view;
	}
}
