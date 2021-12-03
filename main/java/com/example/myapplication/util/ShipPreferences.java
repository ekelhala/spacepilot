package com.example.myapplication.util;

import com.example.myapplication.Const;
import com.example.myapplication.R;

//Helper class to make ship related operations easier to do and understand
public class ShipPreferences {

    private int mainMotor;
    private int controlUnit;
    private int mainModule;
    private int wing;

    private int steeringPoints = 0;
    private int movementPoints = 0;
    private int durabilityPoints = 0;
    private int brakingPoints = 0;

    private boolean isSetSteeringPoints = false;
    private boolean isSetMovementPoints = false;
    private boolean isSetDurabilityPoints = false;
    private boolean isSetBrakingPoints = false;

    private boolean heavy_main_motor_owned;
    private boolean heavy_main_module_owned;
    private boolean heavy_control_unit_owned;
    private boolean heavy_wing_owned;
    //Resource pointers for bitmaps to draw various parts of the ship
    private int mainMotorSprite;
    private int controlUnitSprite;
    private int mainModuleSprite;
    private int wingSprite;

    public ShipPreferences(int mainModule, int mainMotor, int controlUnit, int wing, boolean[] partsOwned) {
        this.mainModule = mainModule;
        this.mainMotor = mainMotor;
        this.controlUnit = controlUnit;
        this.wing = wing;
        setMainModuleSprite();
        setMainMotorSprite();
        setControlUnitSprite();
        setWingSprite();

        heavy_main_module_owned = partsOwned[0];
        heavy_main_motor_owned = partsOwned[1];
        heavy_control_unit_owned = partsOwned[2];
        heavy_wing_owned = partsOwned[3];
    }

    public boolean isOwned(int part) {

        switch (part) {
            case Const.MAIN_MODULE_HEAVY:
                return heavy_main_module_owned;
            case Const.MAIN_MOTOR_HEAVY:
                return heavy_main_motor_owned;
            case Const.CONTROL_UNIT_HEAVY:
                return heavy_control_unit_owned;
            case Const.WING_HEAVY:
                return heavy_wing_owned;
            default:
                return true;
        }

    }

    public void setOwned(int part) {
        switch (part) {
            case Const.MAIN_MODULE_HEAVY:
                heavy_main_module_owned = true;
                break;
            case Const.MAIN_MOTOR_HEAVY:
                heavy_main_motor_owned = true;
                break;
            case Const.CONTROL_UNIT_HEAVY:
                heavy_control_unit_owned = true;
                break;
            case Const.WING_HEAVY:
                heavy_wing_owned = true;
                break;
        }
    }

    public void setSelected(int part) {
        switch (part) {
            case Const.MAIN_MODULE_HEAVY:
            case Const.MAIN_MODULE_NORMAL:
                mainModule = part;
                setMainModuleSprite();
                break;
            case Const.CONTROL_UNIT_NORMAL:
            case Const.CONTROL_UNIT_HEAVY:
                controlUnit = part;
                setControlUnitSprite();
                break;
            case Const.MAIN_MOTOR_NORMAL:
            case Const.MAIN_MOTOR_HEAVY:
                mainMotor = part;
                setMainMotorSprite();
                break;
            case Const.WING_NORMAL:
            case Const.WING_HEAVY:
                wing = part;
                setWingSprite();
                break;
        }

    }

    public int getControlUnit() {
        return controlUnit;
    }

    public int getControlUnitSprite() {
        return controlUnitSprite;
    }

    public int getMainModule() {
        return mainModule;
    }

    public int getMainModuleSprite() {
        return mainModuleSprite;
    }

    public int getMainMotor() {
        return mainMotor;
    }

    public int getMainMotorSprite() {
        return mainMotorSprite;
    }

    public int getWing() {
        return wing;
    }

    public int getWingSprite() {
        return wingSprite;
    }

    public void setMainMotor(int mainMotor) {
        this.mainMotor = mainMotor;
        setMainMotorSprite();
    }

    public void setControlUnit(int controlUnit) {
        this.controlUnit = controlUnit;
        setControlUnitSprite();
    }

    public void setMainModule(int mainModule) {
        this.mainModule = mainModule;
        setMainModuleSprite();
    }

    public void setWing(int wing) {
        this.wing = wing;
        setWingSprite();
    }

    private void setMainMotorSprite() {
        switch (mainMotor) {
            case Const.MAIN_MOTOR_NORMAL:
                mainMotorSprite = R.raw.default_main_motor;
                break;
            case Const.MAIN_MOTOR_HEAVY:
                mainMotorSprite = R.raw.heavy_engine2;
                break;
        }
        calculateMovementPoints();
        calculateDurabilityPoints();
    }

