package com.bfg.game.scene;

import android.graphics.*;
import java.util.concurrent.*;

import com.bfg.game.button.*;
import com.bfg.game.text.*;
import com.bfg.game.*;

public class SceneExtraLevels implements Scene
{
	private volatile static TextTitle title = new TextTitle("Extra",225,150);
	private volatile static TextTitle title1 = new TextTitle("Levels",225,225);
	private volatile static ButtonStandard button = new ButtonStandard("Main Menu",75,350);
	private volatile static ButtonLevel[] level = new ButtonLevel[9];
	private volatile int nextScene = 0;
	private volatile static Semaphore sem = new Semaphore(10,true);
    private volatile int tick = 0;

	public void onCreate() {
		level[0] = new ButtonLevel("1",500,110);
		level[1] = new ButtonLevel("2",600,110);
		level[2] = new ButtonLevel("3",700,110);
		level[3] = new ButtonLevel("4",500,210);
		level[4] = new ButtonLevel("5",600,210);
		level[5] = new ButtonLevel("6",700,210);
		level[6] = new ButtonLevel("7",500,310);
		level[7] = new ButtonLevel("8",600,310);
		level[8] = new ButtonLevel("9",700,310);
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
					MainThread.scene = new SceneMainMenu();
					break;
				case 3:
					MainThread.scene = new SceneMainMenu();
					break;
				case 4:
					MainThread.scene = new SceneMainMenu();
					break;
				case 5:
					MainThread.scene = new SceneMainMenu();
					break;
				case 6:
					MainThread.scene = new SceneMainMenu();
					break;
				case 7:
					MainThread.scene = new SceneMainMenu();
					break;
				case 8:
					MainThread.scene = new SceneMainMenu();
					break;
				case 9:
					MainThread.scene = new SceneMainMenu();
					break;
				case 10:
					MainThread.scene = new SceneMainMenu();
					break;
			}
			MainThread.scene.onCreate();
			MainThread.setLoading(false);
		}
	}
	public void onRender(Canvas canvas) {
        title.draw(canvas);
		title1.draw(canvas);
		button.draw(canvas);
		/*for(int i = 0; i < level.length;i++) {
			if(level[i] != null) {
				level[i].draw(canvas);
			}
		}*/
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
			/*for(int i = 0; i < level.length;i++) {
				if(level[i] != null) {
					if(level[i].checkTouch(x,y)) {
						setNextScene(i+2);
					}
				}
			}*/
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
