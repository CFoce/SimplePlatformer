package com.bfg.game.block;
import java.util.concurrent.*;
import android.graphics.*;

public class Flag implements Block
{
	private volatile float xPos = 0;
	private volatile float yPos = 0;
	private volatile Semaphore button = new Semaphore(10,true);
	private volatile Paint paint = new Paint();
	private volatile Paint paintTwo = new Paint();
	private volatile Paint paintThree = new Paint();
	private volatile Path path = new Path();
	private volatile boolean trigger = false;
	
	public Flag() {
		setPosX(0);
		setPosY(0);
		setDraw();
		setFlag();
	}

	public Flag( float x, float y) {
		setPosX(x);
		setPosY(y);
		setDraw();
		setFlag();
	}

	private void setDraw() {
		this.paint.setStyle(Paint.Style.FILL);
		this.paint.setColor(Color.GRAY);
		this.paintTwo.setStyle(Paint.Style.FILL);
		this.paintTwo.setColor(Color.DKGRAY);
		this.paintThree.setStyle(Paint.Style.FILL);
		this.paintThree.setColor(Color.LTGRAY);
	}

	public void draw(Canvas canvas) {
		canvas.drawPath(path, paintThree);
		canvas.drawRect(getPosX()+24,getPosY()-28,getPosX()+28,getPosY()+32,paint);
		canvas.drawRect(getPosX()+22,getPosY()+28,getPosX()+30,getPosY()+32,paintTwo);
		canvas.drawCircle(getPosX()+26,getPosY()-28,4,paintTwo);
	}
	public void changeDraw(int x, int y) {
		setPosX(x);
		setPosY(y);
		setFlag();
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
		return new RectF(getPosX()+16,getPosY()-24,getPosX()+16,getPosY()+32);
	}
	
	public void setFlag() {
		path = new Path();
		path.moveTo(getPosX(),getPosY()-15);
		path.lineTo(getPosX()+24,getPosY()-5);
		path.lineTo(getPosX()+24,getPosY()-25);
		path.lineTo(getPosX(),getPosY()-15);
		path.close();
	}
	
	public boolean collision(RectF r) {return false;}
	public void collisionAction() {trigger = true;}
	public float collisionMove(String string, float x, float y){return 0;}
	public boolean trigger() {return trigger;}
}
