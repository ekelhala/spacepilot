package com.example.myapplication;

import android.graphics.Bitmap;

public class ShopItem {

    private String name;
    private String description;
    private int cost;
    private Bitmap icon;
    private int id;
    private boolean owned;

    public ShopItem(String name, String description, int cost, int id, boolean owned, Bitmap icon) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.id = id;
        this.icon = icon;
        this.owned = owned;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public int getId() {
        return id;
    }

    public boolean isOwned() {
        return owned;
    }

}