    private void setControlUnitSprite() {
        switch (controlUnit) {
            case Const.CONTROL_UNIT_NORMAL:
                controlUnitSprite = R.raw.default_control_unit;
                break;
            case Const.CONTROL_UNIT_HEAVY:
                controlUnitSprite = R.raw.heavy_control_motor;
                break;
        }
        calculateDurabilityPoints();
        calculateSteeringPoints();
    }

    private void setMainModuleSprite() {
        switch (mainModule) {
            case Const.MAIN_MODULE_NORMAL:
                mainModuleSprite = R.raw.default_main_unit;
                break;
            case Const.MAIN_MODULE_HEAVY:
                mainModuleSprite = R.raw.heavy_control_unit_ready;
                break;
        }
        calculateDurabilityPoints();
    }

    private void setWingSprite() {
        switch (wing) {
            case Const.WING_NORMAL:
                wingSprite = R.raw.default_wing;
                break;
            case Const.WING_HEAVY:
                wingSprite = R.raw.heavy_wing;
                break;
        }
        calculateDurabilityPoints();
        calculateBrakingPoints();
    }

    private void calculateMovementPoints() {
        if(isSetMovementPoints) {
            movementPoints = 0;
        }
        else {
            isSetMovementPoints = true;
        }

        switch (mainMotor) {
            case Const.MAIN_MOTOR_NORMAL:
                movementPoints = movementPoints + Const.MAIN_MOTOR_NORMAL_MOVEMENT;
                break;
            case Const.MAIN_MOTOR_HEAVY:
                movementPoints = movementPoints + Const.MAIN_MOTOR_HEAVY_MOVEMENT;
                break;
        }
    }

    private void calculateDurabilityPoints() {
        if(isSetDurabilityPoints) {
            durabilityPoints = 0;
        }
        else {
            isSetDurabilityPoints = true;
        }

        switch (mainMotor) {
            case Const.MAIN_MOTOR_NORMAL:
                durabilityPoints = durabilityPoints + Const.MAIN_MOTOR_NORMAL_DURABILITY;
                break;
            case Const.MAIN_MOTOR_HEAVY:
                durabilityPoints = durabilityPoints + Const.MAIN_MOTOR_HEAVY_DURABILITY;
                break;
        }

        switch (mainModule) {
            case Const.MAIN_MODULE_NORMAL:
                durabilityPoints = durabilityPoints + Const.MAIN_MODULE_NORMAL_DURABILITY;
                break;
            case Const.MAIN_MODULE_HEAVY:
                durabilityPoints = durabilityPoints + Const.MAIN_MODULE_HEAVY_DURABILITY;
                break;
        }

        switch (wing) {
            case Const.WING_NORMAL:
                durabilityPoints = durabilityPoints + Const.WING_NORMAL_DURABILITY;
                break;
            case Const.WING_HEAVY:
                durabilityPoints = durabilityPoints + Const.WING_HEAVY_DURABILITY;
                break;
        }

        switch (controlUnit) {
            case Const.CONTROL_UNIT_NORMAL:
                durabilityPoints = durabilityPoints + Const.CONTROL_UNIT_NORMAL_DURABILITY;
                break;
            case Const.CONTROL_UNIT_HEAVY:
                durabilityPoints = durabilityPoints + Const.CONTROL_UNIT_HEAVY_DURABILITY;
                break;
        }
    }

    private void calculateSteeringPoints() {
        if(isSetSteeringPoints) {
            steeringPoints = 0;
        }
        else {
            isSetSteeringPoints = true;
        }

        switch (controlUnit) {
            case Const.CONTROL_UNIT_NORMAL:
                steeringPoints = steeringPoints + Const.CONTROL_UNIT_NORMAL_STEERING;
                break;
            case Const.CONTROL_UNIT_HEAVY:
                steeringPoints = steeringPoints + Const.CONTROL_UNIT_HEAVY_STEERING;
                break;
        }

    }

    private void calculateBrakingPoints() {
        if(isSetBrakingPoints) {
            brakingPoints = 0;
        }
        else {
            isSetBrakingPoints = true;
        }

        switch (wing) {
            case Const.WING_NORMAL:
                brakingPoints = Const.WING_NORMAL_BRAKING;
                break;
            case Const.WING_HEAVY:
                brakingPoints = Const.WING_HEAVY_BRAKING;
                break;
        }
    }

    public int getMovementPoints() {
        return movementPoints;
    }

    public int getDurabilityPoints() {
        return durabilityPoints;
    }

    public int getSteeringPoints() {
        return steeringPoints;
    }

    public int getBrakingPointsForUser() {
        switch (wing) {
            case Const.WING_NORMAL:
                return Const.WING_NORMAL_BRAKING_DISP;
            case Const.WING_HEAVY:
                return Const.WING_HEAVY_BRAKING_DISP;
            default:
                return 0;
        }
    }

    public int getBrakingPointsForSystem() {
        return brakingPoints;
    }

}
