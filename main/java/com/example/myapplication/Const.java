package com.example.myapplication;

import android.graphics.Typeface;

public class Const {

    //Constants for data storing
    public static final String SHARED_PREFERENCES_KEY = "gameData";
    public static final String SHARED_PREFERENCES_VALUE_GOLD = "goldAmount";
    public static final String INTENT_KEY_SCORE = "score";
    public static final String INTENT_KEY_DISTANCE = "distance";
    public static final String SHARED_PREFERENCES_VALUE_DISTANCE = "distanceAmount";
    public static final String SHARED_PREFERENCES_VALUE_MAIN_MOTOR_ID = "mainMotor";
    public static final String SHARED_PREFERENCES_VALUE_CONTROL_UNIT_ID = "controlUnit";
    public static final String SHARED_PREFERENCES_VALUE_MAIN_MODULE_ID = "mainModule";
    public static final String SHARED_PREFERENCES_VALUE_WING_ID = "wing";
    public static final String MAIN_MODULE_HEAVY_OWNED = "isHeavyMainModule";
    public static final String MAIN_MOTOR_HEAVY_OWNED = "isHeavyMainMotor";
    public static final String CONTROL_UNIT_HEAVY_OWNED = "isHeavyControlUnit";
    public static final String WING_HEAVY_OWNED = "isHeavyWing";

    //Constants for spacecraft part identification
    public static final int MAIN_MOTOR_NORMAL = 10;
    public static final int MAIN_MOTOR_HEAVY = 11;

    public static final int CONTROL_UNIT_NORMAL = 20;
    public static final int CONTROL_UNIT_HEAVY = 21;

    public static final int MAIN_MODULE_NORMAL = 30;
    public static final int MAIN_MODULE_HEAVY = 31;

    public static final int WING_NORMAL = 40;
    public static final int WING_HEAVY = 41;

    //Constants for part abilities
    //normal main motor points
    public static final int MAIN_MOTOR_NORMAL_DURABILITY = 25;
    public static final int MAIN_MOTOR_NORMAL_MOVEMENT = 45;
    //heavy main motor points
    public static final int MAIN_MOTOR_HEAVY_DURABILITY = 60;
    public static final int MAIN_MOTOR_HEAVY_MOVEMENT = 60;
    //normal main module points
    public static final int MAIN_MODULE_NORMAL_DURABILITY = 50;
    //heavy main module points
    public static final int MAIN_MODULE_HEAVY_DURABILITY = 75;
    //normal control unit points
    public static final int CONTROL_UNIT_NORMAL_DURABILITY = 25;
    public static final int CONTROL_UNIT_NORMAL_STEERING = 40;
    //heavy control unit points
    public static final int CONTROL_UNIT_HEAVY_DURABILITY = 35;
    public static final int CONTROL_UNIT_HEAVY_STEERING = 60;
    //normal wing points
    public static final int WING_NORMAL_DURABILITY = 25;
    public static final int WING_NORMAL_BRAKING = 5;
    public static final int WING_NORMAL_BRAKING_DISP = 20;
    //heavy wing points
    public static final int WING_HEAVY_DURABILITY = 35;
    public static final int WING_HEAVY_BRAKING = 4;
    public static final int WING_HEAVY_BRAKING_DISP = 30;

    public static float SCREEN_DENSITY = 0;

    //Preloaded font to use in all screens
    public static Typeface appFont;

    public static int SCREEN_WIDTH = 0;
    public static int SCREEN_HEIGHT = 0;

    public static void setAppFont(Typeface t) {
        appFont = t;
    }

}
