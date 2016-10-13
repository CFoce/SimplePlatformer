package com.bfg.game.text;

import android.graphics.*;
import java.util.concurrent.*;

import com.bfg.game.common.*;

public class TextDev extends Text implements Sprite
{
	public TextDev(String text, float x, float y) {
		super(text,x,y);
		setDraw();
	}

	private void setDraw() {
		paint.setARGB(255,0,51,102);
        paint.setTextSize(75);
		paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
		paintOne.setStyle(Paint.Style.STROKE);
		paintOne.setTextAlign(Paint.Align.CENTER);
        paintOne.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
		paintOne.setTextSize(75);
		paintOne.setStrokeWidth(3);
		paintOne.setARGB(255,73,143,255);
	}

	public void draw(Canvas canvas) {
		canvas.drawText(getText(), getPosX(), getPosY(), paint);
		canvas.drawText(getText(), getPosX(), getPosY(), paintOne);
	}
	public void changeDraw(int x, int y) {

	}
}
