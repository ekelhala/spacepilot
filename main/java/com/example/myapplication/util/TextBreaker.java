package com.example.myapplication.util;

import android.graphics.Canvas;
import android.graphics.Paint;

public class TextBreaker {

    StringBuilder builder;
    int start, end;

    public TextBreaker() {
        builder = new StringBuilder();

    }

    public void breakAndWrite(String text, Paint paint, float width, float x, float y, Canvas assoc) {
        start = 0;
        builder.append(text);
        while (paint.breakText(builder.toString(), true, width, null) <= builder.toString().length()-1) {
            end = paint.breakText(builder.toString(), true, width, null);
            builder.setLength(0);
            builder.append(text.substring(start, end+1));
            assoc.drawText(builder.toString(), x,y,paint);
            start = end;
            builder.setLength(0);
            builder.append(text.substring(end));
            y =  y + paint.getFontSpacing();
        }
        builder.setLength(0);
    }

    public void writeWithNewlines(String text, Paint paint, float x, float y, Canvas canvas) {
        String[] chopped = text.split("\n");
        for (String s : chopped) {
            canvas.drawText(s, x, y, paint);
            y = y +paint.getFontSpacing();
        }
    }

}
