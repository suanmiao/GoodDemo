package com.cosina.game.fly.util;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class ActivityUtil {
	public static void noTitleBar(Activity activity){
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE); 
	}
	
	public static void noNotificationBar(Activity activity){
		final Window win = activity.getWindow(); 
		win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	public static final int TITLE_BAR = 1<<1; 
	public static final int NOTIFICATION_BAR = 1<<2; 
	
	
	public static void no(int barMask,Activity activity){
		if((TITLE_BAR & barMask) == 1){
			noTitleBar(activity);
		}
		
		if((NOTIFICATION_BAR & barMask) == 1){
			noNotificationBar(activity);
		}
	}
}
