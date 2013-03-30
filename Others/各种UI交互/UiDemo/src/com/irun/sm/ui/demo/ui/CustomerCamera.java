package com.irun.sm.ui.demo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.irun.sm.ui.demo.view.CameraSurface;

/***
 * @author huangsm
 * @date 2012-10-17
 * @email huangsanm@gmail.com
 * @desc 自定义camera
 */
public class CustomerCamera extends Activity {

	private CameraSurface mSurfaceView;
	private Button mTokenPictureButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.customer_camera_layout);

		mSurfaceView = (CameraSurface) findViewById(R.id.camera_surface);
		mTokenPictureButton = (Button) findViewById(R.id.camera_take);
		mTokenPictureButton.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				//抓取图片
				mSurfaceView.tokenPicture();
			}
		});
	}

}
