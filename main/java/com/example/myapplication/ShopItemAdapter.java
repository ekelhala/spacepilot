package com.example.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

public class ShopItemAdapter extends BaseAdapter {

    private static class ViewHolder {
        TextView itemName;
        TextView itemDescription;
        TextView itemCost;
        TextView itemOwnership;
        ImageView itemIcon;
    }

    ViewHolder holder;
    Resources r;
    Context context;
    ArrayList<ShopItem> items;
    Typeface tf;



    public ShopItemAdapter(Context context, ArrayList<ShopItem> items) {
        this.context = context;
        this.items = items;
        r = context.getResources();
        tf = ResourcesCompat.getFont(context, R.font.pixel_font);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ShopItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.shop_list_item,parent,false);
            holder = new ViewHolder();
            holder.itemName = convertView.findViewById(R.id.item_title);
            holder.itemDescription = convertView.findViewById(R.id.item_hint);
            holder.itemCost = convertView.findViewById(R.id.item_cost);
            holder.itemIcon = convertView.findViewById(R.id.item_image);
            holder.itemOwnership = convertView.findViewById(R.id.item_ownership);
            holder.itemName.setTypeface(tf);
            holder.itemOwnership.setTypeface(tf);
            holder.itemCost.setTypeface(tf);
            holder.itemDescription.setTypeface(tf);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        ShopItem currentItem = getItem(position);

        if(currentItem != null) {
            holder.itemName.setText(currentItem.getName());
            holder.itemDescription.setText(currentItem.getDescription());
            holder.itemCost.setText(String.format(Locale.ENGLISH,"%d", currentItem.getCost()));
            holder.itemIcon.setImageBitmap(currentItem.getIcon());
            if(currentItem.isOwned()) {
                holder.itemOwnership.setText(R.string.item_owned);
            }
            else {
                holder.itemOwnership.setText(R.string.item_not_owned);
            }
            }

        return convertView;
    }

}
