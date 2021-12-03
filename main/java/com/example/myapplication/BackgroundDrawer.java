package com.example.myapplication;

import android.graphics.Canvas;
import android.view.TextureView;

public class BackgroundDrawer extends Thread {

    public boolean drawBg;
    TextureView view;
    Canvas canvas;
    ScrollingBackground background;
    long start, end, waitTime;
    long targetTime  = 1000/60;

    public BackgroundDrawer(TextureView target, ScrollingBackground bg) {
        super();
        drawBg = true;
        view = target;
        background = bg;
    }

    @Override
    public void run() {
        while (drawBg) {
            start = System.nanoTime();
            canvas = view.lockCanvas();
            background.update();
            background.draw(canvas);
            view.unlockCanvasAndPost(canvas);
            end = (System.nanoTime() - start)/1000000;
            waitTime = targetTime - end;
            try {
                sleep(waitTime);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
