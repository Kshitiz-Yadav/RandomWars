package com.example.randomwars.gameObjects;

import android.graphics.Canvas;

import com.example.randomwars.GameDisplay;
import com.example.randomwars.GameLoop;
import com.example.randomwars.gamePanel.Joystick;
import com.example.randomwars.resources.MyAnimator;
import com.example.randomwars.resources.SpriteSheet;

public class Player extends GameObjects{

    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private final MyAnimator animator;
    private final Joystick moveJoystick;
    private PlayerState playerState;
    private final int MAX_HEALTH_POINTS = 10;
    private int healthPoints = MAX_HEALTH_POINTS;

    public Player(double positionX, double positionY, Joystick moveJoystick,MyAnimator animator){
        super(positionX, positionY);
        this.animator = animator;
        this.moveJoystick = moveJoystick;
        this.playerState = new PlayerState(this);
    }

    public boolean isHit(GameObjects object){
        if((object.positionX > positionX - SpriteSheet.SPRITE_WIDTH_PIXELS && object.positionX < positionX + SpriteSheet.SPRITE_WIDTH_PIXELS)
                && (object.positionY > positionY - SpriteSheet.SPRITE_HEIGHT_PIXELS && object.positionY < positionY + SpriteSheet.SPRITE_HEIGHT_PIXELS)){
            switch(object.getClass().getName()){
                case "SoldierEnemy":
                    healthPoints -= 2;
                    break;
                case "TankEnemy":
                    healthPoints -= 5;
                    break;
                case "Bullet":
                    healthPoints -= 1;
                    break;
                default:
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        animator.draw(canvas, gameDisplay, this);
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
