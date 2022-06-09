package com.example.randomwars.gameObjects;

import android.content.Context;
import android.graphics.Canvas;

import com.example.randomwars.GameDisplay;
import com.example.randomwars.GameLoop;
import com.example.randomwars.gamePanel.HealthBar;
import com.example.randomwars.resources.Sprite;
import com.example.randomwars.resources.SpriteSheet;

public class SoldierEnemy extends GameObjects{

    public static double SPEED_PIXELS_PER_SECOND = 250.0;
    private static double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private static double SPAWNS_PER_MINUTE = 10;
    private static double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE / 60.0;
    private static double UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWNS_PER_SECOND;
    private static double remainingUpdates = UPDATES_PER_SPAWN;
    private final Player player;
    Sprite[] enemySpriteArray;
    SpriteSheet spriteSheet;
    private int spriteIndex = 0;
    private final int MAX_UPDATES_BEFORE_NEXT_FRAME = (int) GameLoop.MAX_UPS / 6;
    private int remainingUpdatesForFrameChange = MAX_UPDATES_BEFORE_NEXT_FRAME;
    private HealthBar healthBar;
    private int healthPoints;

    public SoldierEnemy(Context context, Player player){
        super(
                Math.random() * 2000 + player.positionX - 1000,
                Math.random() * 1000 + player.positionY - 500
        );
        if(positionX < MIN_POS_X){
            positionX = MIN_POS_X + OFFSET;
        }
        else if(positionX > MAX_POS_X){
            positionX = MAX_POS_X - OFFSET;
        }
        if(positionY < MIN_POS_Y){
            positionY = MIN_POS_Y + OFFSET;
        }
        else if(positionY > MAX_POS_Y){
            positionY = MAX_POS_Y - OFFSET;
        }
        this.player = player;
        spriteSheet = new SpriteSheet(context);
        enemySpriteArray = spriteSheet.getSoldierEnemyArray();
        healthBar = new HealthBar(context, this);
        MAX_HEALTH_POINTS = 2;
        healthPoints = MAX_HEALTH_POINTS;
    }

    public static boolean readyToSpawn() {
        if (remainingUpdates <= 0) {
            remainingUpdates += UPDATES_PER_SPAWN;
            return true;
        } else {
            remainingUpdates --;
            return false;
        }
    }

    public static void incrementLevel() {
        SPAWNS_PER_MINUTE += 2;
        SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE / 60.0;
        UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWNS_PER_SECOND;

        SPEED_PIXELS_PER_SECOND += 2;
        MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    }

    public boolean isDead(Bullet bullet){
        if((bullet.positionX > positionX - bullet.bullet.getWidth() && bullet.positionX < positionX + enemySpriteArray[0].getWidth())
            && (bullet.positionY > positionY - bullet.bullet.getHeight() && bullet.positionY < positionY + enemySpriteArray[0].getHeight())
            && healthPoints == 1){
            return true;
        }
        else if((bullet.positionX > positionX - bullet.bullet.getWidth() && bullet.positionX < positionX + enemySpriteArray[0].getWidth())
                && (bullet.positionY > positionY - bullet.bullet.getHeight() && bullet.positionY < positionY + enemySpriteArray[0].getHeight())){
            healthPoints--;
            bullet.remainingUpdates = 0;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        if(remainingUpdatesForFrameChange == 0){
            remainingUpdatesForFrameChange = MAX_UPDATES_BEFORE_NEXT_FRAME;
            changeMovingIndex();
        }
        remainingUpdatesForFrameChange--;
        drawFrame(canvas, gameDisplay, enemySpriteArray[spriteIndex]);

        healthBar.draw(canvas, gameDisplay);
    }

    private void changeMovingIndex() {
        spriteIndex = (spriteIndex + 1) % 2;
    }

    private void drawFrame(Canvas canvas, GameDisplay gameDisplay, Sprite sprite) {
        sprite.draw(canvas,
                (int) gameDisplay.coordinatesX(positionX) - sprite.getWidth(),
                (int) gameDisplay.coordinatesY(positionY) - sprite.getHeight()
        );
    }

    @Override
    public void update() {
        // Calculate vector from enemy to player (in x and y)
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;

        // Calculate (absolute) distance between enemy (this) and player
        double distanceToPlayer = GameObjects.getDistanceBetween(this, player);

        // Calculate direction from enemy to player
        double directionX = distanceToPlayerX/distanceToPlayer;
        double directionY = distanceToPlayerY/distanceToPlayer;

        // Set velocity in the direction to the player
        if(distanceToPlayer > 0) { // Avoid division by zero
            velocityX = directionX*MAX_SPEED;
            velocityY = directionY*MAX_SPEED;
        } else {
            velocityX = 0;
            velocityY = 0;
        }

        positionX += velocityX;
        positionY += velocityY;
    }

    @Override
    public int getHealthPoint() {
        return healthPoints;
    }
}
