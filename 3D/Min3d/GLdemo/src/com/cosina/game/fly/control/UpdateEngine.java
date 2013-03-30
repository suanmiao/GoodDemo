package com.cosina.game.fly.control;

import android.opengl.GLSurfaceView;
import android.util.Log;

import com.cosina.game.fly.FlyActivity;
import com.cosina.game.fly.model.Game;
import com.cosina.game.fly.ui.GameView;
import com.cosina.game.fly.ui.InfoBar;
import com.cosina.game.fly.util.DialogUtil;

public class UpdateEngine extends Thread {
	private final FlyActivity updateThread;
	private final GLSurfaceView gsv;
	private final Game game;
	private final InfoBar infoBar;
	
	
	public UpdateEngine(FlyActivity flyActivity, GameView gameView) {
		updateThread = flyActivity;
		this.gsv = gameView.gsv;
		this.game = gameView.game;
		this.infoBar = gameView.infoBar;
	}

	@Override
	public void run() {
		infoBar.start();
		while (game.collesion == false) {
			long time = System.currentTimeMillis();
			gsv.requestRender();
			try {
				game.update();
				time = 10 - System.currentTimeMillis() + time;
				if (time > 0)
					sleep(time);
			} catch (InterruptedException e) {
				Log.e("cosina1985", e.getMessage(), e);
			}
		}
		infoBar.end();
		gsv.post(DialogUtil.showNoCancelOnButtonAsAction(gsv.getContext(),
				"GameOver", "Click For New Game", "Click", new Runnable() {
					public void run() {
						UpdateEngine.this.updateThread.startGame();
					}
				}));
	}
}