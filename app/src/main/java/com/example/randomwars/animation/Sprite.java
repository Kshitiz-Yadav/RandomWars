// This class defines the basic properties of a game sprite

package com.example.randomwars.animation;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
    private final SpriteSheet spriteSheet;
    private final Rect rect;

    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
    }

    // Method to draw the sprite on the screen, this will be overridden by the gameObjects and tileMap tiles
    public void draw(Canvas canvas, int x, int y){
        canvas.drawBitmap(
                spriteSheet.getSpriteSheet(),
                rect,
                new Rect(x, y, x + getWidth()*4, y + getHeight()*4),
                null
        );
    }

    // Returns height of the sprite
    public int getHeight(){return rect.height();}

    // Returns width of the sprite
    public int getWidth(){return rect.width();}
}