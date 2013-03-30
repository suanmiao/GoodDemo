package com.irun.sm.ui.demo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

/***
 * @author huangsm
 * @email huangsanm@gmail.com
 * @date 2011-12-9
 * @description 设置alpha值
 **/
public class AlphaDemo extends Activity{

	private ImageView mImageView;
	private TextView mTextView;
	private boolean isRun = false;
	private int alpha = 255;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alpha);
		isRun = true;
		
		mImageView = (ImageView) findViewById(R.id.imageView);
		mImageView.setAlpha(alpha);
		
		mTextView = (TextView) findViewById(R.id.textView);
		new Thread(new Runnable() {
			public void run() {
				while(isRun){
					try {
						Thread.sleep(200);
						setAlpha();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	private void setAlpha(){
		if(alpha >= 0){
			alpha -= 10;
		}else{
			alpha = 255;
		}
		mHandler.sendMessage(mHandler.obtainMessage());
	}
	
	Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			mImageView.setAlpha(alpha);
			mTextView.setText("当前alpha值：" + alpha);
			mImageView.invalidate();
		}
	};
}
