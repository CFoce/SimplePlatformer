package com.bfg.game.scene;

import android.graphics.*;

import com.bfg.game.text.*;
import com.bfg.game.*;

public class SceneLoading implements Scene
{
	private volatile static TextTitle text = new TextTitle("Loading",424,250);

	public void onCreate() {

	}
	public void onChange() {

	}
	public void onRender(Canvas canvas) {
        text.draw(canvas);
	}
	public void onUpdate() {

	}
	public void onListener(boolean touch,int x,int y) {

	}
	public void setNextScene(int next) {

	}
	public int getNextScene() {
		return 0;
	}
}
