package com.irun.sm.ui.demo.vo;
/***
 * @author huangsm
 * @date 2012-10-30
 * @email huangsanm@gmail.com
 * @desc 直方图item
 */
public class HistogramItem {
	
	private String title;
	private float value;
	private int color;
	
	public HistogramItem(String title, float value, int color) {
		this.title = title;
		this.value = value;
		this.color = color;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	
	
}
