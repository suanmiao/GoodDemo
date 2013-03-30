package com.irun.sm.ui.demo.ui;

import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

/***
 * @author huangsm
 * @date 2012-10-31
 * @email huangsanm@gmail.com
 * @desc 
  		手机摇摇
  		方向传感器： Sensor.TYPE_ORIENTATION
		加速度(重力)传感器： Sensor.TYPE_ACCELEROMETER
		光线传感器: Sensor.TYPE_LIGHT
		磁场传感器： Sensor.TYPE_MAGNETIC_FIELD
		距离(临近性)传感器： Sensor.TYPE_PROXIMITY
		温度传感器： Sensor.TYPE_TEMPERATURE
 */
public class SensorActivity extends Activity implements SensorEventListener {

	private TextView mMsgTextView;
	private SensorManager mSensorManager;
	
	private String mSensorText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor_layout);
		
		mMsgTextView = (TextView) findViewById(R.id.msg);
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float[] values = event.values;
		mSensorText = "";
		System.out.println("(x/y/z):" + Arrays.toString(values));
		//考虑是什么传感器
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
			if(Math.abs(values[0]) > 17 || Math.abs(values[1]) > 17 || Math.abs(values[2]) > 17){
				mSensorText += "end:" + Arrays.toString(values);
				mMsgTextView.setText(mSensorText);
			}
		}		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		System.out.println("name:" + sensor.getName());
		System.out.println("MaximumRange：" + sensor.getMaximumRange());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(
				this, 
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
				SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}
}
