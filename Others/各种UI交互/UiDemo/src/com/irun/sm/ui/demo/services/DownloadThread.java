package com.irun.sm.ui.demo.services;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpConnection;

import android.content.Context;
import android.util.Log;

/***
 * @author huangsm
 * @date 2012-11-5
 * @email huangsanm@gmail.com
 * @desc 线程下载
 */
public class DownloadThread extends Thread {

	private final static String TAG = "DownloadThread";
	
	private RandomAccessFile mSaveFile;
	private URL mDownUrl;
	private int mBlock;
	//开始下载位置
	private int mThreadId = -1;
	private int mStartPosition;
	private int mDownLength;
	private boolean mIsFinish = false;
	private FileDownload mFileDownload;
	private Context mContext;
	
	public DownloadThread(Context context, URL url, RandomAccessFile rand, int block, Integer startPos, int threadid){
		mContext = context;
		mDownUrl = url;
		mSaveFile = rand;
		mBlock = block;
		mStartPosition = startPos;
		mThreadId = threadid;
		mDownLength = mStartPosition - (mBlock * (mThreadId - 1));
		
		Log.i(TAG, "DownLength:" + mDownLength);
	}
	
	@Override
	public void run() {
		try {
			if(mDownLength < mBlock){
				HttpURLConnection conn = (HttpURLConnection) mDownUrl.openConnection();
				conn.setRequestMethod("GET");
				InputStream is = conn.getInputStream();
				int max = mBlock > 1024 ? 1024 : (mBlock > 10 ? 10 : 1);
				byte[] bt = new byte[max];
				int offset = 0;
				Log.i(TAG, "thread:" + mThreadId + ",startPosition:" + mStartPosition);
				while(mDownLength < mBlock && (offset = is.read(bt)) != -1){
					mSaveFile.write(bt, 0, offset);
					mDownLength += offset;
					mFileDownload.update(mThreadId, mBlock * (mThreadId - 1) + mDownLength);
					mFileDownload.saveLogFile();
					mFileDownload.append(offset);
					int spare = mBlock - mDownLength;
					if(spare < max)
						max = spare;
				}
				mSaveFile.close();
				is.close();
				mIsFinish = true;
				interrupt();
			}
		} catch (Exception e) {
			Log.i(TAG, e.getMessage());
		}
	}
	
	public boolean isFinish(){
		return mIsFinish;
	}
	
	public int getDownLenght(){
		return mDownLength;
	}
}
