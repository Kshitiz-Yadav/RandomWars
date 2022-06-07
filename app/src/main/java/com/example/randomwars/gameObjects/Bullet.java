package com.example.randomwars.gameObjects;

import android.content.Context;
import android.graphics.Canvas;

import com.example.randomwars.GameLoop;
import com.example.randomwars.resources.Sprite;
import com.example.randomwars.resources.SpriteSheet;

public class Bullet extends GameObjects {

    public static final double SPEED_PIXELS_PER_SECOND = 800.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    Sprite bullet;
    SpriteSheet spriteSheet;

    public Bullet(Context context, Player player, double actuatorX, double actuatorY){
        super(player.getPositionX(), player.getPositionY());
        this.spriteSheet = new SpriteSheet(context);
        this.bullet = spriteSheet.getBulletSprite();
        this.velocityX = actuatorX * MAX_SPEED;
        this.velocityY = actuatorY * MAX_SPEED;
    }

    @Override
    public void draw(Canvas canvas) {
        bullet.draw(canvas, (int) positionX, (int) positionY);
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

    private double getDistanceBetweenPoints(double x, double y, double velocityX, double velocityY) {
        return Math.sqrt((Math.pow(x - velocityX, 2)) + (Math.pow(y - velocityY, 2)));
    }
}
