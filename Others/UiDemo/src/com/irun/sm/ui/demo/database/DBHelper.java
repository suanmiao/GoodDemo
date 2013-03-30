package com.irun.sm.ui.demo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/***
 * @author huangsm
 * @date 2012-7-26
 * @email huangsanm@gmail.com
 * @desc 数据库操作类
 */
public class DBHelper {

	private final static String TAG = "DBHelper";
	private final static String DATABASE_NAME = "scan.db";
	
	public final static String TABLE_NAME_FILES = "files";
	public final static String TABLE_ID = "_id";
	public final static String TABLE_NAME = "name";
	public final static String TABLE_PATH = "path";
	
	public final static String TABLE_FILES_DATE = "date";
	public final static String TABLE_FILES_TAG = "tag";
	public final static String TABLE_FILES_NUM = "num";
	public final static String TABLE_FILES_THUMB = "thumb";
	public final static String TABLE_FILES_COVER = "cover";
	public final static String TABLE_FILES_PASSWORD = "password";
	
	public final static String TABLE_FILEITEM_ORDERS = "orders";
	public final static String TABLE_FILEITEM_FILES = "files";
	public final static String TABLE_FILEITEM_TAG = "tag";
	
	public final static String TABLE_NAME_TAG = "tags";
	public final static String TABLE_NAME_FILEITEM = "fileitem";
	private final static int DATABASE_VERSION = 1;
	
	/***
	 * 构建单实例
	 */
	private static DatabaseHelper mDatabase;
	public DatabaseHelper open(Context context){
		if(mDatabase != null)
			return mDatabase;
		mDatabase = new DatabaseHelper(context);
		return mDatabase;
	}
	
	public void close(){
		if(mDatabase != null)
			mDatabase.close();
	}
	
	/**
	 * 操作数据库
	 * @return
	 * DBHelper.getInstance()
	 */
	public SQLiteDatabase get(){
		return mDatabase.getWritableDatabase();
	}
	
	private class DatabaseHelper extends SQLiteOpenHelper {
		
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			//创建表
			String prefix = "create table ";
			StringBuffer files = new StringBuffer(prefix);
			files.append(TABLE_NAME_FILES).append("(")
				.append(TABLE_ID + " integer primary key autoincrement,")
				.append(TABLE_NAME + " varchar,")
				.append(TABLE_FILES_DATE + " varchar,")
				.append(TABLE_PATH + " varchar,")
				.append(TABLE_FILES_TAG + " int,")
				.append(TABLE_FILES_NUM + " integer,")
				.append(TABLE_FILES_THUMB + " varchar,")
				.append(TABLE_FILES_COVER + " varchar,")
				.append(TABLE_FILES_PASSWORD + " varchar")
				.append(");");
			Log.i(TAG, files.toString());
			db.execSQL(files.toString());
			
			StringBuffer tag = new StringBuffer(prefix);
			tag.append(TABLE_NAME_TAG).append("(")
				.append(TABLE_ID + " integer primary key autoincrement,")
				.append(TABLE_NAME + " varchar")
				.append(");");
			Log.i(TAG, tag.toString());
			db.execSQL(tag.toString());
			
			StringBuffer fileItem = new StringBuffer(prefix);
			fileItem.append(TABLE_NAME_FILEITEM).append("(")
				.append(TABLE_ID + " integer primary key autoincrement,")
				.append(TABLE_FILEITEM_FILES + " integer,")
				.append(TABLE_PATH + " varchar,")
				.append(TABLE_FILEITEM_TAG + " varchar,")
				.append(TABLE_FILEITEM_ORDERS + " integer")
				.append(");");
			Log.i(TAG, fileItem.toString());
			db.execSQL(fileItem.toString());
			
			//初始化数据
			initData(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.i(TAG, "update database " + "oldVersion:" + oldVersion + ", newVersion:" + newVersion);
		}
		
		private void initData(SQLiteDatabase db){
			String sql1 = "insert into tags(name) values('证书');";
			db.execSQL(sql1);
			
			String sql2 = "insert into tags(name) values('名片');";
			db.execSQL(sql2);
			
			String sql3 = "insert into tags(name) values('图片');";
			db.execSQL(sql3);
			
			String sql4 = "insert into tags(name) values('文件');";
			db.execSQL(sql4);
			
			String sql5 = "insert into tags(name) values('幻灯片');";
			db.execSQL(sql5);
			
			String sql6 = "insert into tags(name) values('合同');";
			db.execSQL(sql6);
		}
	}
	
}
