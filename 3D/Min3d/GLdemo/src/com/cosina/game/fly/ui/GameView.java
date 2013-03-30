package com.cosina.game.fly.ui;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cosina.game.fly.model.Game;

public class GameView extends LinearLayout {

	public final Game game;
	public final GLSurfaceView gsv;
	public final InfoBar infoBar;
	public final GameRender gameRender;
	
	
	public GameView(Context context) {
		super(context);
		this.setLayoutParams(new LayoutParams(-1,-1));
		this.setOrientation(LinearLayout.VERTICAL);
		this.infoBar = new InfoBar(context);
		this.addView(infoBar,ViewGroup.LayoutParams.FILL_PARENT,30);
		
		gsv = new GLSurfaceView(context);
		gameRender = new GameRender();
		game = new Game(gameRender);
		gameRender.setGame(game);
		gsv.setRenderer(gameRender);
		gsv.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		
		this.addView(gsv,ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT);
	}
}
