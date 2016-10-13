package com.bfg.game.scene;

import android.graphics.*;
import java.util.concurrent.*;

import com.bfg.game.button.*;
import com.bfg.game.text.*;
import com.bfg.game.*;
import com.bfg.game.block.*;
import com.bfg.game.common.*;

public class SceneMainMenu implements Scene
{
	private volatile static ButtonStandard button = new ButtonStandard("Select Level",480,90);
	private volatile static ButtonStandard button1 = new ButtonStandard("Options",480,210);
	private volatile static ButtonStandard button2 = new ButtonStandard("Extra Levels",480,330);
	private volatile static TextTitle title = new TextTitle("Simple",225,150);
	private volatile static TextTitle title1 = new TextTitle("Platformer",225,225);
	private volatile static TextCredits alpha = new TextCredits("Beta Build 0.1.0",225,275);
	private volatile static Block[] blocks = new Block[7];
	private volatile static Block exit = new Flag(305,318);
	private volatile Player player = new Player(129,334);
	private volatile int nextScene = 0;
	private volatile static Semaphore sem = new Semaphore(10,true);
    private volatile int tick = 0;

	public void onCreate() {
		for(int i = 0; i < blocks.length; i++) {
			blocks[i] = new BlockBlack(113+i*32,350);
		}
	}
	public void onChange() {
		MainThread.setLoading(true);
		if(tick == 0){
			tick = UpdateThread.getTicks();
		}
		if(UpdateThread.getTicks() >= tick+30) {
			switch(getNextScene()) {
				case 1:
					MainThread.scene = new SceneLevelMenu();
					break;
				case 2:
					MainThread.scene = new SceneOptionsMenu();
					break;
				case 3:
					MainThread.scene = new SceneExtraLevels();
					break;
			}
			MainThread.scene.onCreate();
			MainThread.setLoading(false);
		}
	}
	public void onRender(Canvas canvas) {
		player.drawDisplay(canvas);
		exit.draw(canvas);
		for(int i = 0; i < blocks.length; i++) {
			blocks[i].draw(canvas);
		}
        title.draw(canvas);
		title1.draw(canvas);
		alpha.draw(canvas);
		button.draw(canvas);
		button1.draw(canvas);
		button2.draw(canvas);
	}
	public void onUpdate() {
		if(getNextScene() != 0) {
			onChange();
		}
		if(player.getPosX() >= 313) {
			float[] loc = {129,334};
			player.setLocation(loc);
		}
		player.setPosX(player.getPosX()+1);
	}
	public void onListener(boolean touch,int x,int y) {
		if(touch) {
			if(button.checkTouch(x,y)) {
			    setNextScene(1);
			}
			if(button1.checkTouch(x,y)) {
			    setNextScene(2);
			}
			if(button2.checkTouch(x,y)) {
				setNextScene(3);
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
