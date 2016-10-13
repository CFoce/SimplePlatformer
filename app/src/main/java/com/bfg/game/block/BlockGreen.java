package com.bfg.game.block;

import java.util.concurrent.*;
import android.graphics.*;

public class BlockGreen implements Block
{
	private volatile float xPos = 0;
	private volatile float yPos = 0;
	private volatile Semaphore button = new Semaphore(10,true);
	private volatile Paint paint = new Paint();

	public BlockGreen() {
		setPosX(0);
		setPosY(0);
		setDraw();
	}

	public BlockGreen( float x, float y) {
		setPosX(x);
		setPosY(y);
		setDraw();
	}

	private void setDraw() {
		this.paint.setStyle(Paint.Style.FILL);
		this.paint.setColor(Color.GREEN);
	}

	public void draw(Canvas canvas) {
		canvas.drawRect(getPosX(),getPosY(),getPosX()+32,getPosY()+33,paint);
	}
	public void changeDraw(int x, int y) {
		setPosX(x);
		setPosY(y);
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
	
	public RectF hitBox() {
		return new RectF(getPosX(),getPosY(),getPosX()+32,getPosY()+32);
	}
	
	public boolean collision(RectF r) {return false;}
	public void collisionAction() {}
	public float collisionMove(String string, float x, float y){return x;}
}
