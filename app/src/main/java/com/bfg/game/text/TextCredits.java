package com.bfg.game.text;

import android.graphics.*;
import java.util.concurrent.*;

import com.bfg.game.common.*;

public class TextCredits extends Text implements Sprite
{
	public TextCredits(String text, float x, float y) {
		super(text,x,y);
		setDraw();
	}

	private void setDraw() {
		paint.setARGB(255,0,51,102);
        paint.setTextSize(25);
		paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
	}

	public void draw(Canvas canvas) {
		canvas.drawText(getText(), getPosX(), getPosY(), paint);
	}
	public void changeDraw(int x, int y) {

	}
}
