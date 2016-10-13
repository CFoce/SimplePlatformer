package com.bfg.game;

import java.util.concurrent.Semaphore;

import com.bfg.game.*;
import com.bfg.game.scene.*;

public class UpdateThread implements Runnable{
	
	private volatile static int ticks = 0; // Total number of ticks
	private volatile static boolean pause = false; // Boolean of paused or not
	private volatile static Semaphore tick = new Semaphore(100,true);
	private volatile static double averageTPS = 0; // Average ticks per second
	
	@Override
	public void run()
	{
		// Variables to help with tick counting and tick management
		long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int tickCount = 0;
        long targetTime = 1000/60;
		// Main update loop
		while(MainThread.isRunning()) {
			startTime = System.nanoTime();
			//updateInput();
			if(!isPause()) {
				update();
			}
			timeMillis = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime-timeMillis;
			// Try sleeping the amount of time to get 60 ticks per second
			try{
				Thread.sleep(waitTime);
			}catch(Exception e){}
			if(!isPause()) {
				// Add ticks and get information ready for
			    totalTime += System.nanoTime()-startTime;
				tickCount++;
				addTicks();
				// At 60 ticks calculate the ticks per second
				if(tickCount == 60)
				{
					setTPS(1000/((totalTime/tickCount)/1000000));
					tickCount = 0;
					totalTime = 0;
				}
			}
		}
	}
	
	/**
	 * This function performs the secondary update
	 */
	private void update() {
		MainThread.scene.onUpdate();
	}
	
	/**
	 * This function performs the main update
	 */
	private void updateInput() {
		//MainThread.scene.onUpdate();
	}
	
	/**
	 * This function sets pause to true
	 */
	public static void pause() {
		try {
			tick.acquire(100);
			pause = true;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			tick.release(100);
		}
	}

	/**
	 * This function sets pause to false
	 */
	public static void unPause() {
		try {
			tick.acquire(100);
			pause = false;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			tick.release(100);
		}
	}

	/**
	 * This function will return the variable pause as true or false
	 *
	 * @return  pause This is the true or false of whether or not the program is paused
	 */
	public static boolean isPause() {
		try {
			tick.acquire();
			return pause;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			tick.release();
		}
		return false;
	}

	/**
	 * This function will return the variable averageTPS
	 *
	 * @return  averageTPS This is the number of ticks per second
	 */
	public static double getTPS() {
		try {
			tick.acquire();
			return averageTPS;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			tick.release();
		}
		return -1;
	}

	/**
	 * This function can set the variable averageTPS to the parameter localTPS
	 *
	 * @param  localTPS The parameter can be set to any number
	 */
	public static void setTPS(double localTPS) {
		try {
			tick.acquire(100);
			averageTPS = localTPS;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			tick.release(100);
		}
	}

	/**
	 * This function will return the variable ticks
	 *
	 * @return  ticks This is the number of overall ticks
	 */
	public static int getTicks() {
		try {
			tick.acquire();
			return ticks;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			tick.release();
		}
		return -1;
	}

	/**
	 * This function can set the variable ticksto the parameter localTicks
	 *
	 * @param  localTicks The parameter can be set to any number
	 */
	public static void setTicks(int localTicks) {
		try {
			tick.acquire(100);
			ticks = localTicks;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			tick.release(100);
		}
	}

	/**
	 * This function adds 1 to the variable ticks
	 */
	private static void addTicks() {
		try {
			tick.acquire(100);
			++ticks;
		} catch(InterruptedException e) {
			System.err.println (e.getMessage());
		} finally {
			tick.release(100);
		}
	}
	
}
