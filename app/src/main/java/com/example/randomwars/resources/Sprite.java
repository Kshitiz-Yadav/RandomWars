package com.example.randomwars.resources;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {

    private final SpriteSheet spriteSheet;
    private final Rect rect;

    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
    }

    public void draw(Canvas canvas, int x, int y){
        canvas.drawBitmap(
                spriteSheet.getSpriteSheet(),
                rect,
                new Rect(x, y, x + getWidth(), y + getHeight()),
                null
        );
    }

    private int getHeight() {
        return rect.height();
    }

    private int getWidth() {
        return rect.width();
    }
}
