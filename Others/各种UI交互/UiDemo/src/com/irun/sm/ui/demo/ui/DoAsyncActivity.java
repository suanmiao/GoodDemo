package com.irun.sm.ui.demo.ui;

import java.util.concurrent.Callable;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.irun.sm.ui.demo.utils.ActivityUtils;
import com.irun.sm.ui.demo.utils.BaseActivity;
import com.irun.sm.ui.demo.utils.Callback;
import com.irun.sm.ui.demo.utils.IProgressListener;
import com.irun.sm.ui.demo.utils.ProgressCallable;

/***
 * @author huangsm
 * @date 2012-11-12
 * @email huangsanm@gmail.com
 * @desc 进度条
 */
public class DoAsyncActivity extends BaseActivity implements OnClickListener {

	private Context mContext;
	private Button mDoAsyncButton;
	private Button mProgressButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActivityUtils.requestNotTitleBar(this);
		
		setContentView(R.layout.do_async);
		mContext = this;
		
		setupView();
	}
	
	private void setupView(){
		mDoAsyncButton = (Button) findViewById(R.id.do_async);
		mDoAsyncButton.setOnClickListener(this);
		mProgressButton = (Button) findViewById(R.id.do_progress);
		mProgressButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.do_async :
				doAsync(R.string.title_resource, R.string.message_resource, new Callable<String>() {
					@Override
					public String call() throws Exception {
						Thread.sleep(10000);
						return "do Async!";
					}
				}, new Callback<String>() {
					@Override
					public void onCallback(String pCallbackValue) {
						mDoAsyncButton.setText(pCallbackValue);
					}
				});
				/*doAsync(R.string.title_resource, R.string.message_resource, new Callable<String>() {
					@Override
					public String call() throws Exception {
						Thread.sleep(20000);
						return "do Async!";
					}
				}, new Callback<String>() {
					@Override
					public void onCallback(String pCallbackValue) {
						mDoAsyncButton.setText(pCallbackValue);
					}
				}, new Callback<Exception>() {
					@Override
					public void onCallback(Exception pCallbackValue) {
						System.out.println("pCallbackValue...");
					}
				}, false);*/
				break;
			case R.id.do_progress:
				doProgressAsync(R.string.title_resource, R.string.message_resource, new ProgressCallable<String>() {
					@Override
					public String call(IProgressListener iProgressListener) {
						return "this is ProgressCallable!";
					}
				}, new Callback<String>() {
					@Override
					public void onCallback(String pCallbackValue) {
						mProgressButton.setText(pCallbackValue);
					}
				});
				break;
		}
	}
	
}
