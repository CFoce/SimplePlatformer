package com.bfg.game.level;

import android.graphics.*;
import java.util.concurrent.*;

import com.bfg.game.button.*;
import com.bfg.game.text.*;
import com.bfg.game.scene.*;
import com.bfg.game.*;
import com.bfg.game.block.*;

public class LevelThree extends Level implements Scene
{
	private volatile int nextScene = 0;
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
					MainThread.scene = new LevelOne();
					break;
			}
			MainThread.scene.onCreate();
			MainThread.setLoading(false);
		}
	}
	public void onRender(Canvas canvas) {
		bufferRender(canvas);
	}
	public void onUpdate() {
		if(getNextScene() != 0) {
			onChange();
		} else {
			movement();
		    bufferUpdate();
		}
	}
	public void onListener(boolean touch,int x,int y) {
		if(touch) {
			if(left.checkTouch(x,y)) {
				setTouchLeft(true);
			}
			if(right.checkTouch(x,y)) {
				setTouchRight(true);
			}
			if(jump.checkTouch(x,y)) {
				if(!getTouchJump() && !UpdateThread.isPause()) {
					setTouchJump(true);
				}
			}
		} 
		if(!touch) {
			setTouchLeft(false);
			setTouchRight(false);
			if(menu.checkTouch(x,y)) {
				setNextScene(1);
			}
			if(pause.checkTouch(x,y)) {
				if(UpdateThread.isPause()) {
					UpdateThread.unPause();
				} else {
					UpdateThread.pause();
				}
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
