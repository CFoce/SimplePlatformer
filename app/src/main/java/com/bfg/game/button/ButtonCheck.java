package com.bfg.game.button;

import android.graphics.*;
import java.util.concurrent.*;

public class ButtonCheck implements Button
{
	private volatile float xPos = 0;
	private volatile float yPos = 0;
	private volatile String text = "";
	private volatile Semaphore button = new Semaphore(10,true);
	private volatile Paint paint = new Paint();
	private volatile Paint paintOne = new Paint();
    private volatile Paint paintTwo = new Paint();
	private volatile RectF rect;
	private volatile boolean state = false;

	public ButtonCheck(String text, float x, float y, boolean bool) {
		setState(bool);
		setText(text);
		setPosX(x);
		setPosY(y);
		setDraw();
	}
	
	public boolean checkTouch(int x, int y) {
		if(x >= getPosX() && x <= getPosX() + 25 && y <= getPosY() + 25 && y >= getPosY()) {
			if(isState()){
				setState(false);
			} else {
				setState(true);
			}
			return true;
		}
		return false;
	}

	private void setDraw() {
		this.rect = new RectF(getPosX(),getPosY(),25+getPosX(),25+getPosY());
		this.paint.setStyle(Paint.Style.FILL);
		this.paint.setColor(Color.LTGRAY);
		this.paintOne.setStyle(Paint.Style.STROKE);
		this.paintOne.setStrokeWidth(3);
		this.paintOne.setColor(Color.GRAY);
		this.paintTwo.setStyle(Paint.Style.FILL);
		this.paintTwo.setTextSize(35);
		this.paintTwo.setColor(Color.BLACK);
	}

	public void draw(Canvas canvas) {
		if(isState()) {
			canvas.drawRoundRect(rect,20,20,paintTwo);
		} else {
			canvas.drawRoundRect(rect,20,20,paint);
		}
		canvas.drawRoundRect(rect,20,20,paintOne);
		canvas.drawText(getText(), getPosX()+50, getPosY()+25, paintTwo);
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
		float[] points = {getPosX(),getPosY(),getPosX()+25,getPosY()+25};
		return points;
	}
	
	public void setState(boolean bool) {
		try {
			button.acquire(10);
			this.state = bool;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			button.release(10);
		}
	}
	
	public boolean isState() {
		try {
			button.acquire();
			return state;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			button.release();
		}
		return false;
	}

}
