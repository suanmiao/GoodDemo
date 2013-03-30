package com.irun.sm.ui.demo.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

/***
 * @author huangsm
 * @date 2012-7-24
 * @email huangsanm@gmail.com
 * @desc popupwindow
 * showAsDropDown不能放在oncreate方法中执行
 */
public class PopupActivity extends Activity {

	private Context mContext;
	private PopupWindow mPopup;
	private String[] mData = new String[]{"Item1", "Item2", "Item3", "Item4"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_layout);
		mContext = this;
		
		Button btn = (Button) findViewById(R.id.btn_popup);
		btn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				showPopupWindow(v);
			}
		});
	}
	
	private void showPopupWindow(View anchor){
		ListView listView = new ListView(mContext);
		//listView.setBackgroundColor(Color.WHITE);
		listView.setPadding(2, 2, 2, 2);
		//listView.setCacheColorHint(Color.WHITE);
		listView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		ArrayAdapter<String> adapter = 
				new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, android.R.id.text1, mData);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ListView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(mContext, "" + position, Toast.LENGTH_SHORT).show();
			}
		});
		
		mPopup = new PopupWindow(listView, 350, 450);
		//popup.setWidth(350);
		//popup.setHeight(200);
		//popup.setContentView(listView);
		mPopup.setAnimationStyle(R.style.animationFade);
		mPopup.setBackgroundDrawable(new BitmapDrawable());
		mPopup.setFocusable(true);
		mPopup.setTouchable(true);
		//mPopup.setOutsideTouchable(true);
		mPopup.showAsDropDown(anchor, Gravity.RIGHT|Gravity.BOTTOM, 10);
		/*mPopup.setTouchInterceptor(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
					mPopup.dismiss();
				}
				return false;
			}
		});*/
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(mPopup != null && mPopup.isShowing()){
				mPopup.dismiss();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
