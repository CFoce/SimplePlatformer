package com.bfg.game.scene;

import android.graphics.*;

import com.bfg.game.*;
import com.bfg.game.text.*;

public class SceneStartUp implements Scene
{
	private volatile static TextDev text = new TextDev("Blue Forest",424,200);
	private volatile static TextDev text1 = new TextDev("Games",424,300);
	private volatile int tick = 0;

	public void onCreate() {

	}
	public void onChange() {
		MainThread.setLoading(true);
		if(tick == 0){
			tick = UpdateThread.getTicks();
		}
		if(UpdateThread.getTicks() >= tick+30) {
			MainThread.scene = new SceneMainMenu();
			MainThread.scene.onCreate();
			MainThread.setLoading(false);
		}
	}
	public void onRender(Canvas canvas) {
		canvas.drawColor(Color.rgb(159,210,255));
        text.draw(canvas);
		text1.draw(canvas);
	}
	public void onUpdate() {
		if(UpdateThread.getTicks() >= 120) {
			onChange();
		}
	}
	public void onListener(boolean touch,int x,int y) {

	}
	public void setNextScene(int next) {

	}
	public int getNextScene() {
		return 0;
	}
}
