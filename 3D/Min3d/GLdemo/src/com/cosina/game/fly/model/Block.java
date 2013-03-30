package com.cosina.game.fly.model;

import java.nio.FloatBuffer;
import java.util.Random;

import com.cosina.game.fly.control.Constants;

public class Block {
	
	public float x,y,z;
	public float[] rgba = getRandomRgba();
	
	public Block(float x, float z){
		this.x = x;
		this.y = 0.5f;
		this.z = z;
	}
	
	public void moveOut(){
		this.z ++;
	}
	
	public boolean dead(){
		return this.z >= 0;
	}
	
	static float one = 0.5f;
	
	public static FloatBuffer vertextsBuffer = FloatBuffer.wrap(new float[]{
			-one,-one,one,
			one,-one,one,
			-one,one,one,
			one,one,one,
			
			-one,-one,-one,
			one,-one,-one,
			-one,one,-one,
			one,one,-one,
			
			-one,one,one,
			-one,one,-one,
			-one,-one,-one,
			-one,-one,one,
			
			one,one,one,
			one,one,-one,
			one,-one,one,
			one,-one,-one,
			
			-one,one,one,
			one,one,one,
			-one,one,-one,
			one,one,-one,
			
			-one,-one,one,
			one,-one,one,
			-one,-one,-one,
			one,-one,-one,
	});
	
	private static Random  colorRandom = new Random();
	
	public static float[] getRandomRgba(){
		return Constants.colors[colorRandom.nextInt(Constants.colors.length)];
	}
}
