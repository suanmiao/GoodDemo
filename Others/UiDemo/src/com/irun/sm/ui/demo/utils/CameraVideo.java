package com.irun.sm.ui.demo.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.SurfaceHolder;

/***
 * @author huangsm
 * @date 2012-10-25
 * @email huangsanm@gmail.com
 * @desc 相机和摄像机
 */
public class CameraVideo implements AutoFocusCallback {

	private Camera mCamera;
	private MediaRecorder mMediaRecorder;
	
	public CameraVideo() {
		mMediaRecorder = new MediaRecorder();
	}

	public void openCamera(SurfaceHolder holder) {
		try {
			if (mCamera == null) {
				mCamera = Camera.open();
				mCamera.setPreviewDisplay(holder);
				Camera.Parameters parameters = mCamera.getParameters();
				parameters.setPreviewSize(parameters.getPreviewSize().width,
						parameters.getPreviewSize().height);
				mCamera.setParameters(parameters);
				mCamera.setDisplayOrientation(90);
				mCamera.startPreview();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 预览
	public void startPreview() {
		mCamera.startPreview();
	}

	// 拍照
	public void tokenPicture() {
		mCamera.autoFocus(this);
		setPreviewSize();
		mCamera.takePicture(mShutterCallback, mPictureCallbackRaw, mPictureCallbackJpeg);
	}

	// 自动对焦
	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		//TODO:
	}
	
	public void release() {
		if (mMediaRecorder != null) {
			//mMediaRecorder.stop();
			mMediaRecorder.release();
			mMediaRecorder = null;
		}
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}

	// 设置预览大小
	private void setPreviewSize() {
		Camera.Parameters parameters = mCamera.getParameters();
		parameters.setPictureFormat(PixelFormat.JPEG);
		parameters.setJpegQuality(parameters.getJpegQuality());
		parameters.setZoom(parameters.getZoom());
		parameters.setPictureSize(1280, 720);
		parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
		parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
		mCamera.setParameters(parameters);
	}
	
	public void startVideo(SurfaceHolder holder) {
		if (mCamera != null) {
			mCamera.autoFocus(this);
			mCamera.unlock();
			mMediaRecorder.setCamera(mCamera);
			mMediaRecorder.setPreviewDisplay(holder.getSurface());
			mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
			mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
			mMediaRecorder.setVideoSize(800, 480);
			mMediaRecorder.setVideoFrameRate(20);
			mMediaRecorder.setOutputFile(getFileName(".3gp"));
			try {
				mMediaRecorder.prepare();
				mMediaRecorder.start();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stopVideo() {
		try {
			if (mMediaRecorder != null) {
				mMediaRecorder.stop();
				mMediaRecorder.reset();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	// 保存照片到
	private PictureCallback mPictureCallbackJpeg = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			try {
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
						data.length);
				File file = new File(getFileName(".jpeg"));
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(file));
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
				bos.flush();
				bos.close();
				mCamera.startPreview();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private String getFileName(String endWith) {
		return Environment.getExternalStorageDirectory().getPath()
				+ "/DCIM/Camera/" + System.currentTimeMillis() + endWith;
	}

}
