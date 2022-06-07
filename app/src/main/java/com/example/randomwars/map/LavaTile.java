package com.example.randomwars.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.randomwars.resources.Sprite;
import com.example.randomwars.resources.SpriteSheet;

public class LavaTile extends Tile {

    private Sprite sprite;

    public LavaTile(SpriteSheet spritesheet, Rect tileLocation) {
        super(tileLocation);
        sprite = spritesheet.getLavaTile();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, tileLocation.left, tileLocation.top);
    }
}
