package com.bfg.game.scene;

import android.graphics.*;

public interface Scene
{
	public void onCreate();
	public void onChange();
	public void onRender(Canvas Canvas);
	public void onUpdate();
	public void onListener(boolean touch,int x,int y);
	public void setNextScene(int next);
	public int getNextScene();
}
