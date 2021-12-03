package com.example.myapplication;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.util.ShipPreferences;

import java.util.ArrayList;
import java.util.Locale;

import androidx.core.content.res.ResourcesCompat;

public class ShopActivity extends Activity implements View.OnClickListener, SurfaceHolder.Callback, Spinner.OnItemSelectedListener, AlertDialog.OnClickListener {

    Spinner wingSelector, mainModuleSelector, mainEngineSelector, controlUnitSelector;
    ShopItemAdapter wingAdapter, mainModuleAdapter, mainEngineAdapter, controlUnitAdapter;
    ArrayList<ShopItem> wings, mainModules, mainEngines, controlUnits;
    PartPreview body, mainEngine, wing, controlEngine;
    Resources r;
    Intent backToMenu;
    SurfaceView shipPreview;
    SurfaceHolder holder;
    TextView specs, header;
    Canvas canvas;
    Paint p;
    int gold;
    ShipPreferences pref;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    AlertDialog buyDialog;
    ShopItem buying;
    int canvasWidth;
    Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        r = getResources();
        p = new Paint();
        preferences = getSharedPreferences(Const.SHARED_PREFERENCES_KEY, MODE_PRIVATE);
        gold = preferences.getInt(Const.SHARED_PREFERENCES_VALUE_GOLD, 0);
        boolean heavyMainModuleOwned = preferences.getBoolean(Const.MAIN_MODULE_HEAVY_OWNED, false);
        boolean heavyMainEngineOwned = preferences.getBoolean(Const.MAIN_MOTOR_HEAVY_OWNED, false);
        boolean heavyControlEngineOwned = preferences.getBoolean(Const.CONTROL_UNIT_HEAVY_OWNED, false);
        boolean heavyWingOwned = preferences.getBoolean(Const.WING_HEAVY_OWNED, false);
        int mainModule = preferences.getInt(Const.SHARED_PREFERENCES_VALUE_MAIN_MODULE_ID, Const.MAIN_MODULE_NORMAL);
        int mainEngine = preferences.getInt(Const.SHARED_PREFERENCES_VALUE_MAIN_MOTOR_ID, Const.MAIN_MOTOR_NORMAL);
        int controlEngine = preferences.getInt(Const.SHARED_PREFERENCES_VALUE_CONTROL_UNIT_ID, Const.CONTROL_UNIT_NORMAL);
        int wing = preferences.getInt(Const.SHARED_PREFERENCES_VALUE_WING_ID, Const.WING_NORMAL);
        pref = new ShipPreferences(mainModule, mainEngine, controlEngine, wing, new boolean[]{heavyMainModuleOwned, heavyMainEngineOwned, heavyControlEngineOwned, heavyWingOwned});
        tf = ResourcesCompat.getFont(this, R.font.pixel_font);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Const.SCREEN_DENSITY = getResources().getDisplayMetrics().density;
        wingSelector = findViewById(R.id.wingCustom);
        mainModuleSelector = findViewById(R.id.mainModuleCustom);
        mainEngineSelector = findViewById(R.id.mainEngineCustom);
        controlUnitSelector = findViewById(R.id.controlEngineCustom);
        specs = findViewById(R.id.shipSpecs);
        specs.setTypeface(tf);
        header = findViewById(R.id.shopTitle);
        header.setTypeface(tf);
        wings = new ArrayList<>();
        wings.add(new ShopItem(getString(R.string.wing_normal), getString(R.string.wing_normal_hint), 0, Const.WING_NORMAL, true, BitmapFactory.decodeResource(r, R.raw.default_wing)));
        wings.add(new ShopItem(getString(R.string.wing_heavy), getString(R.string.wing_heavy_hint), 100, Const.WING_HEAVY, pref.isOwned(Const.WING_HEAVY), BitmapFactory.decodeResource(r, R.raw.heavy_wing)));
        wingAdapter = new ShopItemAdapter(this, wings);
        wingSelector.setAdapter(wingAdapter);
        wingSelector.setOnItemSelectedListener(this);

