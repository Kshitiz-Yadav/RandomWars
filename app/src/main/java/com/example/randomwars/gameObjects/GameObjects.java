/*
    Abstract class defining some common properties and functions of all the game objects
 */

package com.example.randomwars.gameObjects;

import android.graphics.Canvas;
import com.example.randomwars.GameDisplay;
import com.example.randomwars.animation.SpriteSheet;

public abstract class GameObjects {
    public int MAX_HEALTH_POINTS;
    double positionX, positionY;
    double velocityX = 0.0, velocityY = 0.0;
    double directionX = 1.0, directionY = 0.0;

    // Setting maximum and minimum limits of spawning area
    public final static double MIN_POS_X = SpriteSheet.SPRITE_WIDTH_PIXELS * 10 * 4;
    public final static double MAX_POS_X = SpriteSheet.SPRITE_WIDTH_PIXELS * (90 - 1) * 4;
    public final static double MIN_POS_Y = SpriteSheet.SPRITE_HEIGHT_PIXELS * 5 * 4 ;
    public final static double MAX_POS_Y = SpriteSheet.SPRITE_HEIGHT_PIXELS * (95 - 1) * 4;
    public final static double OFFSET = 50;

    // Constructor to set the position of the object
    public GameObjects(double positionX, double positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }

    // Methods to get the position of the object
    public double getPositionX(){return positionX;}
    public double getPositionY(){return positionY;}

    // Abstract method to draw the gameObject on the screen
    public abstract void draw(Canvas canvas, GameDisplay gameDisplay);
    public abstract void update();

    // Method to get the distance between two gameObjects
    public static double getDistanceBetween(GameObjects obj1, GameObjects obj2){
        return Math.sqrt((Math.pow(obj1.getPositionX() - obj2.getPositionX(), 2)) + (Math.pow(obj1.getPositionY() - obj2.getPositionY(),2)));
    }

    // Method to return health points of the object, used to draw health bar and check game over condition
    public abstract int getHealthPoint();
}