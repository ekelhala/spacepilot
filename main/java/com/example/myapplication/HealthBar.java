package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class HealthBar {

    private int maxHealth;
    private int currentHealth;
    private int width;
    private int height;
    private int x, y;
    private Rect border, filler;
    private Paint borderPaint, fillerPaint, pointPaint;
    float multiplier;

    public HealthBar(int maxHealth, float x, float y, float width, float height, float borderThickness) {
        this.maxHealth = maxHealth;
        this.currentHealth = this.maxHealth;
        this.x = Utils.convertToDevicePixels(Const.SCREEN_DENSITY, x);
        this.y = Utils.convertToDevicePixels(Const.SCREEN_DENSITY, y);
        this.width = Utils.convertToDevicePixels(Const.SCREEN_DENSITY, width);
        this.height = Utils.convertToDevicePixels(Const.SCREEN_DENSITY, height);
        multiplier = (float) this.width/(float) this.maxHealth;
        border = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
        filler = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
        borderPaint = new Paint();
        borderPaint.setColor(Color.WHITE);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderThickness);
        fillerPaint = new Paint();
        fillerPaint.setColor(Color.GREEN);
        fillerPaint.setStyle(Paint.Style.FILL);
        pointPaint = new Paint();
        pointPaint.setColor(Color.WHITE);
        pointPaint.setTextSize(15);
        pointPaint.setTextAlign(Paint.Align.RIGHT);
        pointPaint.setTypeface(Const.appFont);
    }

    public void update(int health) {
        this.currentHealth = health;
        border.top = y;
        border.bottom = y + height;
        filler.top = y;
        filler.bottom = y + height;
    }

    public void draw(Canvas target) {
        float ch = (float) currentHealth;
        float mh = (float) maxHealth;
        float prog = this.width * (ch / mh);
        int progress = Math.round(prog);
        filler.right = filler.left + progress;
        target.drawRect(border, borderPaint);
        target.drawRect(filler, fillerPaint);
        target.drawText(Integer.toString(currentHealth), (float) this.x - 5, (float) this.y + pointPaint.getTextSize(), pointPaint);
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

}