        controlUnits = new ArrayList<>();
        controlUnits.add(new ShopItem(getString(R.string.control_engine_normal), getString(R.string.control_engine_normal_hint), 0, Const.CONTROL_UNIT_NORMAL,true, BitmapFactory.decodeResource(r,R.raw.default_control_unit)));
        controlUnits.add(new ShopItem(getString(R.string.control_engine_heavy), getString(R.string.control_engine_heavy_hint), 250, Const.CONTROL_UNIT_HEAVY, pref.isOwned(Const.CONTROL_UNIT_HEAVY), BitmapFactory.decodeResource(r, R.raw.heavy_control_motor)));
        controlUnitAdapter = new ShopItemAdapter(this, controlUnits);
        controlUnitSelector.setAdapter(controlUnitAdapter);
        controlUnitSelector.setOnItemSelectedListener(this);

        mainModules = new ArrayList<>();
        mainModules.add(new ShopItem(getString(R.string.main_module_normal), getString(R.string.main_module_normal_hint), 0, Const.MAIN_MODULE_NORMAL,true, BitmapFactory.decodeResource(r, R.raw.default_main_unit)));
        mainModules.add(new ShopItem(getString(R.string.main_module_heavy), getString(R.string.main_module_heavy_hint), 550, Const.MAIN_MODULE_HEAVY, pref.isOwned(Const.MAIN_MODULE_HEAVY), BitmapFactory.decodeResource(r,R.raw.heavy_control_unit_ready)));
        mainModuleAdapter = new ShopItemAdapter(this, mainModules);
        mainModuleSelector.setAdapter(mainModuleAdapter);
        mainModuleSelector.setOnItemSelectedListener(this);

        mainEngines = new ArrayList<>();
        mainEngines.add(new ShopItem(getString(R.string.main_engine_normal), getString(R.string.main_engine_normal_hint), 0, Const.MAIN_MOTOR_NORMAL, true, BitmapFactory.decodeResource(r,R.raw.default_main_motor)));
        mainEngines.add(new ShopItem(getString(R.string.main_engine_heavy), getString(R.string.main_engine_heavy_hint), 400, Const.MAIN_MOTOR_HEAVY, pref.isOwned(Const.MAIN_MOTOR_HEAVY), BitmapFactory.decodeResource(r, R.raw.heavy_engine2)));
        mainEngineAdapter = new ShopItemAdapter(this, mainEngines);
        mainEngineSelector.setAdapter(mainEngineAdapter);
        mainEngineSelector.setOnItemSelectedListener(this);

        setCurrentSelections();

        shipPreview = findViewById(R.id.shipView);
        shipPreview.getHolder().addCallback(this);

