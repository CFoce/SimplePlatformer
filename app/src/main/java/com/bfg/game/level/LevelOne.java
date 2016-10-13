package com.bfg.game.level;

import android.graphics.*;
import java.util.concurrent.*;

import com.bfg.game.button.*;
import com.bfg.game.text.*;
import com.bfg.game.scene.*;
import com.bfg.game.*;
import com.bfg.game.block.*;
import com.bfg.game.common.*;

public class LevelOne extends Level implements Scene
{
	private volatile int nextScene = 0;
    private volatile int tick = 0;
	private volatile Flag flag = new Flag();
	
	public void onCreate() {
		moveScale = 4;
		floor = 800;
		flag = new Flag(1104,176);
		level.add(flag);
		level.add(new BlockGray(408,272));
		level.add(new BlockGray(440,272));
		level.add(new BlockGray(472,272));
		
		level.add(new BlockBlack(600,240));
		level.add(new BlockBlack(632,240));
		level.add(new BlockBlack(664,240));
		
		level.add(new BlockYellow(784,304));
		level.add(new BlockYellow(816,304));
		
		level.add(new BlockRed(912,240));
		level.add(new BlockRed(944,240));
		
		level.add(new BlockGreen(1040,208));
		level.add(new BlockGreen(1072,208));
		level.add(new BlockGreen(1104,208));
		
		player = new Player();
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
		} else if(flag.trigger()) {
			setNextScene(1);
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
			if(reset.checkTouch(x,y)) {
				setNextScene(2);
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
