package com.example.myapplication;

import android.graphics.Bitmap;

public class ExhaustSmoke extends GameObject {

    int speed;

    public ExhaustSmoke(float xPos, float yPos, float w, float h, float density, float speed, Bitmap image) {
        super(xPos, yPos, w, h, density, image);
        this.speed = Utils.convertToDevicePixels(density, speed);
    }

    @Override
    public void update() {
        super.update();
        this.x = this.x - speed;
    }

}
