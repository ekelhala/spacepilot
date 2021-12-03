package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.myapplication.util.Dimensions;
import com.example.myapplication.util.Point;
import com.example.myapplication.util.ShipPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    public GameThread thread;
    private NewShuttle obj;
    private int screenHeight;
    private int screenWidth;
    private int halfOfSW;
    private int halfOfSH;
    private List<Rock> rocks = new ArrayList<>();
    private List<GoldenRock> goldenRocks = new ArrayList<>();
    private List<ExhaustSmoke> smokes = new ArrayList<>();
    final Random r = new Random();
    int random;
    int rocksCollected = 0;
    int distanceTravelled = 0;
    int counter = 0;
    int border = 60;
    Level currentLevel, nextLevel;
    Paint collectedGold = new Paint();
    Paint goldStroke = new Paint();
    Paint tapToStart = new Paint();
    Paint distance = new Paint();
    Paint cond = new Paint();
    private MenuInterface interfacing;
    boolean playing = false;
    Animation flameAnimation;
    Bitmap[] flameFrames;
    Animation exhaustAnimation;
    Bitmap[] exhaustFrames;
    ShuttlePart wing, body, mainEngine, steeringEngine;
    Timer t;
    int goldGained = 1;
    int goldGainedOnce = 2;
    Bitmap smoke, exhaustSmoke;
    float dPScale, healthBarY;
    ParticleAnimation collision;
    SharedPreferences shipData;
    HealthBar bar;
    int bodyId, wingId, mainMotorId, controlMotorId, uiMarginTop, uiMarginLeft;
    ShipPreferences preferences;
    ScrollingBackground sb;
    String startText, distanceText, conditionText, instructionsText;
    Resources res;
    SoundPool gameAudio;
    private static final float SHIP_WIDTH_DP = 40;
    private static final float SHIP_HEIGHT_DP = 30;
    private static final float ASTEROID_HEIGHT_DP = 25;
    private static final float ASTEROID_WIDTH_DP = 25;
    private int ID_EXPLOSION;

    GameView(Context c, MenuInterface i) {
        super(c);
        interfacing = i;
        getHolder().addCallback(this);
        thread = new GameThread(this, getHolder());
        res = getResources();
        dPScale = getResources().getDisplayMetrics().density;
        Const.SCREEN_DENSITY = dPScale;
        healthBarY = Utils.convertToDevicePixels(dPScale, 20);
        shipData = c.getSharedPreferences(Const.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        startText = c.getString(R.string.tap_to_start);
        instructionsText = c.getString(R.string.instructions);
        distanceText = c.getString(R.string.distance);
        conditionText = c.getString(R.string.condition);
        bodyId = shipData.getInt(Const.SHARED_PREFERENCES_VALUE_MAIN_MODULE_ID, Const.MAIN_MODULE_NORMAL);
        wingId = shipData.getInt(Const.SHARED_PREFERENCES_VALUE_WING_ID, Const.WING_NORMAL);
        mainMotorId = shipData.getInt(Const.SHARED_PREFERENCES_VALUE_MAIN_MOTOR_ID, Const.MAIN_MOTOR_NORMAL);
        controlMotorId = shipData.getInt(Const.SHARED_PREFERENCES_VALUE_CONTROL_UNIT_ID, Const.CONTROL_UNIT_NORMAL);
        preferences = new ShipPreferences(bodyId, mainMotorId, controlMotorId, wingId, new boolean[]{false, false, false, false});
        border = 200/(preferences.getMovementPoints()/10);
        initializeAnimations();
        initializeUi();
        setFocusable(true);
        nextLevel = new Level(20, 6);
        currentLevel = new Level(0, 5);
        t = new Timer();
        gameAudio = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        ID_EXPLOSION = gameAudio.load(getContext(), R.raw.explosion, 1);
    }

    private void initializeAnimations() {
        Resources res = getResources();
        flameFrames = new Bitmap[]{BitmapFactory.decodeResource(res, R.raw.flame1v2), BitmapFactory.decodeResource(res, R.raw.flame2v2)};
        flameAnimation = new Animation(flameFrames, 4, 10, 15);
        flameAnimation.setVisibility(false);
        exhaustFrames = new Bitmap[]{BitmapFactory.decodeResource(res, R.raw.exhaust_flame1v2), BitmapFactory.decodeResource(res, R.raw.exhaust_flame2v2)};
        exhaustAnimation = new Animation(exhaustFrames, 9, 15, 20);
    }

    private void initializeUi() {
        collectedGold.setColor(Color.YELLOW);
        collectedGold.setTextSize(Utils.convertToDevicePixels(Const.SCREEN_DENSITY, 18));
        collectedGold.setTypeface(Const.appFont);
        tapToStart.setColor(Color.WHITE);
        tapToStart.setTextSize(Utils.convertToDevicePixels(Const.SCREEN_DENSITY, 15));
        tapToStart.setTypeface(Const.appFont);
        distance.setTextSize(Utils.convertToDevicePixels(Const.SCREEN_DENSITY, 20));
        distance.setColor(Color.WHITE);
        distance.setTextAlign(Paint.Align.CENTER);
        distance.setTypeface(Const.appFont);
        cond.setTextSize(30);
        cond.setTextAlign(Paint.Align.CENTER);
        cond.setColor(Color.parseColor("lime"));
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        halfOfSW = screenHeight/2;
        halfOfSH = screenWidth/2;
        bar = new HealthBar(preferences.getDurabilityPoints(), 200, 0, 50, 10, 1);
        sb = new ScrollingBackground(BitmapFactory.decodeResource(res, R.raw.sky), ((float) preferences.getMovementPoints()/10)/2, screenWidth, screenHeight);
        body = new ShuttlePart(BitmapFactory.decodeResource(res, preferences.getMainModuleSprite()), SHIP_WIDTH_DP, SHIP_HEIGHT_DP);
        wing = new ShuttlePart(BitmapFactory.decodeResource(res, preferences.getWingSprite()), 10, 8);
        mainEngine = new ShuttlePart(BitmapFactory.decodeResource(res, preferences.getMainMotorSprite()), 15,20);
        steeringEngine = new ShuttlePart(BitmapFactory.decodeResource(res,preferences.getControlUnitSprite()), 13, 8);
        obj = new NewShuttle(wing, mainEngine, body, steeringEngine, 200, 100, (float) preferences.getSteeringPoints()/10, (float) preferences.getBrakingPointsForSystem(), preferences.getDurabilityPoints(),exhaustAnimation, flameAnimation);
        obj.maxY = screenHeight;
        obj.minY = 0;
        smoke = BitmapFactory.decodeResource(res, R.raw.smoke2);
        exhaustSmoke = BitmapFactory.decodeResource(res, R.raw.smoke_sprite);
        collision = new ParticleAnimation(smoke, new Dimensions(30, 30), new Point(0,0), 4, 60, 20);
        uiMarginTop = (int)distance.getTextSize() + Utils.convertToDevicePixels(Const.SCREEN_DENSITY, 10);
        uiMarginLeft = Utils.convertToDevicePixels(Const.SCREEN_DENSITY, 10);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void drawStarting(Canvas c) {
        obj.draw(c);
        c.drawText(startText, 250, 100, tapToStart);
        c.drawText(instructionsText, 230, 200, tapToStart);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    public void startGame() {
        thread.setRunning(true);
        if(!thread.isAlive()) {
            thread.start();
        }
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                smokes.add(new ExhaustSmoke(Utils.convertFromDevicePixels(Const.SCREEN_DENSITY, (float) obj.mainMotor.x), Utils.convertFromDevicePixels(Const.SCREEN_DENSITY, (float) obj.mainMotor.y + (float) obj.mainMotor.height/2),10,10,Const.SCREEN_DENSITY,(float) preferences.getMovementPoints()/10,exhaustSmoke));
            }
        }, 200, 200);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getActionMasked() == MotionEvent.ACTION_BUTTON_PRESS) {
                    performClick();
                }
                else if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        if (!playing) {
                            startGame();
                            playing = true;
                        }
                        obj.setRocketsOn(true);
                }
                else if(event.getActionMasked() == MotionEvent.ACTION_UP) {
                    obj.setRocketsOn(false);
                }
                return true;
            }
        });
            thread.drawStartingScreen();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopGame();
    }

    public void stopGame() {
        t.cancel();
        boolean retry = true;
        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        obj.update();
        collision.update();
        int i;
        if(rocks.size() < currentLevel.spawnFrequency) {
            random = r.nextInt(screenHeight);
            rocks.add(new Rock(screenWidth, random, ASTEROID_WIDTH_DP, ASTEROID_HEIGHT_DP, (float)preferences.getMovementPoints()/10,dPScale, screenWidth, BitmapFactory.decodeResource(getResources(), R.raw.rock2)));
        }
        if(goldenRocks.size() < 2) {
            random = r.nextInt(screenHeight);
            goldenRocks.add(new GoldenRock(screenWidth, random, ASTEROID_WIDTH_DP, ASTEROID_HEIGHT_DP, (float)preferences.getMovementPoints()/10,dPScale, screenWidth, BitmapFactory.decodeResource(getResources(), R.raw.gold2)));
        }
        for(i = 0; i < rocks.size(); i++) {
            if(rocks.get(i).x < -30) {
                rocks.get(i).destroy();
                rocks.remove(i);
            }
            rocks.get(i).update();
        }

        for(i = 0; i < goldenRocks.size(); i++) {
            if(goldenRocks.get(i).x < -30) {
                goldenRocks.get(i).destroy();
                goldenRocks.remove(i);
            }
            goldenRocks.get(i).update();
        }

        for(i = 0; i < rocks.size(); i++) {
            if(obj.checkCollision(rocks.get(i))) {
                collision = new ParticleAnimation(smoke, new Dimensions(30, 30), new Point(Utils.convertFromDevicePixels(Const.SCREEN_DENSITY, (float)rocks.get(i).x), Utils.convertFromDevicePixels(Const.SCREEN_DENSITY, (float)rocks.get(i).y)), 4, 60, 20);
                collision.exists = true;
                gameAudio.play(ID_EXPLOSION, 1, 1, 0, 0, 1);
                if(obj.condition <= 50) {
                    thread.setRunning(false);
                    playing = false;
                    interfacing.onStartGameOverMenu(rocksCollected, distanceTravelled);
                }
                else if(obj.condition <= 100) {
                    obj.hasWing = false;
                    obj.speedDown = obj.speedDown*2;
                    obj.condition = obj.condition - 50;
                    rocks.get(i).destroy();
                    rocks.remove(i);
                    cond.setColor(Color.RED);
                    cond.setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
                }
                else {
                    obj.condition = obj.condition - 50;
                    rocks.get(i).destroy();
                    rocks.remove(i);
                }
            }
        }
        for(i = 0; i < goldenRocks.size(); i++) {
            if(obj.checkCollision(goldenRocks.get(i))) {
                goldenRocks.get(i).destroy();
                goldenRocks.remove(i);
                rocksCollected = rocksCollected + goldGainedOnce;
                goldGained = goldGainedOnce;
            }
        }
        counter++;
        if(counter == border) {
            distanceTravelled++;
            counter = 0;
        }

        if(distanceTravelled > nextLevel.edge) {
            currentLevel = nextLevel;
            nextLevel = new Level(currentLevel.edge + 20, currentLevel.spawnFrequency + 1);
        }
        for(int i1 = 0; i1 < smokes.size(); i1++) {
            if(smokes.get(i1).x < -30) {
                smokes.get(i1).destroy();
                smokes.remove(i1);
            }
            else {
                smokes.get(i1).update();
            }
        }
        bar.setX(obj.x);
        bar.setY(obj.y - (int) healthBarY);
        bar.update(obj.condition);
        sb.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        sb.draw(canvas);
            int i;
            for (i = 0; i < smokes.size(); i++) {
                smokes.get(i).drawObject(canvas);
            }
            obj.draw(canvas);
            collision.draw(canvas);
            for (i = 0; i < rocks.size(); i++) {
                rocks.get(i).drawObject(canvas);
            }
            for (i = 0; i < goldenRocks.size(); i++) {
                goldenRocks.get(i).drawObject(canvas);
            }
            canvas.drawText(Integer.toString(rocksCollected), uiMarginLeft, uiMarginTop, collectedGold);
            canvas.drawText(distanceTravelled + " km", halfOfSH, uiMarginTop, distance);
            bar.draw(canvas);
    }

}
