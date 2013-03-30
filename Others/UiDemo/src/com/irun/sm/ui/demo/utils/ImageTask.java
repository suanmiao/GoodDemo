package com.irun.sm.ui.demo.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/***
 * @author huangsm
 * @date 2013-1-15
 * @email huangsanm@gmail.com
 * @desc 图片下载
 */
public class ImageTask extends AsyncTask<String, Void, Bitmap> {

	private final static int TIMEOUT = 2500;
	private DownloadImageListener mImageListener;
	
	public ImageTask(DownloadImageListener listener){
		mImageListener = listener;
	}
	
	@Override
	protected Bitmap doInBackground(String... params) {
		if(params == null || params.length <= 0){
			cancel(true);
		}
		Bitmap result = null;
		String path = params[0];
		try {
			//建立连接
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(TIMEOUT);
			conn.setReadTimeout(3000);
			conn.connect();
			//获取文件
			InputStream is = conn.getInputStream();
			result = BitmapFactory.decodeStream(is);
		} catch (Exception e) {
			throw new RuntimeException("Resolve InputStream Error!", e);
		}
		return result;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		if(result != null){
			mImageListener.downloaded(result);
		}
	}
	
	/**
	 * 下载完成回调
	 * @author huangsm
	 */
	public interface DownloadImageListener {
		void downloaded(Bitmap bitmap);
	}
}
