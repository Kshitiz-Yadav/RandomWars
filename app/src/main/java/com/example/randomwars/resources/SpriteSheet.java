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

    public SpriteSheet(Context context){
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprites_sheet, bitmapOptions);
    }

    Bitmap getSpriteSheet(){return spriteSheet;}

    private Sprite getTileByIndex(int row, int col) {
        return new Sprite(this, new Rect(
                col *  SPRITE_WIDTH_PIXELS,
                row * SPRITE_HEIGHT_PIXELS,
                (col + 1) * SPRITE_WIDTH_PIXELS,
                (row + 1) * SPRITE_HEIGHT_PIXELS
        ));
    }

    public Sprite getFloorTile(){return getTileByIndex(0,0);}

    public Sprite getWaterTile() {return getTileByIndex(0, 1);}

    public Sprite getLavaTile() {return getTileByIndex(0,2);}

    public Sprite getGrassTile() {return getTileByIndex(1,0);}

    public Sprite getDirtTile() {return getTileByIndex(1, 1);}

    public Sprite getBulletSprite() {return getTileByIndex(5,2);}

    public Sprite[] getPlayerArray() {
        Sprite[] playerSpriteArray = new Sprite[3];
        playerSpriteArray[0] = getTileByIndex(1,2);
        playerSpriteArray[1] = getTileByIndex(2,0);
        playerSpriteArray[2] = getTileByIndex(2,1);
        return playerSpriteArray;
    }

    public Sprite[] getSoldierEnemyArray(){
        Sprite[] soldierEnemyArray = new Sprite[3];
        soldierEnemyArray[0] = getTileByIndex(2,2);
        soldierEnemyArray[1] = getTileByIndex(3,0);
        soldierEnemyArray[2] = getTileByIndex(3,1);
        return soldierEnemyArray;
    }

    public Sprite[] getTankEnemyArray(){
        Sprite[] tankEnemyArray = new Sprite[4];
        tankEnemyArray[0] = getTileByIndex(3,2);
        tankEnemyArray[1] = getTileByIndex(4,0);
        tankEnemyArray[2] = getTileByIndex(4,1);
        tankEnemyArray[0] = getTileByIndex(4,2);
        return tankEnemyArray;
    }

}
