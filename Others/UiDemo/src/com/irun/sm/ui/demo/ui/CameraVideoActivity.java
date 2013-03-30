package com.irun.sm.ui.demo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.irun.sm.ui.demo.utils.CameraVideo;

/***
 * @author huangsm
 * @date 2012-10-17
 * @email huangsanm@gmail.com
 * @desc 自定义camera
 */
public class CameraVideoActivity extends Activity implements SurfaceHolder.Callback, OnClickListener {
	
	public static final String CAMERA = "camera";
	public static final String VIDEO = "video";
	private static final int MAX_POINTER = 2;
	
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	
	private Button mTokenPictureButton;
	private Button mTokenVideoButton;

	//private CameraManager mCamera;
	//private VideoManager mVideo;
	private CameraVideo mCameraVideo;
	
	private boolean isOpen = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.camera);

		setupView();
		
		/*mCamera = new CameraManager();
		mVideo = new VideoManager();*/
		mCameraVideo = new CameraVideo();
	}
	
	private void setupView(){
		mSurfaceView = (SurfaceView) findViewById(R.id.camera_surface);
		mSurfaceView.setOnTouchListener(mTouchListener);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mTokenPictureButton = (Button) findViewById(R.id.camera_take);
		mTokenPictureButton.setOnClickListener(this);
		mTokenVideoButton = (Button) findViewById(R.id.camera_video);
		mTokenVideoButton.setOnClickListener(this);
	}
	
	
	private OnTouchListener mTouchListener = new SurfaceView.OnTouchListener(){
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int eventPointer = event.getPointerCount();
			if(eventPointer > MAX_POINTER){
				eventPointer = MAX_POINTER;
			}
			if(eventPointer >= 2){
				//锁定canvas
				//Canvas canvas = mSurfaceHolder.lockCanvas();
				//if(canvas != null){
					float x1 = event.getX(0);
					float y1 = event.getY(0);
					float x2 = event.getX(1);
					float y2 = event.getY(1);
					System.out.println(x1 + ":" + y1 + "," + x2 + ":" + y2);
					float x = Math.abs(x2 - x1);
					float y = Math.abs(y2 - y1);
					float zoom = FloatMath.sqrt(x * x + y * y);
					System.out.println("zoom:" + zoom);
				//}
					return true;
			}
			return false;
		}
	};

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mCameraVideo.openCamera(holder);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		mCameraVideo.startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mCameraVideo.release();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.camera_take:
				mCameraVideo.tokenPicture();
				break;
			case R.id.camera_video:
				if(isOpen){
					((Button) v).setText("录像");
					mCameraVideo.stopVideo();
					isOpen = false;
					mTokenPictureButton.setEnabled(true);
				}else{
					mTokenPictureButton.setEnabled(false);
					((Button) v).setText("正在录像...");
					mCameraVideo.startVideo(mSurfaceHolder);
					isOpen = true;
				}
				break;
		}
	}
	
	@Override
	protected void onDestroy() {
		mCameraVideo.release();
		super.onDestroy();
	}
}
