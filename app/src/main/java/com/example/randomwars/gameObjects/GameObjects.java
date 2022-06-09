package com.example.randomwars.gameObjects;

import android.graphics.Canvas;

import com.example.randomwars.GameDisplay;
import com.example.randomwars.resources.SpriteSheet;

public abstract class GameObjects {
    public int MAX_HEALTH_POINTS;
    double positionX = 0.0, positionY = 0.0;
    double velocityX = 0.0, velocityY = 0.0;
    double directionX = 1.0;
    double directionY = 0.0;
    public final double MIN_POS_X = SpriteSheet.SPRITE_WIDTH_PIXELS * 10 * 4;
    public final double MAX_POS_X = SpriteSheet.SPRITE_WIDTH_PIXELS * (90 - 1) * 4;
    public final double MIN_POS_Y = SpriteSheet.SPRITE_HEIGHT_PIXELS * 5 * 4 ;
    public final double MAX_POS_Y = SpriteSheet.SPRITE_HEIGHT_PIXELS * (95 - 1) * 4;
    public final double OFFSET = 50;

    public GameObjects(double positionX, double positionY){
        this.positionX = positionX;;
        this.positionY = positionY;
    }

    public double getPositionX(){return positionX;}
    public double getPositionY(){return positionY;}

    public double getDirectionX() {return directionX;}
    public double getDirectionY() {return directionY;}

    public abstract void draw(Canvas canvas, GameDisplay gameDisplay);
    public abstract void update();

    public static double getDistanceBetween(GameObjects obj1, GameObjects obj2){
        return Math.sqrt((Math.pow(obj1.getPositionX() - obj2.getPositionX(), 2)) + (Math.pow(obj1.getPositionY() - obj2.getPositionY(),2)));
    }

    public abstract int getHealthPoint();
}
