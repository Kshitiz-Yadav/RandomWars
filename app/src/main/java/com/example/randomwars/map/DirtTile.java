// Class to create and draw a dirt tile

package com.example.randomwars.map;

import android.graphics.Canvas;
import android.graphics.Rect;
import com.example.randomwars.animation.Sprite;
import com.example.randomwars.animation.SpriteSheet;

public class DirtTile extends Tile {
    private final Sprite sprite;

    // Getting a new dirt tile
    public DirtTile(SpriteSheet spritesheet, Rect tileLocation) {
        super(tileLocation);
        sprite = spritesheet.getDirtTile();
    }

    // Drawing the dirt tile
    @Override
    public void draw(Canvas canvas){sprite.draw(canvas, tileLocation.left, tileLocation.top);}
}