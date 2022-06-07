package com.example.randomwars.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.randomwars.resources.SpriteSheet;

abstract public class Tile {
    Rect tileLocation;

    public enum tileType{
        FLOOR_TILE,
        WATER_TILE,
        LAVA_TILE,
        GRASS_TILE,
        DIRT_TILE
    }

    Tile(Rect tileLocation){
        this.tileLocation = tileLocation;
    }

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

    public abstract void draw(Canvas canvas);
}
