package com.irun.sm.ui.demo.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;

/***
 * @author huangsm
 * @date 2012-12-5
 * @email huangsanm@gmail.com
 * @desc 微信分享
 */
public class WeiChatActivity extends Activity {
	
	private static final String APP_ID = "wx11ae08ae7be076d8";
	
	private IWXAPI mApi;
	
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Button tv = new Button(this);
		tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		tv.setText("Share WeiChat");
		setContentView(tv);
		mContext = this;

		mApi = WXAPIFactory.createWXAPI(mContext, APP_ID, true);
        //将应用注册的微信
        boolean register = mApi.registerApp(APP_ID);
        System.out.println("reg:" + register);
		
		tv.setOnClickListener(new Button.OnClickListener() {

					@Override
					public void onClick(View v) {
						shareFriend();
					}
				});
	}

	// 告诉好友
	private void shareFriend() {
		boolean result = false;
        final String text = "my app";
        final String content = "UIDemo";
        WXTextObject wxTextObject = new WXTextObject();
        wxTextObject.text = text;
        
        WXMediaMessage wxMediaMessage = new WXMediaMessage();
        wxMediaMessage.mediaObject = wxTextObject;
        wxMediaMessage.title = text;
        wxMediaMessage.description = content;
        
        SendMessageToWX.Req sendReq = new SendMessageToWX.Req();
        sendReq.transaction = String.valueOf(System.currentTimeMillis());
        sendReq.message = wxMediaMessage;
        //发送至朋友圈
        sendReq.scene = SendMessageToWX.Req.WXSceneTimeline;
        //发送到会话
        //sendReq.scene = SendMessageToWX.Req.WXSceneSession;
        
        //发送数据
        result = mApi.sendReq(sendReq);
        
        /*GetMessageFromWX.Resp sendResp = new GetMessageFromWX.Resp();
        //new GetMessageFromWX.Req(getIntent().getExtras()).transaction;
        sendResp.transaction = String.valueOf(System.currentTimeMillis());
        sendResp.message = wxMediaMessage;
        result = mApi.sendResp(sendResp);*/
        
        System.out.println("result:" + result);
	}
}
