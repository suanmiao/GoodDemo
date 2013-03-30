package com.irun.sm.ui.demo.utils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.text.TextUtils;

/***
 * @author huangsm
 * @date 2012-6-26
 * @email huangsanm@gmail.com
 * @desc 公用类
 */
public class Globals {

	private final static String[] MIMETYPE = {"image/jpeg", "image/jpg", "image/x-ms-bmp"};
	
	private final static float ROUND = 20.0f;
	/**
	 * 是否图片类型
	 * @param mimeType
	 * @return
	 */
	public static boolean isImageType(String mimeType){
		boolean result = false;
		for (int i = 0; i < MIMETYPE.length; i++) {
			if(MIMETYPE[i].toLowerCase().equals(mimeType.toLowerCase())){
				result = true;
				break;
			}
		}
		return result;
	}
	
	/***
	 * 根据路径图片转换为bitmap
	 * @param path
	 * @param w 目标宽度
	 * @param h 目标高度
	 * @return
	 */
	public static Bitmap convertToBitmap(String path, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		// 返回为空
		BitmapFactory.decodeFile(path, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (width > w || height > h) {
			// 缩放
			scaleWidth = ((float) width) / w;
			scaleHeight = ((float) height) / h;
		}
		opts.inJustDecodeBounds = false;
		float scale = Math.max(scaleWidth, scaleHeight);
		opts.inSampleSize = (int)scale;
		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
		return Bitmap.createScaledBitmap(weak.get(), w, h, true);
	}
	
	/***
	 * 传入对象返回xml文件，xml文件的节点名称为obj属性名
	 * @param params
	 * @return
	 */
	public static String getXmlContent(Object params){
		StringBuffer xml = new StringBuffer();
		if(params != null){
			try {
				Class<?> cal = params.getClass();
				for (int i = 0; i < cal.getDeclaredFields().length; i++) {
					Field field = cal.getDeclaredFields()[i];
					String fname = field.getName();
					String strLitter = fname.substring(0, 1).toUpperCase();
					
					String getName = "get" + strLitter + fname.substring(1);
					String setName = "set" + strLitter + fname.substring(1);
					
					Method getmethod = cal.getMethod(getName, new Class[]{});
					cal.getMethod(setName, new Class[]{field.getType()});
					
					String value = getmethod.invoke(params, new Object[]{}).toString();
					if(!TextUtils.isEmpty(value)){
						xml
						.append("<").append(fname).append(">")
						.append(value)
						.append("<").append("/").append(fname).append(">");
					}
					
				}
				System.out.println("xml:" + xml.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return xml.toString();
	}
	
	/**
	 * 绘制圆角
	 * @param data
	 * @return
	 */
	public Bitmap getPhotos(Bitmap bitmap, float round){
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		canvas.drawARGB(0, 0, 0, 0);
		final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setAntiAlias(true);
		paint.setColor(0xff424242);
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectf = new RectF(rect);
		canvas.drawRoundRect(rectf, round, round, paint);
		paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
	
	/**
	 * 是否存在sd卡
	 * @return
	 */
	public static boolean hasSDCard(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
}
