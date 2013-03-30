package com.irun.sm.ui.demo.ui;

import java.io.File;

import com.irun.sm.ui.demo.services.DownloadProgressListener;
import com.irun.sm.ui.demo.services.FileDownload;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/***
 * @author huangsm
 * @date 2012-11-5
 * @email huangsanm@gmail.com
 * @desc 多线程下载
 * http://www.cnblogs.com/zxl-jay/archive/2011/10/09/2204195.html
 */
public class MultThreadDownloader extends Activity implements OnClickListener {
	
	private final static String FILE_SIZE = "size";
	private final static String ERROR = "error";
	
	private EditText mPathEditText;
	private Button mDownloadButton;
	private TextView mResultTextView;
	private ProgressBar mDownloadProgressBar;
	
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mult_download);
		
		mContext = this;
		setupView();
	}
	
	//初始化控件
	private void setupView(){
		mPathEditText = (EditText) findViewById(R.id.path);
		mDownloadButton = (Button) findViewById(R.id.download);
		mDownloadButton.setOnClickListener(this);
		mResultTextView = (TextView) findViewById(R.id.result);
		mDownloadProgressBar = (ProgressBar) findViewById(R.id.downloadbar);
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.download){
			if(hasSDCard()){
				String uri = mPathEditText.getText().toString();
				download(uri, Environment.getExternalStorageDirectory());
			}else{
				Toast.makeText(mContext, "sdcard不存在！", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	//下载
	private void download(final String uri, final File dir){
		new Thread(new Runnable() {
			@Override
			public void run() {
				FileDownload downloader = new FileDownload(mContext, uri, dir, 3);
				mDownloadProgressBar.setMax(downloader.getFileSize());
				try {
					downloader.download(new DownloadProgressListener() {
						@Override
						public void OnDownloadSize(int downloadSize) {
							Message msg = new Message();
							msg.what = 1;
							msg.getData().putInt(FILE_SIZE, downloadSize);
							mHandler.sendMessage(msg);
						}
					});
				} catch (Exception e) {
					Message msg = new Message();
					msg.what = -1;
					msg.getData().putString(ERROR, e.getMessage());
					mHandler.sendMessage(msg);
				}
			}
		}).start();
	}
	
	//handler
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1 :
					int fileSize = msg.getData().getInt(FILE_SIZE);
					mDownloadProgressBar.setProgress(fileSize);
					int result = (int) (((float)fileSize / mDownloadProgressBar.getMax()) * 100);
					mResultTextView.setText(result + "%");
					if(mDownloadProgressBar.getMax() == fileSize){
						Toast.makeText(mContext, "finish!", Toast.LENGTH_SHORT).show();
					}
					break;
				case 2:
					String error = msg.getData().getString(ERROR);
					Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};
	
	private boolean hasSDCard(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
}