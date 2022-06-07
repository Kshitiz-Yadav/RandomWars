package com.example.randomwars;

import android.graphics.Rect;

import com.example.randomwars.gameObjects.GameObjects;

public class GameDisplay {
    public final Rect displayRect;
    private final int widthPixels;
    private final int heightPixels;
    private final GameObjects centerObject;
    private final double displayCenterX;
    private final double displayCenterY;
    private double coordinatesOffsetX;
    private double coordinatesOffsetY;
    private double gameCenterX;
    private double gameCenterY;

    public GameDisplay(int widthPixels, int heightPixels, GameObjects centerGameObject) {
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;
        displayRect = new Rect(0,0,widthPixels,heightPixels);

        this.centerObject = centerGameObject;

        displayCenterX = widthPixels / 2.0;
        displayCenterY = heightPixels / 2.0;

        update();
    }

    public void update() {
        gameCenterX = centerObject.getPositionX();
        gameCenterY = centerObject.getPositionY();

        coordinatesOffsetX = displayCenterX - gameCenterX;
        coordinatesOffsetY = displayCenterY - gameCenterY;
    }

    public double coordinatesX(double x) {
        return x + coordinatesOffsetX;
    }

    public double coordinatesY(double y) {
        return y + coordinatesOffsetY;
    }

    public Rect getGameRect() {
        return new Rect(
                (int) (gameCenterX - widthPixels/2),
                (int) (gameCenterY - heightPixels/2),
                (int) (gameCenterX + widthPixels/2),
                (int) (gameCenterY + heightPixels/2)
        );
    }

}
