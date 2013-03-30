package com.cosina.game.fly.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class DialogUtil {
	public static void showNoCancelOnButton(Context context, String title, String message, String buttonText, final Runnable runable){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setNeutralButton(buttonText, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				runable.run();
			}
		});
		builder.show();
	}
	
	
	public static Runnable showNoCancelOnButtonAsAction(final Context context, final String title, final String message, final String buttonText, final Runnable runable){
		return new Runnable(){
			public void run(){
				showNoCancelOnButton(context,title,message,buttonText,runable);
			}
		};
	}
	
}
