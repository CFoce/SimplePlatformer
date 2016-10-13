package com.bfg.game.button;

import android.graphics.*;
import java.util.concurrent.*;

import com.bfg.game.common.*;

public interface Button extends Sprite, Location
{
	public boolean checkTouch(int x, int y);
	public void setText(String string);
	public String getText();
	public float[] actionArea();
}
