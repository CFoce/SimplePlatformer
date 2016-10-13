package com.bfg.game;

import android.graphics.*;
import android.view.SurfaceHolder;
import java.util.concurrent.Semaphore;

import com.bfg.game.*;
import com.bfg.game.scene.*;

public class MainThread extends Thread
{
    public static double averageFPS; // Average frames per second, not thread safe
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private volatile static boolean running = false; // Program running boolean
    public static Canvas canvas; // Main window canvas
	private volatile static boolean fpsCap = true; // Frame rate cap boolean
	private volatile static boolean loading = false; // Game loading boolean
	private volatile static boolean devMode = false; // Developer mode boolean
	private volatile static Semaphore main = new Semaphore(10,true);
	public volatile static Scene scene = new SceneStartUp(); // Main scene holder, not thread safe

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }
	
    @Override
    public void run()
    {
		// Variables for frame rate capping and measuring
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount =0;
        long targetTime = 1000/31;//31
		// Main renderer loop
        while(isRunning()) {
            startTime = System.nanoTime();
            canvas = null;
            //try locking the canvas for editing
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
					// Set a base background color for the canvas
					canvas.drawColor(Color.WHITE);
					// Performs the main render
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e) {
            }
            finally{
                if(canvas!=null)
                {
					// Relock the canvas
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }
			// Checks if FPS cap is active and if so activates the cap
			if(isFPSCap()) {
				timeMillis = (System.nanoTime() - startTime) / 1000000;
				waitTime = targetTime-timeMillis;
                try{
                    this.sleep(waitTime);
                }catch(Exception e){}
			}
			// Calculates frames per second
            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == 31) // Set to 31 frames, as it always will loose 1 frame
            {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount =0;
                totalTime = 0;
            }
        }
    }
	
	/**
	 * This function can set the variable running to true or false
	 *
	 * @param  run The true or false if the program is running or not
	 */
    public static void setRunning(boolean run)
    {
		try {
			main.acquire(10);
			running = run;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			main.release(10);
		}
    }
	
	/**
	 * This function will return the variable running as true or false
	 *
	 * @return  running This is the true or false of whether or not the program is running
	 */
	public static Boolean isRunning() {
		try {
			main.acquire();
			return running;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			main.release();
		}
		
		return true;
	}
	
	/**
	 * This function can set the variable loading to true or false
	 *
	 * @param  load The true or false if the program should be in a loading state or not
	 */
	public static void setLoading(boolean load)
    {
		try {
			main.acquire(10);
			loading = load;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			main.release(10);
		}
    }

	/**
	 * This function will return the variable loading as true or false
	 *
	 * @return  loading This is the true or false of whether or not the program is loading
	 */
	public static Boolean isLoading() {
		try {
			main.acquire();
			return loading;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			main.release();
		}

		return true;
	}
	
	/**
	 * This function can set the variable devMode to true or false
	 *
	 * @param  mode The true or false if the program should be in developer mode or not
	 */
	public static void setDevMode(boolean mode)
    {
		try {
			main.acquire(10);
			devMode = mode;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			main.release(10);
		}
    }

	/**
	 * This function will return the variable devMode as true or false
	 *
	 * @return  devMode This is the true or false of whether or not the program is in developer mode
	 */
	public static Boolean isDevMode() {
		try {
			main.acquire();
			return devMode;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			main.release();
		}
		return false;
	}
	
	/**
	 * This function can set the variable fpsCap to true or false
	 *
	 * @param  cap The true or false if the program should be using a frame cap or not
	 */
	public static void setFPSCap(boolean cap)
    {
		try {
			main.acquire(10);
			fpsCap = cap;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			main.release(10);
		}
    }

	/**
	 * This function will return the variable fpsCap as true or false
	 *
	 * @return  fpsCap This is the true or false of whether or not the program has a frame cap
	 */
	public static Boolean isFPSCap() {
		try {
			main.acquire();
			return fpsCap;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			main.release();
		}
		return true;
	}
}
