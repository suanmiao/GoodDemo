package com.irun.sm.ui.demo.ui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.Bundle;

/***
 * @author huangsm
 * @date 2012-6-28
 * @email huangsanm@gmail.com
 * @desc xml解析
 */
public class XMLActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String xml = "<action>哈哈</action><sss>你好</sss>";
		InputStream is = new ByteArrayInputStream(xml.getBytes());
		try {
			//xmlpullparserfactory
			XmlPullParserFactory xmlpull = XmlPullParserFactory.newInstance();
			xmlpull.setNamespaceAware(true);//名称空间
			XmlPullParser pullParser = xmlpull.newPullParser();
			pullParser.setInput(is, "UTF-8");
			int eventType = pullParser.getEventType();
			while(eventType != pullParser.END_DOCUMENT){
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					pullParser.getName();
					break;
				case XmlPullParser.START_TAG:
					
					break;
				case XmlPullParser.END_TAG:
					
					break;
				case XmlPullParser.END_DOCUMENT:
					
					break;
				}
				eventType = pullParser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void parserXml(String xml){
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new ByteArrayInputStream(xml.getBytes()), "utf-8");
			int eventType = parser.getEventType();
			switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					
					break;
				case XmlPullParser.START_TAG:
					System.out.println(parser.getName() + ":" + parser.getText());
					break;
				case XmlPullParser.END_TAG:
					
					break;
				case XmlPullParser.END_DOCUMENT:
					
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
