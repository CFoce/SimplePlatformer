package com.bfg.game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import android.graphics.*;

import com.bfg.game.*;
import com.bfg.game.scene.*;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    private static final int WIDTH = 848; // Canvas width
    private static final int HEIGHT = 480;// Canvas height
    private MainThread thread; // Main thread holder
	private static Scene loading = new SceneLoading(); // Game Loading scene

    public GamePanel(Context context)
    {
        super(context);
        //Adds the callback to the surfaceholder to get events
        getHolder().addCallback(this);
		//Sets window focusable for event handling
        setFocusable(true);
    }

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

	@Override
    public void surfaceDestroyed(SurfaceHolder holder){
		boolean retry = true;
		int counter = 0;
		while(retry && counter<1000)
		{
			counter++;
			try{thread.setRunning(false);
				thread.join();
				retry = false;
				thread = null;
			}catch(InterruptedException e){e.printStackTrace();}
		}
	}

	@Override
    public void surfaceCreated(SurfaceHolder holder){
		// Creates the main thread
		thread = new MainThread(getHolder(), this);
		// Starts the main thread
		thread.setRunning(true);
		thread.start();
		// Start asynchronous update thread
		(new Thread(new UpdateThread())).start();
	}
	
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
		// Checks if the game is in loading state
		if(!MainThread.isLoading()) {
			for (int i = 0; i < event.getPointerCount(); i++) {
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
					case MotionEvent.ACTION_DOWN:
					case MotionEvent.ACTION_POINTER_DOWN:
						MainThread.scene.onListener(true,(int)(event.getX(i)/(getWidth()/(WIDTH*1.f))),(int)(event.getY(i)/(getHeight()/(HEIGHT*1.f))));
						break;
					case MotionEvent.ACTION_MOVE:
						MainThread.scene.onListener(true,(int)(event.getX(i)/(getWidth()/(WIDTH*1.f))),(int)(event.getY(i)/(getHeight()/(HEIGHT*1.f))));
						break;
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_POINTER_UP:
						MainThread.scene.onListener(false,(int)(event.getX(i)/(getWidth()/(WIDTH*1.f))),(int)(event.getY(i)/(getHeight()/(HEIGHT*1.f))));
						break;
				}
			}
			return true;
		}
        return super.onTouchEvent(event);
    }
    
    @Override
    public void draw(Canvas canvas)
    {
		// Scales the canvas in both width and height
        final float scaleWidth = getWidth()/(WIDTH*1.f);
        final float scaleHeight = getHeight()/(HEIGHT*1.f);
		// Checks if canvas exists
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleWidth, scaleHeight);
            // Checks if game is in loading state, then renders
			if(!MainThread.isLoading()) {
				MainThread.scene.onRender(canvas);
			} else {
				GamePanel.loading.onRender(canvas);
			}
			// Checks if developer mode is on, then renders
			if(MainThread.isDevMode()){
				drawDev(canvas);
			}
            canvas.restoreToCount(savedState);
        }
    }

	/**
	* Paints frames per second, ticks per second, overall ticks, and that the game is in developer mode.
	*
	* @param  canvas The object to draw too.
	*/
    public void drawDev(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(15);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("FPS: " + MainThread.averageFPS, 10, 15, paint);
        canvas.drawText("Ticks: " + UpdateThread.getTicks(), 160, 15, paint);
		canvas.drawText("TPS: " + UpdateThread.getTPS(), 85, 15, paint);
		canvas.drawText("Dev Mode", WIDTH-75, 15, paint);
    }
}
