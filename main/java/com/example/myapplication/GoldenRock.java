package com.example.myapplication;

import android.graphics.Bitmap;

public class GoldenRock extends GameObject {

    private int speed;

    public GoldenRock(float xPos, float yPos, float w, float h, float speed, float density, int screenWidth, Bitmap image) {
        super(xPos, yPos, w, h,density, image);
        this.speed = Utils.convertToDevicePixels(density, speed);
        x = screenWidth;
    }

    @Override
    public void update() {
        x = x - speed;
        super.update();
    }

}
