package com.irun.sm.ui.demo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.adview.AdViewInterface;
import com.adview.AdViewLayout;

/***
 * @author huangsm
 * @date 2012-7-9
 * @email huangsanm@gmail.com
 * @desc 纯代码方式
 */
public class CodeActivity extends Activity implements AdViewInterface{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 /*下面两行只用于测试,完成后一定要去掉,参考文挡说明*/
        //AdViewTargeting.setUpdateMode(UpdateMode.EVERYTIME);  //每次都从服务器取配置
        //AdViewTargeting.setRunMode(RunMode.TEST);        //保证所有选中的广告公司都为测试状态

    	AdViewLayout adViewLayout = new AdViewLayout(this, "SDK201207150706098ng5d8fpuvjg0gi");
    	adViewLayout.setAdViewInterface(this);
    	FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, 
    			FrameLayout.LayoutParams.WRAP_CONTENT);		
    	params.gravity=Gravity.BOTTOM; 
    	setContentView(adViewLayout, params);
	}

	@Override
	public void onClickAd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisplayAd() {
		// TODO Auto-generated method stub
		
	}
}
