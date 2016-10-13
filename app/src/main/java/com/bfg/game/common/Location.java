package com.bfg.game.common;


public interface Location
{
	public void setPosX(float x);
	public void setPosY(float y);
	public void setLocation(float[] location);
	public float getPosX();
	public float getPosY();
	public float[] getLocation();
}
