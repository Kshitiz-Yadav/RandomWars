package com.example.randomwars.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.randomwars.resources.Sprite;
import com.example.randomwars.resources.SpriteSheet;

public class WaterTile extends Tile {

    private Sprite sprite;

    public WaterTile(SpriteSheet spritesheet, Rect tileLocation) {
        super(tileLocation);
        sprite = spritesheet.getWaterTile();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, tileLocation.left, tileLocation.top);
    }
}
