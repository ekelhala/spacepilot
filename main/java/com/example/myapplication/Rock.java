package com.example.myapplication;

import android.graphics.Bitmap;

public class Rock extends GameObject {

    private int speed;

    public Rock(float xPosition, float yPosition, float w, float h, float speed, float density, int screenWdth, Bitmap image) {
        super(xPosition, yPosition, w, h, density, image);
        this.speed = Utils.convertToDevicePixels(density, speed);
        x = screenWdth;
    }

    @Override
    public void update() {
        x = x - speed;
        super.update();
    }

}
