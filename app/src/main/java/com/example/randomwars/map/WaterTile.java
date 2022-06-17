// Class to create and draw a water tile

package com.example.randomwars.map;

import android.graphics.Canvas;
import android.graphics.Rect;
import com.example.randomwars.animation.Sprite;
import com.example.randomwars.animation.SpriteSheet;

public class WaterTile extends Tile {
    private final Sprite sprite;

    // Getting a new water tile
    public WaterTile(SpriteSheet spritesheet, Rect tileLocation) {
        super(tileLocation);
        sprite = spritesheet.getWaterTile();
    }

    // Drawing the water tile
    @Override
    public void draw(Canvas canvas){sprite.draw(canvas, tileLocation.left, tileLocation.top);}
}