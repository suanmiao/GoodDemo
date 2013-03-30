package com.cosina.game.fly.control;

import java.nio.FloatBuffer;

public interface Constants {
	float[] SKY_COLOR = {0.808f, 1f, 1f,1f};
	float[] GROUD_COLOR = {0.353f,0.353f,0.353f,1f};
	
	FloatBuffer GROUND = FloatBuffer.wrap(new float[]{
			-25f,0f,-26f,
			25f,0f,-26f,
			-25f,0f,10f,
			25f,0f,10f,
	});
	
	float[][] colors = new float[][]{
		{1f,0f,0f,1f}, //Red
		{0f,1f,0f,1f},	//green
		{0f,0f,1f,1f},	//blue
		{1f,1f,0f,1f},	//yellow
		{0f,1f,1f,1f},	//something
		{1f,0f,1f,1f},	//something
	};
}
