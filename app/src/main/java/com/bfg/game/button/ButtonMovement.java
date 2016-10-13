package com.bfg.game.button;

import android.graphics.*;
import java.util.concurrent.*;

public class ButtonMovement implements Button
{
	private volatile float xPos = 0;
	private volatile float yPos = 0;
	private volatile String text = "";
	private volatile Semaphore button = new Semaphore(10,true);
	private volatile Paint paint = new Paint();
	private volatile Paint paintOne = new Paint();
    private volatile Paint paintTwo = new Paint();
	private volatile RectF rect;

	public ButtonMovement(String text, float x, float y) {
		setText(text);
		setPosX(x);
		setPosY(y);
		setDraw();
	}

	public boolean checkTouch(int x, int y) {
		if(x >= getPosX() && x <= getPosX() + 74 && y <= getPosY() + 74 && y >= getPosY()) {
			return true;
		}
		return false;
	}

	private void setDraw() {
		this.rect = new RectF(getPosX(),getPosY(),74+getPosX(),74+getPosY());
		this.paint.setStyle(Paint.Style.FILL);
		this.paint.setARGB(100,255,255,255);
		this.paintOne.setStyle(Paint.Style.STROKE);
		this.paintOne.setStrokeWidth(2);
		this.paintOne.setColor(Color.GRAY);
		this.paintTwo.setStyle(Paint.Style.FILL);
		this.paintTwo.setTextSize(20);
		this.paintTwo.setColor(Color.GRAY);
		this.paintTwo.setTextAlign(Paint.Align.CENTER);
	}

	public void draw(Canvas canvas) {
		canvas.drawRoundRect(rect,15,15,paint);
		canvas.drawRoundRect(rect,15,15,paintOne);
		canvas.drawText(getText(), getPosX()+36, getPosY()+42, paintTwo);
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

	public void setText(String string) {
		try {
			button.acquire(10);
			this.text = string;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			button.release(10);
		}
	}
	public String getText() {
		try {
			button.acquire();
			return text;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			button.release();
		}
		return "Error";
	}
	public float[] actionArea() {
		float[] points = {getPosX(),getPosY(),getPosX()+150,getPosY()+30};
		return points;
	}

}
