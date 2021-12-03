package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorSpace;
import android.graphics.Paint;

public class ScrollingBackground {

    Bitmap original;
    Bitmap original2;
    int speed;
    int xPosition;
    int xPosition2;
    int screenWidth;
    int xMax;
    Paint p;

    public ScrollingBackground(Bitmap bg, float speed, int screenWidth, int screenHeight) {
        this.speed = Utils.convertToDevicePixels(Const.SCREEN_DENSITY, speed);
        original = Utils.scaleBitmap(bg, screenWidth, screenHeight);
        original2 = original;
        xMax = original.getWidth();
        this.screenWidth = screenWidth;
        xPosition = 0;
        xPosition2 = this.screenWidth;
        p = new Paint();
    }

    public void update() {
        xPosition = xPosition - speed;
        xPosition2 = xPosition2 - speed;
        if(xPosition <= -xMax) {
            xPosition = screenWidth;
        }
        if(xPosition2 <= -xMax) {
            xPosition2 = screenWidth;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(original, xPosition, 0, p);
        canvas.drawBitmap(original2, xPosition2, 0, p);
    }

}
