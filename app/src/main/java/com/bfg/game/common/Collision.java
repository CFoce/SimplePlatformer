package com.bfg.game.common;

import android.graphics.*;

public interface Collision extends Location
{
	public boolean collision(RectF r);
	public void collisionAction();
}
