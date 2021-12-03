package com.example.myapplication;

import android.graphics.Bitmap;

public class PartPreview extends ShuttlePart {


    public PartPreview(Bitmap img, float w, float h, float x, float y, float density) {
        super(img, w, h);
        this.x = Utils.convertToDevicePixels(density, x);
        this.y = Utils.convertToDevicePixels(density, y);
    }

    public void setSprite(Bitmap value) {
        this.sprite = Utils.scaleBitmap(value, this.width, this.height);
    }

}
