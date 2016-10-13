package com.bfg.game.block;

import com.bfg.game.common.*;

public interface Block extends Collision, Sprite, Location, HitBox
{
	public float collisionMove(String string, float x, float y);
}
