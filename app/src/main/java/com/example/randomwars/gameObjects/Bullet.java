package com.example.randomwars.gameObjects;

import android.content.Context;
import android.graphics.Canvas;

import com.example.randomwars.GameDisplay;
import com.example.randomwars.GameLoop;
import com.example.randomwars.resources.Sprite;
import com.example.randomwars.resources.SpriteSheet;

public class Bullet extends GameObjects {

    public static final double SPEED_PIXELS_PER_SECOND = 800.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private static final double UPDATES_BEFORE_TIMEOUT = GameLoop.MAX_UPS * 5;
    public double remainingUpdates = UPDATES_BEFORE_TIMEOUT;

    public Sprite bullet;
    SpriteSheet spriteSheet;

    public Bullet(Context context, GameObjects object, double actuatorX, double actuatorY){
        super(object.getPositionX(), object.getPositionY());
        this.spriteSheet = new SpriteSheet(context);
        this.bullet = spriteSheet.getBulletSprite();
        this.velocityX = actuatorX * MAX_SPEED;
        this.velocityY = actuatorY * MAX_SPEED;
    }

    public boolean timeOut(){
        if(remainingUpdates == 0){
            return true;
        }
        remainingUpdates--;
        return false;
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        bullet.draw(canvas,
                (int) gameDisplay.coordinatesX(positionX) - bullet.getWidth(),
                (int) gameDisplay.coordinatesY(positionY) - bullet.getHeight()
        );
    }

    @Override
    public void update() {

        // Update position
        positionX += velocityX;
        positionY += velocityY;

        // Update direction
        if (velocityX != 0 || velocityY != 0) {
            // Normalize velocity to get direction (unit vector of velocity)
            double distance = getDistanceBetweenPoints(0, 0, velocityX, velocityY);
            directionX = velocityX / distance;
            directionY = velocityY / distance;
        }
    }

    @Override
    public int getHealthPoint() {
        return 0;
    }

    private double getDistanceBetweenPoints(double x, double y, double velocityX, double velocityY) {
        return Math.sqrt((Math.pow(x - velocityX, 2)) + (Math.pow(y - velocityY, 2)));
    }
}
