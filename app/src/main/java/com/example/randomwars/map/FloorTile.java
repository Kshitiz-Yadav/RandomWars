// Class to create and draw a floor tile

package com.example.randomwars.map;

import android.graphics.Canvas;
import android.graphics.Rect;
import com.example.randomwars.animation.Sprite;
import com.example.randomwars.animation.SpriteSheet;

public class FloorTile extends Tile {
    private final Sprite sprite;

    // Getting a new floor tile
    public FloorTile(SpriteSheet spritesheet, Rect tileLocation) {
        super(tileLocation);
        sprite = spritesheet.getFloorTile();
    }

    // Drawing the floor tile
    @Override
    public void draw(Canvas canvas){sprite.draw(canvas, tileLocation.left, tileLocation.top);}
}