package com.irun.sm.ui.demo.view;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.irun.sm.ui.demo.utils.Globals;
import com.irun.sm.ui.demo.utils.ImageTask;

/***
 * @author huangsm
 * @date 2013-1-15
 * @email huangsanm@gmail.com
 * @desc 图片下载 1：第一次下载图片缓存在sd卡，第二次直接从sd卡上获取照片 2：开始时显示默认图片
 */
public class DownloadImageView extends ImageView {


	public DownloadImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DownloadImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 加载图片
	 * 
	 * @param url
	 *            网络地址
	 * @param resID
	 *            默认图片
	 * @param isBackground
	 *            是否是背景,默认为src
	 */
	public void loadImage(String url, int resID) {
		loadImage(url, resID, 0, 0, false);
	}

	/**
	 * 加载图片
	 * 
	 * @param url
	 *            网络地址
	 * @param resID
	 *            默认图片
	 * @param width
	 *            缩放的值
	 * @param height
	 * @param isBackground
	 *            是否是背景,默认为src
	 */
	private void loadImage(String url, final int resID, final int width, final int height,
			final boolean isBackground) {
		//显示默认图片
		setImage(isBackground, null, resID);
		if (!TextUtils.isEmpty(url)) {
			// 文件名称
			String fname = url.substring(url.lastIndexOf("/") + 1);
			// 文件缓存目录
			final String path = Environment.getExternalStorageDirectory()
					+ File.separator.concat("uidemo").concat(File.separator);
			
			final String filePath = path.concat(fname);
			boolean exists = true;
			// 缓存目录已经存在文件,直接返回
			if (Globals.hasSDCard()) {
				//建立文件夹
				File file = new File(path);
				if(!file.exists()){
					file.mkdirs();
				}
				file = new File(filePath);
				if (file.exists()) {
					Bitmap source = null;
					// width||height缩放
					if (width > 0 || height > 0) {
						source = Globals.convertToBitmap(filePath, width,
								height);
					} else {
						source = BitmapFactory.decodeFile(filePath);
					}
					setImage(isBackground, source, resID);
					exists = false;
				}
			}
			// 执行下载
			if(exists){
				new ImageTask(
						new ImageTask.DownloadImageListener() {
							@Override
							public void downloaded(Bitmap bitmap) {
								// 保存文件
								if (Globals.hasSDCard()) {
									File f = new File(filePath);
									FileOutputStream fos = null;
									try {
										fos = new FileOutputStream(f);
										bitmap.compress(CompressFormat.JPEG, 80, fos);
										fos.close();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								Bitmap source = null;
								// width||height缩放
								if (width > 0 || height > 0) {
									source = Globals.convertToBitmap(filePath, width,
											height);
								} else {
									source = bitmap;
								}
								setImage(isBackground, source, resID);
							}
						}).execute(url);
			}
		}
	}
	
	public void setImage(boolean isBackground, Bitmap bitmap, int resid) {
		if (bitmap == null) {
			bitmap = BitmapFactory.decodeResource(getResources(), resid);
		}
		if (isBackground) {
			Drawable d = new BitmapDrawable(bitmap);
			setBackgroundDrawable(d);
		} else {
			setImageBitmap(bitmap);
		}
	}
}
