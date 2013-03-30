package com.cosina.game.fly.model;

import java.nio.FloatBuffer;

public class Plane {
	
	public float x;
	public final float y,z;
	public Plane(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static FloatBuffer planeBuffer = FloatBuffer.wrap(new float[]{
			0f,0.5f,
			0.4f,-0.1f,
			-0.4f,-0.1f
	});
	
}
