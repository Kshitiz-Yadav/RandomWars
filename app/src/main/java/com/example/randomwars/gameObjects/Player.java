// This is the player class which is responsible for the functionalities of the main player

package com.example.randomwars.gameObjects;

import android.content.Context;
import android.graphics.Canvas;
import com.example.randomwars.GameDisplay;
import com.example.randomwars.GameLoop;
import com.example.randomwars.gamePanel.HealthBar;
import com.example.randomwars.gamePanel.Joystick;
import com.example.randomwars.animation.PlayerAnimator;
import com.example.randomwars.animation.SpriteSheet;

public class Player extends GameObjects{
    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    private final PlayerAnimator animator;
    private final Joystick moveJoystick;
    private final PlayerState playerState;
    private final HealthBar healthBar;
    private int healthPoints;

    public Player(double positionX, double positionY, Context context, Joystick moveJoystick, PlayerAnimator animator){
        super(positionX, positionY);
        this.animator = animator;
        this.moveJoystick = moveJoystick;
        this.playerState = new PlayerState(this);
        healthBar = new HealthBar(context, this);
        MAX_HEALTH_POINTS = 10;
        healthPoints = MAX_HEALTH_POINTS;
    }

    // This method is called when the player makes contact with another gameObject on screen, accordingly, the functionalities have been defined
    public boolean isHit(GameObjects object){
        if((object.positionX > positionX - SpriteSheet.SPRITE_WIDTH_PIXELS && object.positionX < positionX + SpriteSheet.SPRITE_WIDTH_PIXELS)
                && (object.positionY > positionY - SpriteSheet.SPRITE_HEIGHT_PIXELS && object.positionY < positionY + SpriteSheet.SPRITE_HEIGHT_PIXELS)){
            switch(object.getClass().getName()){
                case "com.example.randomwars.gameObjects.SoldierEnemy":
                    healthPoints = healthPoints > 2 ? healthPoints - 2 : 0;
                    break;
                case "com.example.randomwars.gameObjects.TankEnemy":
                    healthPoints = healthPoints > 5 ? healthPoints - 5 : 0;
                    break;
                case "com.example.randomwars.gameObjects.Bullet":
                    healthPoints = healthPoints > 1 ? healthPoints - 1 : 0;
                    break;
                case "com.example.randomwars.gameObjects.FirstAid":
                    healthPoints = MAX_HEALTH_POINTS;
                default:
                    break;
            }
            return true;
        }
        return false;
    }

    // Method to draw the player to the screen
    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        animator.draw(canvas, gameDisplay, this);
        healthBar.draw(canvas, gameDisplay);
    }

    // Method to update the position of the player on the screen so that it appears moving
    @Override
    public void update() {
        // Updating velocity according to actuator of joystick
        velocityX = moveJoystick.getActuatorX()*MAX_SPEED;
        velocityY = moveJoystick.getActuatorY()*MAX_SPEED;

        // Updating position
        if((positionX > MIN_POS_X && positionX < MAX_POS_X) ||
                (positionX < MAX_POS_X && positionX > MIN_POS_X - OFFSET && velocityX > 0) ||
                (positionX > MIN_POS_X && positionX < MAX_POS_X + OFFSET && velocityX < 0)){
            positionX += velocityX;
        }
        if((positionY > MIN_POS_Y && positionY < MAX_POS_Y) ||
                (positionY < MAX_POS_Y && positionY > MIN_POS_Y - OFFSET && velocityY > 0) ||
                (positionY > MIN_POS_Y && positionY < MAX_POS_Y + OFFSET && velocityY < 0)){
            positionY += velocityY;
        }

        // Updating direction using vector algebra
        if (velocityX != 0 || velocityY != 0) {
            double distance = getDistanceBetweenPoints(velocityX, velocityY);
            directionX = velocityX/distance;
            directionY = velocityY/distance;
        }

        playerState.update();
    }

    // Method to return the health points of the player to display or check game over condition
    @Override
    public int getHealthPoint() {return healthPoints;}

    // Method to calculate distance between origin and player to find directions
    private double getDistanceBetweenPoints(double velocityX, double velocityY) {
        int originX = 0;
        int originY = 0;
        return Math.sqrt((Math.pow(originX - velocityX, 2)) + (Math.pow(originY - velocityY, 2)));
    }

    // Method to get the moving state of player, used by animator
    public PlayerState getPlayerState() {return playerState;}
}