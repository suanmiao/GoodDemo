package com.irun.sm.ui.demo.ui;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.irun.sm.ui.demo.view.DownloadImageView;

/***
 * @author huangsm
 * @date 2012-9-6
 * @email huangsanm@gmail.com
 * @desc 下载图片
 */
public class ImageActivity extends Activity {

	private GridView mGridView;
	private String[] mArrays;
	
	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_layout);
		mContext = this;
		
		mArrays = mContext.getResources().getStringArray(R.array.url);
		
		mGridView = (GridView) findViewById(R.id.gridView);
		ImageItemAdapter itemAdapter = new ImageItemAdapter();
		mGridView.setAdapter(itemAdapter);
	}
	
	
	class ImageItemAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mArrays.length;
		}

		@Override
		public Object getItem(int position) {
			return mArrays[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = getLayoutInflater().inflate(R.layout.gridview_item, null);
			DownloadImageView imageView = (DownloadImageView) convertView.findViewById(R.id.imageview);
			imageView.loadImage(mArrays[position], R.drawable.ic_launcher);
			//imageView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_in));
			//imageView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_out));
			//new TestImage(imageView).execute(mArrays[position]);
			return convertView;
		}
	}
	
	/**
	 * 下载图片
	 * @author Administrator
	 */
	class TestImage extends AsyncTask<String, Void, Bitmap> {
		
		private ImageView mImageView;
		public TestImage(ImageView imageView){
			mImageView = imageView;
		}
		
		@Override
		protected void onPreExecute() {
			
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			String path = params[0];
			Bitmap bitmap = null;
			try {
				URL url = new URL(path);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(5000);
				connection.setRequestMethod("GET");
				connection.setDoInput(true);
				if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
					InputStream is = connection.getInputStream();
					bitmap = BitmapFactory.decodeStream(is);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			mImageView.setImageBitmap(result);
		}
	}
}
