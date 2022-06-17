// Bullet GameObject- shot by player and tankEnemy, an individual loses health when shot by enemy's bullet

package com.example.randomwars.gameObjects;

import android.content.Context;
import android.graphics.Canvas;
import com.example.randomwars.GameDisplay;
import com.example.randomwars.GameLoop;
import com.example.randomwars.animation.Sprite;
import com.example.randomwars.animation.SpriteSheet;

public class Bullet extends GameObjects {
    public static final double SPEED_PIXELS_PER_SECOND = 800.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    private static final double UPDATES_BEFORE_TIMEOUT = GameLoop.MAX_UPS * 5;
    public double remainingUpdates = UPDATES_BEFORE_TIMEOUT;

    public Sprite bullet;

    // Defining the final velocity of bullet in constructor as it will not change with time
    public Bullet(Context context, GameObjects object, double actuatorX, double actuatorY){
        super(object.getPositionX(), object.getPositionY());

        SpriteSheet spriteSheet = new SpriteSheet(context);
        this.bullet = spriteSheet.getBulletSprite();
        this.velocityX = actuatorX * MAX_SPEED;
        this.velocityY = actuatorY * MAX_SPEED;
    }

    // Method which returns true if it is time to remove the first aid from the screen if it hasn't interacted with the player
    public boolean timeOut(){
        if(remainingUpdates == 0){
            return true;
        }
        remainingUpdates--;
        return false;
    }

    // Method to draw the bullet to the screen
    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        bullet.draw(canvas,
                (int) gameDisplay.coordinatesX(positionX) - bullet.getWidth(),
                (int) gameDisplay.coordinatesY(positionY) - bullet.getHeight()
        );
    }

    // Method to update the position of the bullet on the screen so that it appears moving
    @Override
    public void update() {
        positionX += velocityX;
        positionY += velocityY;
    }

    // Method to return health points, returning zero as bullet has no health
    @Override
    public int getHealthPoint() {return 0;}
}