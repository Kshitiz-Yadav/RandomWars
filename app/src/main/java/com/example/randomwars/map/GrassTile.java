// Class to create and draw a grass tile

package com.example.randomwars.map;

import android.graphics.Canvas;
import android.graphics.Rect;
import com.example.randomwars.animation.Sprite;
import com.example.randomwars.animation.SpriteSheet;

public class GrassTile extends Tile {
    private final Sprite sprite;

    // Getting a new grass tile
    public GrassTile(SpriteSheet spritesheet, Rect tileLocation) {
        super(tileLocation);
        sprite = spritesheet.getGrassTile();
    }

    // Drawing the grass tile
    @Override
    public void draw(Canvas canvas){sprite.draw(canvas, tileLocation.left, tileLocation.top);}
}