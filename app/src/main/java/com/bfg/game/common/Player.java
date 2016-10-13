package com.bfg.game.common;

import android.graphics.*;
import android.graphics.RectF;
import java.util.concurrent.*;
import com.bfg.game.common.*;

public class Player implements Sprite, Location, Collision
{
	private volatile float xPos = 0;
	private volatile float yPos = 0;
	private volatile Semaphore button = new Semaphore(10,true);
	private volatile Paint paintOne = new Paint();
	private volatile RectF left = new RectF(424-17,224-15,424-15,224+15);
	private volatile RectF top = new RectF(424-15,224-17,424+15,224-15);
	private volatile RectF right = new RectF(424+15,224-15,424+17,224+15);
	private volatile RectF bottom = new RectF(424-15,224+15,424+15,224+17);
	//private volatile RectF left = new RectF(424-16,224-16,424-16,224+16);
	//private volatile RectF top = new RectF(424-16,224-16,424+16,224-16);
	//private volatile RectF right = new RectF(424+16,224-16,424+16,224+16);
	//private volatile RectF bottom = new RectF(424-16,224+16,424+16,224+16);

	public Player() {
		setPosX(424);
		setPosY(224);
		setDraw();
	}

	public Player(float x, float y) {
		setPosX(x);
		setPosY(y);
		setDraw();
	}

	private void setDraw() {
		this.paintOne.setStyle(Paint.Style.FILL);
		this.paintOne.setColor(Color.BLUE);
	}

	public void drawDisplay(Canvas canvas) {
		canvas.drawCircle(getPosX(),getPosY(),16,paintOne);
	}
	public void draw(Canvas canvas) {
		canvas.drawCircle(424,224,16,paintOne);
	}
	public void changeDraw(int x, int y) {

	}

	public void setPosX(float x) {
		try {
			button.acquire(10);
			this.xPos = x;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			button.release(10);
		}
	}
	public void setPosY(float y) {
		try {
			button.acquire(10);
			this.yPos = y;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			button.release(10);
		}
	}
	public void setLocation(float[] location) {
		try {
			button.acquire(10);
			this.xPos = location[0];
			this.yPos = location[1];
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			button.release(10);
		}
	}
	public float getPosX() {
		try {
			button.acquire();
			return xPos;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			button.release();
		}
		return 0;
	}
	public float getPosY() {
		try {
			button.acquire();
			return yPos;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			button.release();
		}
		return 0;
	}
	public float[] getLocation() {
		try {
			button.acquire();
			float[] location = {xPos,yPos};
			return location;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			button.release();
		}
		float[] fail = {0,0};
		return fail;
	}
	
	public boolean collision(RectF r) {
	    //return RectF.intersects(rect,r);
		return false;
	}
	public void collisionAction(){
		
	}
	public boolean collisionLeft(RectF r) {
	    return RectF.intersects(left,r);
	}
	public boolean collisionTop(RectF r) {
	    return RectF.intersects(top,r);
	}
	public boolean collisionRight(RectF r) {
	    return RectF.intersects(right,r);
	}
	public boolean collisionBottom(RectF r) {
	    return RectF.intersects(bottom,r);
	}
	public boolean[] collide(RectF r) {
		boolean[] bool = {false,false,false,false};
	    bool[0] = collisionLeft(r);
		bool[1] = collisionTop(r);
		bool[2] = collisionRight(r);
		bool[3] = collisionBottom(r);
		return bool;
	}
}

