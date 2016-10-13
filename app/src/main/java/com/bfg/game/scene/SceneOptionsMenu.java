package com.bfg.game.scene;

import android.graphics.*;
import java.util.concurrent.*;

import com.bfg.game.button.*;
import com.bfg.game.text.*;
import com.bfg.game.*;

public class SceneOptionsMenu implements Scene
{
	private volatile static TextTitle title = new TextTitle("Options",225,200);
	private volatile static ButtonStandard button = new ButtonStandard("Main Menu",75,350);
	private volatile static ButtonStandard credits = new ButtonStandard("Credits",473,350);
	private volatile static ButtonCheck check = new ButtonCheck("Dev Mode",525,90,MainThread.isDevMode());
	private volatile static ButtonCheck check1 = new ButtonCheck("Cap FPS",525,180,MainThread.isFPSCap());
	private volatile static ButtonCheck check2 = new ButtonCheck("Sound",525,270,false);
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
			switch(getNextScene()) {
				case 1:
					MainThread.scene = new SceneMainMenu();
					break;
				case 2:
					MainThread.scene = new SceneCredits();
					break;
			}
			MainThread.scene.onCreate();
			MainThread.setLoading(false);
		}
	}
	public void onRender(Canvas canvas) {
		check.draw(canvas);
		check1.draw(canvas);
		//check2.draw(canvas);
		button.draw(canvas);
		credits.draw(canvas);
		title.draw(canvas);
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
			if(credits.checkTouch(x,y)) {
			    setNextScene(2);
			}
			if(check.checkTouch(x,y)) {
				if(MainThread.isDevMode()) {
					MainThread.setDevMode(false);
				} else {
					MainThread.setDevMode(true);
				}
			}
			if(check1.checkTouch(x,y)) {
				if(MainThread.isFPSCap()) {
					MainThread.setFPSCap(false);
				} else {
					MainThread.setFPSCap(true);
				}
			}
			if(check2.checkTouch(x,y)) {

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
