package com.irun.sm.ui.demo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/***
 * @author huangsm
 * @date 2012-11-5
 * @email huangsanm@gmail.com
 * @desc 数据操作
 */
public class DBOpenHelper extends SQLiteOpenHelper {

	private final static String DATABASE_NAME = "multi_download.db";
	private final static int VERSION = 1;
	
	public DBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists filedown(" +
				"id integer primary key autoincrement, " +
				"downpath varchar(100), " +
				"threadid integer, " +
				"position integer)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists filedown");
		onCreate(db);
	}

	
}
