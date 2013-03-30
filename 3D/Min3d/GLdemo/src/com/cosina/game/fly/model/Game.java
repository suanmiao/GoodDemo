package com.cosina.game.fly.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

import com.cosina.game.fly.ui.GameRender;

public class Game {
	
	public static final int X_RANGE = 20;
	public static final int HALF_X_RANGE = 20/2;
	public static final int Y_RANGE = 20;
	 
	private Random random = new Random();
	public int movePart = 0;

	private List<Block> blocks = new ArrayList<Block>();
	public Plane plane = new Plane(0f,0.0f,0f);
	
	
	private Semaphore semaphore = new Semaphore(1);
	
	public List<Block> getBlocks(){
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return blocks;
	}
	
	public void returnBlocks(){
		semaphore.release();
	}
	
	public int farestRow = -18;
	public boolean collesion = false;
	public boolean generation = false;
	public final static int MOVE_PART_MAX = 7;
	private GameRender renderEngine;
	
	public Game(GameRender flyRender){
		renderEngine = flyRender;
		for(int i=-14; i>farestRow;i--){
			getNewBlocks(i);
		}
	}
	
	public void update() {
		movePart ++;
		if(movePart == MOVE_PART_MAX){
			List<Block> blocks = getBlocks();
			Iterator<Block> blockIter = blocks.iterator();
			while(blockIter.hasNext()){
				Block each = blockIter.next();
				each.moveOut();
				if(each.dead()){
					blockIter.remove();
				}
				if(		(Math.abs(each.y-plane.y) < 1f) 
						&& (Math.abs(each.z - plane.z)<1f) 
						&& (Math.abs(each.x + renderEngine.xSpan -plane.x)) <1f){
					collesion = true;
				}
			}
			generation = !generation;
			if(generation){
				getNewBlocks(farestRow);
			}
			
			returnBlocks();
			movePart = 0;
		}
		
	}
	
	
	
	private void getNewBlocks(int row){
		int aIndex = getRandomXIndex();
		blocks.add(new Block(aIndex - renderEngine.xSpan, row));
		if(random.nextBoolean() == true){
			int bIndex2 = getRandomXIndex();
			if(bIndex2 != aIndex)
				blocks.add(new Block(bIndex2 - renderEngine.xSpan, row));
		}
	}
	
	private int getRandomXIndex(){
		return random.nextInt(X_RANGE) - HALF_X_RANGE;
	}
}
