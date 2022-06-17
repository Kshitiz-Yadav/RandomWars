// Class to create and draw a lava tile

package com.example.randomwars.map;

import android.graphics.Canvas;
import android.graphics.Rect;
import com.example.randomwars.animation.Sprite;
import com.example.randomwars.animation.SpriteSheet;

public class LavaTile extends Tile {
    private final Sprite sprite;

    // Getting a new lava tile
    public LavaTile(SpriteSheet spritesheet, Rect tileLocation) {
        super(tileLocation);
        sprite = spritesheet.getLavaTile();
    }

    // Drawing the lava tile
    @Override
    public void draw(Canvas canvas){sprite.draw(canvas, tileLocation.left, tileLocation.top);}
}