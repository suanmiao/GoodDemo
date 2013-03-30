package com.irun.sm.ui.demo.ui;

import android.app.Activity;
import android.os.Bundle;

import com.adview.AdViewInterface;
import com.adview.AdViewLayout;
import com.adview.AdViewTargeting;
import com.adview.AdViewTargeting.RunMode;
import com.adview.AdViewTargeting.UpdateMode;

/***
 * @author huangsm
 * @date 2012-7-9
 * @email huangsanm@gmail.com
 * @desc 
 */
public class XMLAdViewActivity extends Activity implements AdViewInterface {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xml_layout);
		
		 /*下面两行只用于测试,完成后一定要去掉,参考文挡说明*/
        //AdViewTargeting.setUpdateMode(UpdateMode.EVERYTIME);  //每次都从服务器取配置
       // AdViewTargeting.setRunMode(RunMode.TEST);        //保证所有选中的广告公司都为测试状态
        /*下面这句方便开发者进行发布渠道统计,详细调用可以参考java doc  */
        //AdViewTargeting.setChannel(Channel.GOOGLEMARKET);
        AdViewLayout adViewLayout = (AdViewLayout)findViewById(R.id.adview_layout);
        adViewLayout.setAdViewInterface(this);
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
