package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.concurrent.CopyOnWriteArrayList;

public class ShuttlePart {

    Bitmap sprite;
    int x;
    int y;
    int width;
    int height;
    Rect hitbox;
    Paint p;
    private float dens;

    public ShuttlePart(Bitmap img, float w, float h) {
        sprite = Utils.scaleBitmap(img, Utils.convertToDevicePixels(Const.SCREEN_DENSITY, w), Utils.convertToDevicePixels(Const.SCREEN_DENSITY, h));
        width = sprite.getWidth();
        height = sprite.getHeight();
        p = new Paint();
        dens = Const.SCREEN_DENSITY;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        hitbox = new Rect(this.x, this.y, this.x + width, this.y + height);
    }

    public void update(int xPos, int yPos) {
        x = xPos;
        y = yPos;
        hitbox.top = y;
        hitbox.left = x;
        hitbox.bottom = y + height;
        hitbox.right = x + width;
    }

    public boolean collides(GameObject g) {
        return Rect.intersects(hitbox, g.hitbox);
    }

    public void draw(Canvas target) {
        target.drawBitmap(sprite, x, y, p);
    }

    public void destroy() {
        sprite.recycle();
    }

}
