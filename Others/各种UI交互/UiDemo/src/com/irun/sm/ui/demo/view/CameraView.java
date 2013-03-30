package com.irun.sm.ui.demo.view;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/***
 * @author huangsm
 * @date 2012-7-10
 * @email huangsanm@gmail.com
 * @desc camera对焦
 */
public class CameraView extends SurfaceView implements Callback, PictureCallback {
	
	private SurfaceHolder mHolder;
	private Camera mCamera;
	
	public CameraView(Context context) {
		super(context);
		init(context);
	}
	
	public CameraView(Context context, AttributeSet attr){
		super(context, attr);
		init(context);
	}
	
	private void init(Context context){
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mCamera = Camera.open();
			mCamera.setPreviewDisplay(mHolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Parameters parameter = mCamera.getParameters();
		parameter.setPictureFormat(PixelFormat.JPEG);
		//parameter.setPreviewSize(width, height);
		mCamera.setParameters(parameter);
		mCamera.startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mCamera.setPreviewCallback(null);
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}
	
	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		String path = Environment.getExternalStorageDirectory() + "/" + UUID.randomUUID() + ".jpg";
		
		try {
			FileOutputStream os = new FileOutputStream(path);
			os.write(data);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
