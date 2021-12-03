package com.example.myapplication;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.SurfaceTexture;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

import androidx.core.content.res.ResourcesCompat;

public class MainActivity extends Activity implements View.OnClickListener, TextureView.SurfaceTextureListener {

    Intent startGame, startCredits;
    Intent startShop;
    SharedPreferences preferences;
    TextureView bg;
    TextView myGold;
    boolean runBg = true;
    ScrollingBackground sbg;
    BackgroundDrawer bgd;
    Bundle b;
    Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bg = findViewById(R.id.backgroundView);
        bg.setSurfaceTextureListener(this);
        b = ActivityOptions.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
        tf = ResourcesCompat.getFont(this, R.font.pixel_font);
        Const.setAppFont(tf);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ImageButton start = findViewById(R.id.start);
        ImageButton shop = findViewById(R.id.shopButton);
        findViewById(R.id.aboutButton).setOnClickListener(this);
        shop.setOnClickListener(this);
        myGold = findViewById(R.id.yourGold);
        myGold.setTypeface(tf, Typeface.NORMAL);
        TextView title = (TextView) findViewById(R.id.game_title);
        title.setTypeface(tf);
        TextView subtitle = (TextView) findViewById(R.id.game_subtitle);
        subtitle.setTypeface(tf);
        TextView highScore = (TextView) findViewById(R.id.highScore);
        highScore.setTypeface(tf);
        start.setOnClickListener(this);
        Const.SCREEN_DENSITY = getResources().getDisplayMetrics().density;
        startGame = new Intent(MainActivity.this, GameActivity.class);
        startShop = new Intent(MainActivity.this, ShopActivity.class);
        startCredits = new Intent(MainActivity.this, CreditsActivity.class);
        preferences = getSharedPreferences(Const.SHARED_PREFERENCES_KEY, MODE_PRIVATE);
        if(preferences.contains(Const.SHARED_PREFERENCES_VALUE_GOLD)) {
            myGold.setText(String.format(Locale.ENGLISH,"%d",preferences.getInt(Const.SHARED_PREFERENCES_VALUE_GOLD, 0)));
        }
        else {
            myGold.setText("0");
        }
        if(preferences.contains(Const.SHARED_PREFERENCES_VALUE_DISTANCE)) {
            highScore.setText(getString(R.string.best_score, preferences.getInt(Const.SHARED_PREFERENCES_VALUE_DISTANCE, 0)));
        }
        else {
            highScore.setVisibility(View.INVISIBLE);
        }
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        sbg = new ScrollingBackground(BitmapFactory.decodeResource(getResources(), R.raw.sky), 1, screenWidth, screenHeight);
        bgd = new BackgroundDrawer(bg, sbg);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bgd.drawBg = false;
        try {
            bgd.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void createBg() {
        Canvas canvas;
        while (runBg) {
            canvas = bg.lockCanvas();
            sbg.update();
            sbg.draw(canvas);
            bg.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                startActivity(startGame, b);
                break;
            case R.id.shopButton:
                startActivity(startShop, b);
                break;
            case R.id.aboutButton:
                startActivity(startCredits, b);
                break;
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        bgd.start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}