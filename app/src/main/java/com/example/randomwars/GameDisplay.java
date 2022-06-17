// Class used to center the player on the screen and move the map and other objects around it

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
        this.centerObject = centerGameObject;

        displayRect = new Rect(0,0,widthPixels,heightPixels);
        displayCenterX = widthPixels / 2.0;
        displayCenterY = heightPixels / 2.0;

        update();
    }

    // Method to update the game center position as the player moves
    public void update() {
        gameCenterX = centerObject.getPositionX();
        gameCenterY = centerObject.getPositionY();

        coordinatesOffsetX = displayCenterX - gameCenterX;
        coordinatesOffsetY = displayCenterY - gameCenterY;
    }

    // Returning the X-co-ordinate offset which centers the object on x-axis
    public double coordinatesX(double x) {
        return x + coordinatesOffsetX;
    }

    // Returning the Y-co-ordinate offset which centers the object on y-axis
    public double coordinatesY(double y) {
        return y + coordinatesOffsetY;
    }

    // This method returns the game rect, which is the area visible around the player on the screen
    public Rect getGameRect() {
        return new Rect(
                (int) (gameCenterX - widthPixels/2),
                (int) (gameCenterY - heightPixels/2),
                (int) (gameCenterX + widthPixels/2),
                (int) (gameCenterY + heightPixels/2)
        );
    }
}