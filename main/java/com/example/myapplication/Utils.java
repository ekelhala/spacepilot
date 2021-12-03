package com.example.myapplication;

import android.graphics.Bitmap;

public class Utils {

    public static Bitmap scaleBitmap(Bitmap patient, int maxWidth, int maxHeight) {
        int width = patient.getWidth();
        int height = patient.getHeight();
        float ratioBitmap = (float) width / (float) height;
        float ratioMax = (float) maxWidth / (float) maxHeight;

        int finalWidth = maxWidth;
        int finalHeight = maxHeight;
        if(ratioBitmap < ratioMax) {
            finalWidth = (int) ((float)maxHeight * ratioBitmap);
        }
        else {
            finalHeight = (int) ((float)maxWidth /ratioBitmap);
        }
        patient = Bitmap.createScaledBitmap(patient, finalWidth, finalHeight, true);
        return patient;
    }

    public static int convertToDevicePixels(float density, float value) {
        return (int)(value * density + 0.5f);
    }

    public static int convertFromDevicePixels(float density, float value) {
        return (int) (value / density - 0.5f);
    }

}


