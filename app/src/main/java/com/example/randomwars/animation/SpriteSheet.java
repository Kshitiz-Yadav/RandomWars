// This class holds the sprite sheet for the game and returns the required sprites to the calling classes

package com.example.randomwars.animation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import com.example.randomwars.R;

public class SpriteSheet {
    public static final int SPRITE_WIDTH_PIXELS = 32;
    public static final int SPRITE_HEIGHT_PIXELS = 32;
    private final Bitmap spriteSheet;

    // Initializing the bitmap with the game sprite sheet
    public SpriteSheet(Context context){
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprites_sheet, bitmapOptions);
    }

    // Method to get the sprite sheet
    Bitmap getSpriteSheet(){return spriteSheet;}

    // Method to get a particular sprite from the sprite sheet by passing it index of that sprite in the spritesheet
    private Sprite getTileByIndex(int row, int col) {
        return new Sprite(this, new Rect(
                col *  SPRITE_WIDTH_PIXELS,
                row * SPRITE_HEIGHT_PIXELS,
                (col + 1) * SPRITE_WIDTH_PIXELS,
                (row + 1) * SPRITE_HEIGHT_PIXELS
        ));
    }

    // Method to get the floor tile to put in map
    public Sprite getFloorTile(){return getTileByIndex(0,0);}

    // Method to get the water tile to put in map
    public Sprite getWaterTile() {return getTileByIndex(0, 1);}

    // Method to get the lava tile to put in map
    public Sprite getLavaTile() {return getTileByIndex(0,2);}

    // Method to get the grass tile to put in map
    public Sprite getGrassTile() {return getTileByIndex(1,0);}

    // Method to get the dirt tile to put in map
    public Sprite getDirtTile() {return getTileByIndex(1, 1);}

    // Method to get the bullet sprite for the player and the tank
    public Sprite getBulletSprite() {return getTileByIndex(5,2);}

    // Method to get the first-aid sprite
    public Sprite getFirstAid() {return getTileByIndex(5,0);}

    // Method to get the bomb sprite
    public Sprite getAtomBomb() {return getTileByIndex(5, 1);}

    // Method to get the player sprite array for PlayerAnimator
    public Sprite[] getPlayerArray() {
        Sprite[] playerSpriteArray = new Sprite[3];
        playerSpriteArray[0] = getTileByIndex(1,2);
        playerSpriteArray[1] = getTileByIndex(2,0);
        playerSpriteArray[2] = getTileByIndex(2,1);
        return playerSpriteArray;
    }

    // Method to get the soldier sprite array
    public Sprite[] getSoldierEnemyArray(){
        Sprite[] soldierEnemyArray = new Sprite[2];
        soldierEnemyArray[0] = getTileByIndex(3,0);
        soldierEnemyArray[1] = getTileByIndex(3,1);
        return soldierEnemyArray;
    }

    // Method to get the tank sprite array
    public Sprite[] getTankEnemyArray(){
        Sprite[] tankEnemyArray = new Sprite[4];
        tankEnemyArray[0] = getTileByIndex(3,2);
        tankEnemyArray[1] = getTileByIndex(4,0);
        tankEnemyArray[2] = getTileByIndex(4,1);
        tankEnemyArray[0] = getTileByIndex(4,2);
        return tankEnemyArray;
    }
}