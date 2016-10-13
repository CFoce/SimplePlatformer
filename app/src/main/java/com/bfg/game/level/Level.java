package com.bfg.game.level;

import com.bfg.game.button.*;
import java.util.concurrent.*;
import com.bfg.game.block.*;
import android.graphics.*;
import com.bfg.game.common.*;
import java.util.*;
import com.bfg.game.*;

public class Level
{
	protected volatile ArrayList<Block> level = new ArrayList<Block>();
	protected volatile ButtonLevelMenu menu = new ButtonLevelMenu("Main Menu",675,25);
	protected volatile ButtonLevelMenu pause = new ButtonLevelMenu("Pause",500,25);
	protected volatile ButtonLevelMenu reset = new ButtonLevelMenu("Reset",325,25);
	protected volatile ButtonMovement left = new ButtonMovement("Left",25,379);
	protected volatile ButtonMovement right = new ButtonMovement("Right",125,379);
	protected volatile ButtonMovement jump = new ButtonMovement("Jump",725,379);
	protected volatile static Semaphore sem = new Semaphore(10,true);
	protected volatile static Semaphore buf = new Semaphore(10,true);
	protected volatile Player player = new Player();
	protected volatile boolean[] touch = new boolean[3];
	protected volatile int jumpTicker = 0;
	protected volatile boolean jumpFlag = true;
	protected volatile float[] newPos;
	protected volatile float[] movement = {0,0};
	protected volatile boolean[] collide = new boolean[]{false,false,false,false};
	protected volatile int moveScale;
	protected volatile int floor;
	
	protected void levelWalls() {
		
	}
	protected void bufferUpdate() {
		try {
			buf.acquire(10);
			for (int i = 0; i < level.size(); i++) {
			    level.get(i).changeDraw((int)(level.get(i).getPosX()-movement[0]),(int)(level.get(i).getPosY()+movement[1]));
			}
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			buf.release(10);
		}
	}
	
	protected void bufferRender(Canvas canvas) {
		player.draw(canvas);
		try {
			buf.acquire();
			for (int i = 0; i < level.size(); i++) {
				level.get(i).draw(canvas);
			}
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			buf.release();
		}
		reset.draw(canvas);
		pause.draw(canvas);
		menu.draw(canvas);
		left.draw(canvas);
		right.draw(canvas);
		jump.draw(canvas);
	}
	
	protected void movement() {
		movement = new float[]{0,0};
		collide = new boolean[]{false,false,false,false};
		for (int i = 0; i < level.size(); i++) {
			if(player.collisionLeft(level.get(i).hitBox())) {
				collide[0] = true;
				level.get(i).collisionAction();
			}
			if(player.collisionTop(level.get(i).hitBox())) {
				collide[1] = true;
				level.get(i).collisionAction();
			}
			if(player.collisionRight(level.get(i).hitBox())) {
				collide[2] = true;
				level.get(i).collisionAction();
			}
			if(player.collisionBottom(level.get(i).hitBox())) {
				collide[3] = true;
				level.get(i).collisionAction();
			}
		}
		if(getTouchLeft() && !collide[0]) {
			movement[0] = -moveScale;
		}
		if(getTouchRight() && !collide[2]) {
			movement[0] = moveScale;
		}
		if(getTouchJump() && !collide[1] && jumpFlag) {
			movement[1] = moveScale;
			jumpTicker++;
		} else if(!collide[3]) {
			movement[1] = -moveScale;
			jumpFlag = false;
		} else if(collide[3]) {
			setTouchJump(false);
			jumpFlag = true;
		}
		if(jumpTicker >= 24) {
			jumpFlag = false;
			jumpTicker = 0;
		}
		
		player.setPosX(player.getPosX()-movement[0]);
		player.setPosY(player.getPosY()-movement[1]);
		if(player.getPosY() > floor) {MainThread.scene.setNextScene(2);}
	}
	
	protected void setTouchLeft(boolean touch) {
		try {
			sem.acquire(10);
			this.touch[0] = touch;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			sem.release(10);
		}
	}
	protected boolean getTouchLeft() {
		try {
			sem.acquire();
			return touch[0];
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			sem.release();
		}
		return false;
	}
	protected void setTouchRight(boolean touch) {
		try {
			sem.acquire(10);
			this.touch[1] = touch;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			sem.release(10);
		}
	}
	protected boolean getTouchRight() {
		try {
			sem.acquire();
			return touch[1];
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			sem.release();
		}
		return false;
	}
	protected void setTouchJump(boolean touch) {
		try {
			sem.acquire(10);
			this.touch[2] = touch;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			sem.release(10);
		}
	}
	protected boolean getTouchJump() {
		try {
			sem.acquire();
			return touch[2];
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			sem.release();
		}
		return false;
	}
}
