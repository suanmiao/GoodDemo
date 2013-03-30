package com.irun.sm.ui.demo.services;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

/***
 * @author huangsm
 * @date 2012-11-5
 * @email huangsanm@gmail.com
 * @desc 多线程断点下载
 */
public class FileDownload {
	
	private static final String TAG = "FileDownload";

	private Context mContext;
	private FileService mFileService;
	//已下载文件大小
	private int mDownloadSize;
	//原始文件大小
	private int mFileSize;
	//线程数
	private DownloadThread[] mThreads;
	//下载路径
	private URL mUrl;
	//本地保存文件
	private File mSaveFile;
	//下载记录文件
	private File mLogFile;
	//缓存各个线程最后的下载位置
	private Map<Integer, Integer> mData = new ConcurrentHashMap<Integer, Integer>();
	//每条线程下载的大小
	private int mBlock;
	//下载路径
	private String mDownloadUrl;
	
	public FileDownload(Context context, File path){
		mContext = context;
		
	}
	
	public FileDownload(Context context, String downloadUrl, File fileSaveDir, int threadNum){
		mContext = context;
		mDownloadUrl = downloadUrl;
		mFileService = new FileService(mContext);
		mThreads = new DownloadThread[threadNum];
		try {
			//创建目录
			if(!fileSaveDir.exists()){
				fileSaveDir.mkdirs();
			}
			mUrl = new URL(mDownloadUrl);
			HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();
			connection.setConnectTimeout(6 * 1000);
			connection.setRequestMethod("GET");
			connection.connect();
			Log.i(TAG, "" + connection.getResponseCode());
			if(connection.getResponseCode() == 200){
				//文件大小
				mFileSize = connection.getContentLength();
				Log.i(TAG, "mFileSize:" + mFileSize);
				if(mFileSize <= 0){
					throw new RuntimeException("无法获取文件大小");
				}
				String fileName = getFileName(connection);
				Log.i(TAG, "FileName:" + fileName);
				mSaveFile = new File(fileSaveDir, fileName);
				Map<Integer, Integer> logData = mFileService.getData(mDownloadUrl);
				if(!logData.isEmpty()){
					mData.putAll(logData);
					mBlock = mFileSize / (mThreads.length + 1);
					if(mData.size() == mThreads.length){
						for (int i = 0; i < mThreads.length; i++) {
							mDownloadSize += mData.get(i + 1) - (mBlock * i);
						}
						Log.i(TAG, "DownloadSize:" + mDownloadSize);
					}
				}
			}else{
				throw new RuntimeException("服务器无响应");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//获取文件名称
	private String getFileName(HttpURLConnection conn){
		String fileName = mUrl.toString().substring(mUrl.toString().lastIndexOf('/') + 1);
		if(TextUtils.isEmpty(fileName)){
			for (int i = 0; ; i++) {
				String mine = conn.getHeaderField(i);
				System.out.println("mine:" + mine);
				if(mine == null)
					break;
				if("content-disposition".equals(conn.getHeaderFieldKey(i).toLowerCase())){
					Matcher m = Pattern.compile(".*filename=(.*)").matcher(mine);
					if(m.find()){
						return m.group(1);
					}
				}
			}
			fileName = UUID.randomUUID() + ".tmp";
		}
		return fileName;
	}
	
	//开始下载文件
	public int download(DownloadProgressListener listener){
		if(mData.size() == mThreads.length){
			mData.clear();
			for (int i = 0; i < mThreads.length; i++) {
				mData.put(i + 1, mBlock * i);
			}
		}
		
		try {
			for (int i = 0; i < mThreads.length; i++) {
				int downloadLength = mData.get(i + 1) - (mBlock * i);
				if(downloadLength < mBlock && mData.get(i + 1) < mFileSize){
					RandomAccessFile randomAccessFile = new RandomAccessFile(mSaveFile, "rw");
					if(mFileSize > 0){
						randomAccessFile.setLength(mFileSize);
					}
					randomAccessFile.seek(mData.get(i + 1));
					mThreads[i] = new DownloadThread(mContext, mUrl, randomAccessFile, mBlock, mData.get(i + 1), i + 1);
					mThreads[i].setPriority(7);
					mThreads[i].start();
				}else{
					mThreads[i] = null;
				}
			}
			mFileService.save(mDownloadUrl, mData);
			boolean isFinish = true;
			while(isFinish){
				Thread.sleep(900);
				isFinish = false;
				for (int i = 0; i < mThreads.length; i++) {
					if(mThreads[i] != null && mThreads[i].isFinish()){
						isFinish = false;
						if(mThreads[i].getDownLenght() == -1){
							RandomAccessFile randomAccessFile = new RandomAccessFile(mSaveFile, "rw");
							randomAccessFile.seek(mData.get(i + 1));
							mThreads[i] = new DownloadThread(mContext, mUrl, randomAccessFile, mBlock, mData.get(i + 1), i + 1);
							mThreads[i].setPriority(7);
							mThreads[i].start();
						}
					}
				}
				if(listener != null)
					listener.OnDownloadSize(mDownloadSize);
			}
			mFileService.delete(mDownloadUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mDownloadSize;
	}
	
	public static Map<String, String> getHttpResponseHeader(HttpURLConnection http){
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; ; i++) {
			String mine = http.getHeaderField(i);
			if(mine == null)
				break;
			map.put(http.getHeaderFieldKey(i), mine);
		}
		return map;
	}
	
	//线程数
	public int getThreadSize(){
		return mThreads.length;
	}
	
	//文件
	public int getFileSize(){
		return mFileSize;
	}
	
	//累计已下载多少
	protected synchronized void append(int size){
		mDownloadSize += size;
	}
	
	//更新指定线程最后下载的位置
	protected void update(int thread, int position){
		mData.put(thread, position);
	}
	//保存记录文件
	protected synchronized void saveLogFile(){
		mFileService.update(mDownloadUrl, mData);
	}
	
	
}
