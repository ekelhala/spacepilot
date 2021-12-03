package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class NewShuttle {

    ShuttlePart wing, mainMotor, body, secondaryMotor;
    int x, y, speedUp, speedDown;
    int maxY, minY;
    private Animation mainMotorAnim;
    private Animation secondaryMotorAnim;
    private boolean rocketsOn, showExhaustAnim;
    int condition;
    boolean hasWing;
    Paint hBox = new Paint();
    boolean drawHitboxes = false;
    float dens;

    public NewShuttle(ShuttlePart wing, ShuttlePart mainMotor, ShuttlePart body, ShuttlePart secondaryMotor, float x, float y, float speedUp,float speedDown ,int condition, Animation mainMotorAnim, Animation secondaryMotorAnim) {
        this.wing = wing;
        this.mainMotor = mainMotor;
        this.body = body;
        this.secondaryMotor = secondaryMotor;
        this.x = Utils.convertToDevicePixels(x, Const.SCREEN_DENSITY);
        this.y = Utils.convertToDevicePixels(y, Const.SCREEN_DENSITY);
        this.speedUp = Utils.convertToDevicePixels(speedUp, Const.SCREEN_DENSITY);
        this.speedDown = Utils.convertToDevicePixels(speedDown, Const.SCREEN_DENSITY);
        this.mainMotorAnim = mainMotorAnim;
        this.secondaryMotorAnim = secondaryMotorAnim;
        showExhaustAnim = false;
        hasWing = true;
        this.condition = condition;
        hBox.setColor(Color.parseColor("lime"));
        hBox.setStyle(Paint.Style.STROKE);
        hBox.setStrokeWidth(3);
        positionParts(this.x, this.y);
    }

    void positionParts(int x, int y) {
        body.setPosition(x,y);
        wing.setPosition(body.x + body.width/4, body.y - wing.height);
        mainMotor.setPosition(body.x - mainMotor.width, body.y + (body.height/2 - mainMotor.height/2));
        secondaryMotor.setPosition(x + body.width/4, y + body.height);
        mainMotorAnim.positionAnimation(mainMotor.x - mainMotorAnim.getWidth(), mainMotor.y);
        secondaryMotorAnim.positionAnimation(secondaryMotor.x + (secondaryMotor.width/2 - secondaryMotorAnim.getWidth()/2), secondaryMotor.y + secondaryMotor.height);
    }

    public void setRocketsOn(boolean val) {
        rocketsOn = val;
    }

    public void setShowExhaustAnim(boolean value) {
        showExhaustAnim = value;
    }

    public void setX(int value) {
        x = value;
    }

    public void setY(int value) {
        y = value;
    }

    public void update() {
        if(rocketsOn) {
            if(y > minY) {
                y = y - speedUp;
                secondaryMotorAnim.setVisibility(true);
                mainMotorAnim.update(mainMotorAnim.getX(), mainMotorAnim.getY() - speedUp);
                secondaryMotorAnim.update(secondaryMotorAnim.getX(), secondaryMotorAnim.getY() - speedUp);
                body.update(x, y);
                wing.update(wing.x, wing.y - speedUp);
                mainMotor.update(mainMotor.x, mainMotor.y - speedUp);
                secondaryMotor.update(secondaryMotor.x, secondaryMotor.y - speedUp);
            }
            else {
                secondaryMotorAnim.update(secondaryMotorAnim.getX(), secondaryMotorAnim.getY());
                mainMotorAnim.update(mainMotorAnim.getX(), mainMotorAnim.getY());
            }
        }
        else {
            if(y < maxY) {
                y = y + speedDown;
                secondaryMotorAnim.setVisibility(false);
                mainMotorAnim.update(mainMotorAnim.getX(), mainMotorAnim.getY() + speedDown);
                secondaryMotorAnim.update(secondaryMotorAnim.getX(), secondaryMotorAnim.getY() + speedDown);
                body.update(x, y);
                wing.update(wing.x, wing.y + speedDown);
                mainMotor.update(mainMotor.x, mainMotor.y + speedDown);
                secondaryMotor.update(secondaryMotor.x, secondaryMotor.y + speedDown);
            }
            else {
                secondaryMotorAnim.setVisibility(false);
                mainMotorAnim.update(mainMotorAnim.getX(), mainMotorAnim.getY());
            }
        }
    }

    public boolean checkCollision(GameObject g) {
        if(wing.collides(g) || mainMotor.collides(g) || body.collides(g) || secondaryMotor.collides(g)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void draw(Canvas c) {
        if(hasWing) {
            wing.draw(c);
        }
        mainMotor.draw(c);
        body.draw(c);
        secondaryMotor.draw(c);
        secondaryMotorAnim.drawAnimation(c);
        mainMotorAnim.drawAnimation(c);
        if(drawHitboxes) {
            c.drawRect(wing.hitbox, hBox);
            c.drawRect(mainMotor.hitbox, hBox);
            c.drawRect(body.hitbox, hBox);
            c.drawRect(secondaryMotor.hitbox, hBox);
        }
    }

}
