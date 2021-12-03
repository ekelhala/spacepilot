package com.example.myapplication.util;

import android.graphics.Canvas;

import java.util.List;

public class Scene {

    private List<SceneObject> objects;
    SceneObject object;
    TextBreaker breaker;

    public Scene() {
        breaker = new TextBreaker();
    }

    public void setContents(List<SceneObject> contents) {
        objects = contents;
    }

    public void draw(Canvas canvas) {
        for(int i = 0; i < objects.size(); i++) {
            object = objects.get(i);
            if(object.getType() == SceneObject.TYPE_IMAGE) {
                canvas.drawBitmap(object.getImageContent(), (float) object.getX(), (float) object.getY(), object.getPaint());
            }
            else if(object.getType() == SceneObject.TYPE_TEXT) {
                breaker.writeWithNewlines(object.getTextContent(), object.getPaint(), (float) object.getX(), (float) object.getY(), canvas);
            }
        }
    }

    public void update() {

    }

    public void clear() {
        object = null;
        objects.clear();
    }

}
