package com.example.myapplication.util;


import android.graphics.Bitmap;
import android.graphics.Paint;

import com.example.myapplication.Const;
import com.example.myapplication.Utils;

public class SceneObject {

    private Bitmap bitmapContent;
    private String textContent;
    private int x;
    private int y;
    private int width;
    private int height;
    private int mode;
    private float textSize;
    private Paint paint;

    public static final int TYPE_IMAGE = 0;
    public static final int TYPE_TEXT = 1;


    public SceneObject(int mode) {
        this.mode = mode;
    }

    public int getType() {
        return mode;
    }


    public void setContent(Bitmap content) {
        this.bitmapContent = content;
    }

    public void setContent(String content) {
        this.textContent = content;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Bitmap getImageContent() {
        return bitmapContent;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextSize(float textSize) {
        this.textSize = (float) Utils.convertToDevicePixels(Const.SCREEN_DENSITY, textSize);
    }

    public float getTextSize() {
        return textSize;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setDimensions(int width, int height) {
        if(this.mode == TYPE_IMAGE) {
            int bmWidth = Utils.convertToDevicePixels(Const.SCREEN_DENSITY, (float) width);
            int bmHeight = Utils.convertToDevicePixels(Const.SCREEN_DENSITY, (float) height);
            bitmapContent = Utils.scaleBitmap(bitmapContent, bmWidth, bmHeight);
            this.width = bitmapContent.getWidth();
            this.height = bitmapContent.getHeight();
        }
    }

    public SceneObject copy(SceneObject to) {
        to.setContent(this.textContent);
        to.setX(this.x);
        to.setY(this.y);
        to.setPaint(this.paint);
        return to;
    }
}
