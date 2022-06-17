// Method that defines and selects the tile indexes used in TileLayout and then returns it to the TileMap

package com.example.randomwars.map;

import android.graphics.Canvas;
import android.graphics.Rect;
import com.example.randomwars.animation.SpriteSheet;

abstract public class Tile {
    Rect tileLocation;

    // Defining the tile indexes with an enum for all types of tiles
    public enum tileType{
        FLOOR_TILE,
        WATER_TILE,
        LAVA_TILE,
        GRASS_TILE,
        DIRT_TILE
    }

    // Constructor to set the tile location
    Tile(Rect tileLocation){this.tileLocation = tileLocation;}

    // Method that returns the tile sprite based on the index provided to it
    public static Tile getTile(int tileTypeIndex, Rect tileLocation, SpriteSheet spritesheet){
        switch(tileType.values()[tileTypeIndex]){
            case FLOOR_TILE:
                return new FloorTile(spritesheet, tileLocation);
            case WATER_TILE:
                return new WaterTile(spritesheet, tileLocation);
            case LAVA_TILE:
                return new LavaTile(spritesheet, tileLocation);
            case GRASS_TILE:
                return new GrassTile(spritesheet, tileLocation);
            case DIRT_TILE:
                return new DirtTile(spritesheet, tileLocation);
        }
        return null;
    }

    // Abstract method to draw the tiles on the screen
    public abstract void draw(Canvas canvas);
}