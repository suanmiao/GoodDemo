package com.irun.sm.ui.demo.ui.intent;

import com.irun.sm.ui.demo.ui.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/***
 * @author huangsm
 * @date 2013-1-14
 * @email huangsanm@gmail.com
 * @desc Intent说明
 */
public class ContactRecordActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.intent_layout);
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_contact_record :
				Uri uri = Uri.parse("content://contacts/people");
				Intent contact = new Intent(Intent.ACTION_PICK, uri);
				startActivity(contact);
				break;
		}
	}
	
}
