package com.irun.sm.ui.demo.utils;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/***
 * @author huangsm
 * @date 2012-6-27
 * @email huangsanm@gmail.com
 * @desc XML解析
 */
public class XMLContentHandler extends DefaultHandler {

	private Map<String, Object> params;
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		params = new HashMap<String, Object>();
		
		System.out.println("startDocument......");
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		System.out.println("characters......");
		System.out.println("ch:" + new String(ch));
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		System.out.println("startElement......");
		System.out.println("uri:" + uri + ",localName:" + localName + ",qName:" + qName + ",attributes:" + attributes);
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		System.out.println("endElement......");
		System.out.println("uri:" + uri + ",localName:" + localName + ",qName:" + qName);
	}
	
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		System.out.println("endDocument......");
	}
	
	public Map<String, Object> getParamsMap(){
		return params;
	}
}
