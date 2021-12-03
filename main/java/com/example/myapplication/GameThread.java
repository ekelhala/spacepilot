package com.example.myapplication;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import static java.lang.Math.abs;

public class GameThread extends Thread {

    private GameView v;
    private SurfaceHolder h;
    private boolean isRunning;
    public static Canvas canvas;
    private int targetFps = 60;
    private long cycleStart, cycleEnd, waitTime;
    private long targetTime = 1000/targetFps;

    public GameThread(GameView view, SurfaceHolder holder) {
        super();
        v = view;
        h = holder;
    }

    public void drawStartingScreen() {
        canvas = null;
        try {
            canvas = h.lockCanvas();
            v.drawStarting(canvas);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(canvas != null) {
                try {
                    h.unlockCanvasAndPost(canvas);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void run() {
        while(isRunning) {
            cycleStart = System.nanoTime();
            canvas = null;

            try {
                canvas = h.lockCanvas();
                    v.update();
                    v.draw(canvas);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            finally {
                if(canvas != null) {
                    try {
                        h.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            cycleEnd = (System.nanoTime() - cycleStart) / 1000000;
            waitTime = abs(targetTime - cycleEnd);
            try {
                if(waitTime > 0) {
                    sleep(waitTime);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setRunning(boolean r) {
        isRunning = r;
    }

}
