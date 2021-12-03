package com.example.myapplication.util;

import android.graphics.Canvas;

import java.util.List;

public class SceneSequence {

    public List<Scene> scenes;
    public int currentScene = 0;

    public SceneSequence(List<Scene> scenes) {
        this.scenes = scenes;
    }

    public void update() {
        scenes.get(currentScene).update();
    }

    public void nextScene() {
        currentScene++;
    }

    public void draw(Canvas canvas) {
        scenes.get(currentScene).draw(canvas);
    }

}
