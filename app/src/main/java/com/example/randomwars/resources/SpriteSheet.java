package com.example.randomwars.resources;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.randomwars.R;

public class SpriteSheet {
    private final int SPRITE_WIDTH_PIXELS = 32;
    private final int SPRITE_HEIGHT_PIXELS = 32;
    private Bitmap spriteSheet;

    SpriteSheet(Context context){
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.tiles, bitmapOptions);
    }

    Bitmap getSpriteSheet(){
        return spriteSheet;
    }

    private Sprite getTileByIndex(int row, int col) {
        return new Sprite(this, new Rect(
                col * SPRITE_WIDTH_PIXELS,
                row * SPRITE_HEIGHT_PIXELS,
                (col + 1) * SPRITE_WIDTH_PIXELS,
                (row + 1) * SPRITE_HEIGHT_PIXELS
        ));
    }

    public Sprite getFloorTile(){
        return getTileByIndex(0,0);
    }

    public Sprite getWaterTile() {
        return getTileByIndex(0, 1);
    }

    public Sprite getLavaTile() {
        return getTileByIndex(0,2);
    }

    public Sprite getGrassTile() {
        return getTileByIndex(1,0);
    }

    public Sprite getDirtTile() {
        return getTileByIndex(1, 1);
    }
}
