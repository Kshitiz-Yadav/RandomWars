package com.example.randomwars.gameObjects;

import android.graphics.Canvas;

import com.example.randomwars.GameLoop;
import com.example.randomwars.gamePanel.Joystick;
import com.example.randomwars.resources.MyAnimator;

public class Player extends GameObjects{

    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private MyAnimator animator;
    private Joystick moveJoystick;
    private PlayerState playerState;

    public Player(double positionX, double positionY, Joystick moveJoystick,MyAnimator animator){
        super(positionX, positionY);
        this.animator = animator;
        this.moveJoystick = moveJoystick;
        this.playerState = new PlayerState(this);
    }

    @Override
    public void draw(Canvas canvas) {
        animator.draw(canvas, this);
    }

    @Override
    public void update() {
        // Update velocity based on actuator of joystick
        velocityX = moveJoystick.getActuatorX()*MAX_SPEED;
        velocityY = moveJoystick.getActuatorY()*MAX_SPEED;

        // Update position
        positionX += velocityX;
        positionY += velocityY;

        // Update direction
        if (velocityX != 0 || velocityY != 0) {
            // Normalize velocity to get direction (unit vector of velocity)
            double distance = getDistanceBetweenPoints(0, 0, velocityX, velocityY);
            directionX = velocityX/distance;
            directionY = velocityY/distance;
        }

        playerState.update();
    }

    private double getDistanceBetweenPoints(double x, double y, double velocityX, double velocityY) {
        return Math.sqrt((Math.pow(x - velocityX, 2)) + (Math.pow(y - velocityY, 2)));
    }

    public PlayerState getPlayerState(){
        return playerState;
    }
}
