package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class GameObject {

    int x;
    int y;
    int width;
    int height;
    Bitmap sprite;
    Rect hitbox;
    Paint p, hb;
    boolean drawHitbox = false;
    
    public GameObject(float xPos, float yPos, float w, float h, float density, Bitmap image) {
        x = Utils.convertToDevicePixels(density, xPos);
        y = Utils.convertToDevicePixels(density, yPos);
        width = Utils.convertToDevicePixels(density,w);
        height = Utils.convertToDevicePixels(density,h);
        sprite = Utils.scaleBitmap(image, width, height);
        hitbox = new Rect(x, y, x+sprite.getWidth(), y+sprite.getHeight());
        p = new Paint();
        hb = new Paint();
        hb.setStrokeWidth(3);
        hb.setStyle(Paint.Style.STROKE);
        hb.setColor(Color.RED);
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void drawObject(Canvas canvas) {
        canvas.drawBitmap(sprite, x, y, p);
        if(drawHitbox) {
            canvas.drawRect(hitbox, hb);
        }
    }

    public void update() {
        hitbox.top = y;
        hitbox.left = x;
        hitbox.bottom = y + height;
        hitbox.right = x + width;

    }

    public void destroy() {
        sprite.recycle();
    }

}
