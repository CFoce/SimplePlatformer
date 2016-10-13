package com.bfg.game.text;

import android.graphics.*;
import java.util.concurrent.*;

import com.bfg.game.common.*;

public class TextCreditsTitle extends Text implements Sprite
{
	public TextCreditsTitle(String text, float x, float y) {
		super(text,x,y);
		setDraw();
	}

	private void setDraw() {
		paint.setARGB(255,0,51,102);
        paint.setTextSize(35);
		paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
		paintOne.setStyle(Paint.Style.STROKE);
		paintOne.setTextAlign(Paint.Align.CENTER);
        paintOne.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
		paintOne.setTextSize(35);
		paintOne.setStrokeWidth(2);
		paintOne.setARGB(255,73,143,255);
	}

	public void draw(Canvas canvas) {
		canvas.drawText(getText(), getPosX(), getPosY(), paint);
		canvas.drawText(getText(), getPosX(), getPosY(), paintOne);
	}
	public void changeDraw(int x, int y) {

	}
}
