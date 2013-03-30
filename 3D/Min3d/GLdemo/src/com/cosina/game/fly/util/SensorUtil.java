package com.cosina.game.fly.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorUtil {
	public static SensorEventListener regSensorListener(Context context,
			final SensorRunnable runnable, int type) {
		SensorManager sensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		Sensor sensor = sensorManager.getDefaultSensor(type);
		SensorEventListener someSensorListener = new SensorEventListener() {

			@Override
			public void onSensorChanged(SensorEvent event) {
				runnable.run(event);
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
			}
		};
		sensorManager.registerListener(someSensorListener, sensor,
				SensorManager.SENSOR_DELAY_GAME);

		return someSensorListener;
	}

	public static void removeSensorListener(Context context,
			SensorEventListener someSensorListener) {
		SensorManager sensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		
		sensorManager.unregisterListener(someSensorListener);
	}
	
	public interface SensorRunnable {
		void run(SensorEvent sensorEvent);
	}
	
}
