package com.example.myapplication;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MenuActivity extends Activity implements View.OnClickListener {

    Intent startGame;
    SharedPreferences pref;
    TextView scoreView;
    TextView distanceView;
    TextView highScoreView, bonusView;
    ImageButton retryButton;
    ImageButton menuButton;
    Intent startMenu;
    Intent starter;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameove_screen);
        starter = getIntent();
        startMenu = new Intent(MenuActivity.this, MainActivity.class);
        startGame = new Intent(MenuActivity.this, GameActivity.class);
        b = ActivityOptions.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        pref = getSharedPreferences(Const.SHARED_PREFERENCES_KEY, MODE_PRIVATE);
        int score = starter.getIntExtra(Const.INTENT_KEY_SCORE, 0);
        int distance = starter.getIntExtra(Const.INTENT_KEY_DISTANCE, 0);
        scoreView = findViewById(R.id.scoreDisplay);
        scoreView.setTypeface(Const.appFont);
        distanceView = findViewById(R.id.gameover);
        distanceView.setTypeface(Const.appFont);
        highScoreView = findViewById(R.id.highScoreView);
        highScoreView.setTypeface(Const.appFont);
        bonusView = (TextView) findViewById(R.id.bonusText);
        bonusView.setTypeface(Const.appFont);
        if(checkHighScore(distance)) {
            highScoreView.setVisibility(View.VISIBLE);
            bonusView.setVisibility(View.VISIBLE);
            score += 20;
        }
        else {
            highScoreView.setVisibility(View.INVISIBLE);
            bonusView.setVisibility(View.INVISIBLE);
        }
        TextView scoreHint = (TextView) findViewById(R.id.scoreExp);
        scoreHint.setTypeface(Const.appFont);
        String scoreStr = String.valueOf(score);
        distanceView.setText(getString(R.string.distance_travelled, distance));
        scoreView.setText(scoreStr);
        retryButton = findViewById(R.id.retry_button);
        retryButton.setOnClickListener(this);
        menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(this);
        storeValues(score, distance);
    }

    private boolean checkHighScore(int dist) {
        if(pref.contains(Const.SHARED_PREFERENCES_VALUE_DISTANCE)) {
            if(dist > pref.getInt(Const.SHARED_PREFERENCES_VALUE_DISTANCE, 0)) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return true;
        }
    }

    private void storeValues(int gold, int dist) {
        SharedPreferences.Editor edit = pref.edit();
        if(pref.contains(Const.SHARED_PREFERENCES_VALUE_GOLD)) {
            int current = pref.getInt(Const.SHARED_PREFERENCES_VALUE_GOLD, 0);
            edit.putInt(Const.SHARED_PREFERENCES_VALUE_GOLD, current + gold);
        }
        else {
            edit.putInt(Const.SHARED_PREFERENCES_VALUE_GOLD, gold);
        }
        if(checkHighScore(dist)) {
            edit.putInt(Const.SHARED_PREFERENCES_VALUE_DISTANCE, dist);
        }
        edit.apply();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.retry_button:
                startActivity(startGame, b);
                finish();
                break;
            case R.id.menu_button:
                startActivity(startMenu, b);
                finish();
                break;
        }

    }


}