        backToMenu = new Intent(ShopActivity.this, MainActivity.class);
        backToMenu.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        ImageButton menu = findViewById(R.id.menuButton);
        menu.setOnClickListener(this);
    }

    void setCurrentSelections() {

        switch (pref.getMainModule()) {
            case Const.MAIN_MODULE_NORMAL:
                mainModuleSelector.setSelection(0);
                break;
            case Const.MAIN_MODULE_HEAVY:
                mainModuleSelector.setSelection(1);
                break;
        }

        switch (pref.getMainMotor()) {
            case Const.MAIN_MOTOR_NORMAL:
                mainEngineSelector.setSelection(0);
                break;
            case Const.MAIN_MOTOR_HEAVY:
                mainEngineSelector.setSelection(1);
                break;
        }

        switch (pref.getControlUnit()) {
            case Const.CONTROL_UNIT_NORMAL:
                controlUnitSelector.setSelection(0);
                break;
            case Const.CONTROL_UNIT_HEAVY:
                controlUnitSelector.setSelection(1);
                break;
        }

        switch (pref.getWing()) {
            case Const.WING_NORMAL:
                wingSelector.setSelection(0);
                break;
            case Const.WING_HEAVY:
                wingSelector.setSelection(1);
                break;
        }
        specs.setText(getString(R.string.ship_specs, pref.getDurabilityPoints(), pref.getMovementPoints(), pref.getSteeringPoints(), pref.getBrakingPointsForUser()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        editor = preferences.edit();
        editor.putInt(Const.SHARED_PREFERENCES_VALUE_MAIN_MODULE_ID, pref.getMainModule());
        editor.putInt(Const.SHARED_PREFERENCES_VALUE_MAIN_MOTOR_ID, pref.getMainMotor());
        editor.putInt(Const.SHARED_PREFERENCES_VALUE_CONTROL_UNIT_ID, pref.getControlUnit());
        editor.putInt(Const.SHARED_PREFERENCES_VALUE_WING_ID, pref.getWing());
        editor.putInt(Const.SHARED_PREFERENCES_VALUE_GOLD, gold);
        editor.putBoolean(Const.CONTROL_UNIT_HEAVY_OWNED, pref.isOwned(Const.CONTROL_UNIT_HEAVY));
        editor.putBoolean(Const.WING_HEAVY_OWNED, pref.isOwned(Const.WING_HEAVY));
        editor.putBoolean(Const.MAIN_MOTOR_HEAVY_OWNED, pref.isOwned(Const.MAIN_MOTOR_HEAVY));
        editor.putBoolean(Const.MAIN_MODULE_HEAVY_OWNED, pref.isOwned(Const.MAIN_MODULE_HEAVY));
        editor.apply();
    }

    void drawShipPreview() {
     canvas = holder.lockCanvas();
     canvasWidth = canvas.getWidth();
     canvas.drawColor(Color.BLACK);

     body.setSprite(BitmapFactory.decodeResource(getResources(), pref.getMainModuleSprite()));
     mainEngine.setSprite(BitmapFactory.decodeResource(getResources(), pref.getMainMotorSprite()));
     wing.setSprite(BitmapFactory.decodeResource(getResources(), pref.getWingSprite()));
     controlEngine.setSprite(BitmapFactory.decodeResource(getResources(), pref.getControlUnitSprite()));

     body.draw(canvas);
     mainEngine.draw(canvas);
     wing.draw(canvas);
     controlEngine.draw(canvas);
     holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.holder = holder;
        pref.setMainModule(mainModuleAdapter.getItem(mainModuleSelector.getSelectedItemPosition()).getId());
        pref.setControlUnit(controlUnitAdapter.getItem(controlUnitSelector.getSelectedItemPosition()).getId());
        pref.setMainMotor(mainEngineAdapter.getItem(mainEngineSelector.getSelectedItemPosition()).getId());
        pref.setWing(wingAdapter.getItem(wingSelector.getSelectedItemPosition()).getId());

        body = new PartPreview(BitmapFactory.decodeResource(getResources(), pref.getMainModuleSprite()), 40, 30, 30, 30, Const.SCREEN_DENSITY);
        mainEngine = new PartPreview(BitmapFactory.decodeResource(getResources(), pref.getMainMotorSprite()), 15, 20, 0, 0, Const.SCREEN_DENSITY);
        wing = new PartPreview(BitmapFactory.decodeResource(getResources(), pref.getWingSprite()), 10, 8, 0,0,Const.SCREEN_DENSITY);
        controlEngine = new PartPreview(BitmapFactory.decodeResource(getResources(), pref.getControlUnitSprite()), 13, 8, 0,0,Const.SCREEN_DENSITY);

        mainEngine.setPosition(body.x - mainEngine.width, body.y);
        wing.setPosition(body.x + body.width/4, body.y - wing.height);
        controlEngine.setPosition(wing.x, body.y + body.height);
        drawShipPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.menuButton) {
            startActivity(backToMenu);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ShopItem selectedItem = (ShopItem) parent.getAdapter().getItem(position);
        if(!pref.isOwned(selectedItem.getId())) {
            buyItem(selectedItem);
        }
        else {
            pref.setSelected(selectedItem.getId());
            drawShipPreview();
            specs.setText(getString(R.string.ship_specs, pref.getDurabilityPoints(), pref.getMovementPoints(), pref.getSteeringPoints(), pref.getBrakingPointsForUser()));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    void buyItem(ShopItem item) {
        buying = item;
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage(String.format(Locale.ENGLISH, getString(R.string.confirm_buy), item.getCost()));
        b.setPositiveButton(R.string.do_buy, this);
        b.setNegativeButton(R.string.do_not_buy, this);
        buyDialog = b.create();
        buyDialog.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == AlertDialog.BUTTON_POSITIVE) {
            if(gold >= buying.getCost()) {
                gold = gold - buying.getCost();
                pref.setOwned(buying.getId());
                pref.setSelected(buying.getId());
                drawShipPreview();
                setCurrentSelections();
            }
            else {
                dialog.dismiss();
                Toast.makeText(this, "You don't have enough gold to buy this item", Toast.LENGTH_LONG).show();
                setCurrentSelections();
            }
        }
        else {
            dialog.dismiss();
        }
    }
}