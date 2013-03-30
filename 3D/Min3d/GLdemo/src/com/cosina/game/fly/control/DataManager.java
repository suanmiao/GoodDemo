package com.cosina.game.fly.control;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;

public class DataManager {
	private static Resources resources;
	private static Map<Integer,Bitmap> bms = new HashMap<Integer,Bitmap>();
	
	public static void init(Resources resources){
		DataManager.resources = resources;
	}
	
	public static Bitmap getBm(int id){
		return bms.get(id);
	}
	
	public static InputStream getFile(String name){
		AssetManager am = resources.getAssets();
		try {
			return am.open(name);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
