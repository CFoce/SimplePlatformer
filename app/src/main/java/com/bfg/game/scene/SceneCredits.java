package com.bfg.game.scene;

import android.graphics.*;
import java.util.concurrent.*;

import com.bfg.game.*;
import com.bfg.game.button.*;
import com.bfg.game.text.*;

public class SceneCredits implements Scene
{
	private volatile static TextDev title = new TextDev("Credits",424,75);
	private volatile static TextCreditsTitle title1 = new TextCreditsTitle("Blue Forest Games",424,125);
	private volatile static TextCredits title2 = new TextCredits("Programmer: Chris Foce",424,175);
	private volatile static ButtonTwo button = new ButtonTwo("Main Menu",274,400);
	private volatile int nextScene = 0;
	private volatile static Semaphore sem = new Semaphore(10,true);
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
		button.draw(canvas);
		title.draw(canvas);
		title1.draw(canvas);
		title2.draw(canvas);
	}
	public void onUpdate() {
		if(getNextScene() != 0) {
			onChange();
		}
	}
	public void onListener(boolean touch,int x,int y) {
		if(touch) {
			if(button.checkTouch(x,y)) {
			    setNextScene(1);
			}
		}
	}

	public void setNextScene(int next) {
		try {
			sem.acquire(10);
			this.nextScene = next;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			sem.release(10);
		}
	}
	public int getNextScene() {
		try {
			sem.acquire();
			return nextScene;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			sem.release();
		}
		return 0;
	}
}
