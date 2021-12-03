package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Animation {

    private Bitmap[] frameArray;
    private int animSpeed;
    private int ticker = 0;
    private int currentState = 0;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean visible = true;
    private Paint p;

    public Animation(Bitmap[] frames, int speed, float width, float height) {
        for(int i = 0; i < frames.length; i++) {
           frames[i] = Utils.scaleBitmap(frames[i], Utils.convertToDevicePixels(Const.SCREEN_DENSITY, width), Utils.convertToDevicePixels(Const.SCREEN_DENSITY, height));
        }
        this.width = frames[0].getWidth();
        this.height = frames[0].getHeight();
        frameArray = frames;
        animSpeed = speed;
        p = new Paint();
    }

   public void positionAnimation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setVisibility(boolean val) {
        visible = val;
    }

    public void update(int nX, int nY) {
        x = nX;
        y = nY;
        ticker++;
        if(ticker == animSpeed) {
            ticker = 0;
            currentState++;
        }
        if(currentState > frameArray.length-1) {
            currentState = 0;
        }
    }

    public void drawAnimation(Canvas canvas) {
        if(visible) {
            canvas.drawBitmap(frameArray[currentState], x, y, p);
        }
    }

}
