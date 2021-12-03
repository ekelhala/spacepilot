package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

public class GameActivity extends Activity implements MenuInterface {

    Intent i;
    GameView g;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        g = new GameView(this, this);
        setContentView(g);
    }

    @Override
    protected void onStart() {
        super.onStart();
        i = new Intent(GameActivity.this, MenuActivity.class);
        b = ActivityOptions.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        g.stopGame();
        finish();
    }

    @Override
    public void onStartGameOverMenu(int s, int distance) {
        i.putExtra(Const.INTENT_KEY_SCORE, s);
        i.putExtra(Const.INTENT_KEY_DISTANCE, distance);
        startActivity(i, b);
        finish();
    }
}