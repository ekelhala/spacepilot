package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.myapplication.util.Dimensions;
import com.example.myapplication.util.IPoint;
import com.example.myapplication.util.Point;

import java.util.ArrayList;
import java.util.List;

public class ParticleAnimation {

    private Bitmap particle;
    int startX;
    int startY;
    int width;
    int height;
    int speed;
    int duration;
    int ticker;
    int time;
    int relativeSpeed;
    List<IPoint> particles = new ArrayList<>();
    Paint p;
    boolean exists = false;

    //Create an explosion of particles travelling to all directions from a starting point (x,y)
    public ParticleAnimation(Bitmap particle, Dimensions particleDimensions, Point startingPoint,  int speed, int duration, int relativeSpeed) {
        this.height = Utils.convertToDevicePixels(Const.SCREEN_DENSITY, particleDimensions.getHeight());
        this.width = Utils.convertToDevicePixels(Const.SCREEN_DENSITY, particleDimensions.getWidth());
        this.particle = Utils.scaleBitmap(particle, width, height);
        this.startX = Utils.convertToDevicePixels(Const.SCREEN_DENSITY, startingPoint.getX());
        this.startY = Utils.convertToDevicePixels(Const.SCREEN_DENSITY, startingPoint.getY());
        this.speed = speed;
        this.duration = duration;
        this.relativeSpeed = Utils.convertToDevicePixels(Const.SCREEN_DENSITY, (float)relativeSpeed);
        for(int i = 0; i < 8; i++) {
            particles.add(new IPoint(startX, startY));
        }
        p = new Paint();
    }

    public void reset(Point newStart) {
        this.startX = Utils.convertToDevicePixels(Const.SCREEN_DENSITY, newStart.getX());
        this.startY = Utils.convertToDevicePixels(Const.SCREEN_DENSITY, newStart.getY());
        particles.clear();
        for(int i = 0; i < 8; i++) {
            particles.add(new IPoint(startX, startY));
        }
        exists = true;
    }

    public void update() {
        if(exists) {
            time++;
            if (time == duration) {
                exists = false;
            } else {
                ticker++;
                if (ticker == speed) {
                    particles.get(0).setY(particles.get(0).getY() - speed);
                    particles.get(0).setX(particles.get(0).getX() - relativeSpeed);
                    particles.get(1).setY(particles.get(1).getY() - speed);
                    particles.get(1).setX(particles.get(1).getX() - speed -relativeSpeed);
                    particles.get(2).setX(particles.get(2).getX() - speed -relativeSpeed);
                    particles.get(3).setX(particles.get(3).getX() - speed -relativeSpeed);
                    particles.get(3).setY(particles.get(3).getY() + speed);
                    particles.get(4).setY(particles.get(4).getY() + speed);
                    particles.get(4).setX(particles.get(4).getX() - relativeSpeed);
                    particles.get(5).setY(particles.get(5).getY() + speed);
                    particles.get(5).setX(particles.get(5).getX() + speed -relativeSpeed);
                    particles.get(6).setX(particles.get(6).getX() + speed -relativeSpeed);
                    particles.get(7).setX(particles.get(7).getX() + speed -relativeSpeed);
                    particles.get(7).setY(particles.get(7).getY() - speed);
                    ticker = 0;
                }
            }
        }
    }

    public void draw(Canvas c) {
        if(exists) {
            for (int i = 0; i < particles.size(); i++) {
                c.drawBitmap(particle, (float) particles.get(i).getX(), (float) particles.get(i).getY(), p);
            }
        }
    }

}
