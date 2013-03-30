package com.irun.sm.ui.demo.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.irun.sm.ui.demo.view.ShareAdapter;

public class ShareDemoActivity extends Activity implements OnClickListener {
    
	private final static int SHARE_ALL = 1;
	private final static int SHARE_EMAIL = 2;
    private ShareAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share);
        
        Button btn = (Button) findViewById(R.id.btn_share_all);
        btn.setOnClickListener(this);
        
        btn = (Button) findViewById(R.id.btn_share_email);
        btn.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
    	switch (v.getId()) {
			case R.id.btn_share_all:
				showDialog(SHARE_ALL);
				break;
			case R.id.btn_share_email:
				showDialog(SHARE_EMAIL);
				break;
		}
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	Intent intent = null;
    	ListView view = new ListView(this);
    	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    	dialog.setTitle("分享");
    	dialog.setView(view);
    	switch (id) {
			case SHARE_ALL:
				intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				mAdapter = new ShareAdapter(this, intent);
				view.setAdapter(mAdapter);
				break;
			case SHARE_EMAIL:
				intent = new Intent(Intent.ACTION_SENDTO);
				intent.setData(Uri.parse("mailto:"));
				mAdapter = new ShareAdapter(this, intent);
				view.setAdapter(mAdapter);
				break;
		}
    	final AlertDialog ad = dialog.create();
    	view.setOnItemClickListener(new ListView.OnItemClickListener(){
    		@Override
    		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    			Intent i = mAdapter.getIntentForPosition(position);
    			startActivity(i);
    			ad.dismiss();
    		}
    	});
    	return ad;
    }
    
}