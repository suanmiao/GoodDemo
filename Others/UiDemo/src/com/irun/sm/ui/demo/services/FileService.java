package com.irun.sm.ui.demo.services;

import java.util.HashMap;
import java.util.Map;

import com.irun.sm.ui.demo.database.DBOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/***
 * @author huangsm
 * @date 2012-11-5
 * @email huangsanm@gmail.com
 * @desc 操作数据库
 */
public class FileService {

	private DBOpenHelper mOpenHelper;
	
	public FileService(Context context){
		mOpenHelper = new DBOpenHelper(context);
	}
	
	//实时更新线程最后的下载位置
	public void update(String downloadPath, Map<Integer, Integer> map){
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		try {
			db.beginTransaction();
			for (Map.Entry<Integer, Integer> key : map.entrySet()) {
				db.execSQL("update filedown set position=? where downpath=? and threadid=?", 
						new Object[]{key.getValue(), downloadPath, key.getKey()});
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		db.close();
	}
	
	//获取线程最后现在位置
	public Map<Integer, Integer> getData(String url){
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select threadid, position from filedown where downpath=?", new String[]{url});
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		while (cursor.moveToNext()) {
			map.put(cursor.getInt(0), cursor.getInt(1));
		}
		cursor.close();
		db.close();
		return map;
	}
	
	//保存下载线程初始位置
	public void save(String downloadUrl, Map<Integer, Integer> map){
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		try {
			db.beginTransaction();
			for (Map.Entry<Integer, Integer> key : map.entrySet()) {
				db.execSQL("insert into filedown(downpath, threadid, position) values(?, ?, ?)", 
						new Object[]{downloadUrl, key.getKey(), key.getValue()});
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		db.close();
	}
	
	//清楚缓存
	public void delete(String url){
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		db.delete("filedown", "downpath=?", new String[]{url});
		db.close();
	}
}
