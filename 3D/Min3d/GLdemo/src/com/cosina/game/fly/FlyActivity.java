package com.cosina.game.fly;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;

import com.cosina.game.fly.control.UpdateEngine;
import com.cosina.game.fly.ui.GameView;
import com.cosina.game.fly.util.ActivityUtil;
import com.cosina.game.fly.util.DialogUtil;
import com.cosina.game.fly.util.SensorUtil;
import com.cosina.game.fly.util.SensorUtil.SensorRunnable;

public class FlyActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityUtil.noNotificationBar(this);
		ActivityUtil.noTitleBar(this);
		DialogUtil.showNoCancelOnButton(this, "Start Game",
				"Click For New Game", "Click", new Runnable() {
					public void run() {
						startGame();
					}
		});
	}

	private SensorEventListener backupForRemove;
	
	public void startGame() {
		final GameView gameView = new GameView(this);
 		setContentView(gameView);

		new UpdateEngine(this, gameView).start();
		
		if(null != backupForRemove)
			SensorUtil.removeSensorListener(this, backupForRemove);
		
		backupForRemove = SensorUtil.regSensorListener(this, new SensorRunnable() {
			
			@Override
			public void run(SensorEvent sensorEvent) {
				gameView.gameRender.rotate(sensorEvent.values[1] / 4);
			}
		}, Sensor.TYPE_ORIENTATION);
	};

}