package com.irun.sm.ui.demo.view;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/***
 * @author huangsm
 * @date 2012-10-18
 * @email huangsanm@gmail.com
 * @desc Camera view
 */
public class CameraSurface extends SurfaceView implements AutoFocusCallback,
		SurfaceHolder.Callback {

	private Camera mCamera;
	private Context mContext;
	private SurfaceHolder mSurfaceHolder;
	//private final static int mPictureWidth = 1600;
	//private final static int mPictureHeight = 1200;

	public CameraSurface(Context context) {
		super(context);
		init(context);
	}

	public CameraSurface(Context context, AttributeSet attr) {
		super(context, attr);

		init(context);
	}

	public CameraSurface(Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);

		init(context);
	}

	private void init(Context context) {
		mContext = context;
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	// 预览
	public void startPreview() {
		mCamera.startPreview();
	}
	
	//拍照
	public void tokenPicture() {
		mCamera.autoFocus(this);
	}

	// 自动对焦
	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		setPreviewSize();
		mCamera.takePicture(mShutterCallback, mPictureCallbackRaw, mPictureCallbackJpeg);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			if(mCamera == null){
				mCamera = Camera.open();
				mCamera.setPreviewDisplay(mSurfaceHolder);
				Camera.Parameters parameters = mCamera.getParameters();
				parameters.setPreviewSize(parameters.getPreviewSize().width, parameters.getPreviewSize().height);
				mCamera.setParameters(parameters);
				mCamera.setDisplayOrientation(90);
				mCamera.startPreview();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		mCamera.startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mCamera != null) {
			mCamera.release();
		}
	}
	
	//设置预览大小
	private void setPreviewSize(){
		Camera.Parameters parameters = mCamera.getParameters();
		parameters.setPictureFormat(parameters.getPictureFormat());
		parameters.setJpegQuality(parameters.getJpegQuality());
		parameters.setZoom(parameters.getZoom());
		parameters.setPictureSize(parameters.getPictureSize().width, parameters.getPictureSize().height);
		parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
		parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
		mCamera.setParameters(parameters);
	}

	private ShutterCallback mShutterCallback = new ShutterCallback() {
		@Override
		public void onShutter() {
			
		}
	};

	private PictureCallback mPictureCallbackRaw = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			
		}
	};

	//保存照片到
	private PictureCallback mPictureCallbackJpeg = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			try {
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				File file = new File(getPictureName());
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
				bos.flush();
				bos.close();
				mCamera.startPreview();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private String getPictureName(){
		return Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + System.currentTimeMillis() + ".jpg";
	}

}
