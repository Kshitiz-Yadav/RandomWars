package com.example.randomwars.gameObjects;

import android.graphics.Canvas;

import com.example.randomwars.GameDisplay;

public abstract class GameObjects {
    public int MAX_HEALTH_POINTS;
    double positionX = 0.0, positionY = 0.0;
    double velocityX = 0.0, velocityY = 0.0;
    double directionX = 1.0;
    double directionY = 0.0;

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
