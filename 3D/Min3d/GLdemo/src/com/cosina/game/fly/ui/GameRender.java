package com.cosina.game.fly.ui;


import javax.microedition.khronos.opengles.GL10;

import com.cosina.game.fly.control.Constants;
import com.cosina.game.fly.model.Block;
import com.cosina.game.fly.model.Game;
import com.cosina.game.fly.model.Plane;

public class GameRender extends BaseRender {
	
	private Game game;
	
	public float zRotate = 0;
	public float xSpan = 0;
	
	public void setGame(Game game){
		this.game = game;
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		super.baseDrawFrame(gl);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		
		
		
		gl.glTranslatef(0f, -2.00f, -9f);
		gl.glRotatef(5f, 1f, 0f, 0f);
		gl.glRotatef(zRotate, 0f, 0f, 1f);
		
		drawGround(gl);
		drawPlane(gl);
		drawBlocks(gl);
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

	private void drawGround(GL10 gl){
		gl.glColor4f(
				Constants.GROUD_COLOR[0], 
				Constants.GROUD_COLOR[1], 
				Constants.GROUD_COLOR[2], 
				Constants.GROUD_COLOR[3]);
		
//		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, Constants.GROUND);
//		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}
	
	private void drawPlane(GL10 gl){
		gl.glColor4f(0, 0, 0, 1);
		gl.glTranslatef(game.plane.x , game.plane.y, game.plane.z);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, Plane.planeBuffer);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		gl.glTranslatef(-game.plane.x, -game.plane.y, -game.plane.z);
	}
	
	private void drawBlocks(GL10 gl){
		gl.glTranslatef(0f, 0f, (float)game.movePart/(float)Game.MOVE_PART_MAX);
		for(Block each : game.getBlocks()){
			gl.glTranslatef(each.x + this.xSpan, each.y, each.z);
			gl.glColor4f(each.rgba[0], each.rgba[1], each.rgba[2], each.rgba[3]);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, Block.vertextsBuffer);
			
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 24);
			gl.glTranslatef(-each.x - this.xSpan, -each.y, -each.z);
		}
		game.returnBlocks();
		gl.glTranslatef(0, 0, -game.movePart/5);
	}
	
	@Override
	public float[] getClearColor() {
		return Constants.SKY_COLOR;
	}

	public void rotate(float z) {
		if(z >15){
			zRotate = 15;
		}else if(z < -15){
			zRotate = -15;
		}else {
			zRotate = z;
		}
		xSpan += zRotate/15f;
	}
}
