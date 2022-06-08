package com.example.randomwars.gameObjects;

import android.content.Context;
import android.graphics.Canvas;

import com.example.randomwars.GameDisplay;
import com.example.randomwars.GameLoop;
import com.example.randomwars.resources.Sprite;
import com.example.randomwars.resources.SpriteSheet;

public class TankEnemy extends GameObjects{

    public static final double SPEED_PIXELS_PER_SECOND = 100.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private static final double SPAWNS_PER_MINUTE = 2;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE / 60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWNS_PER_SECOND;
    private static double remainingUpdates = UPDATES_PER_SPAWN;
    private final Player player;
    Sprite[] enemySpriteArray;
    SpriteSheet spriteSheet;
    private int spriteIndex = 0;
    private final int MAX_UPDATES_BEFORE_NEXT_FRAME = (int) GameLoop.MAX_UPS / 6;
    private int remainingUpdatesForFrameChange = MAX_UPDATES_BEFORE_NEXT_FRAME;
    private int healthPoints = 5;
    private final int MAX_UPDATES_TO_SHOOT = (int) GameLoop.MAX_UPS;
    private int remainingUpdatesToShoot = MAX_UPDATES_TO_SHOOT;

    public TankEnemy(Context context, Player player){
        super(
                Math.random() * 2000 + player.positionX - 1000,
                Math.random() * 1000 + player.positionY - 500
        );
        this.player = player;
        spriteSheet = new SpriteSheet(context);
        enemySpriteArray = spriteSheet.getTankEnemyArray();
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

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        remainingUpdatesForFrameChange--;
        if(remainingUpdatesForFrameChange == 0){
            remainingUpdatesForFrameChange = MAX_UPDATES_BEFORE_NEXT_FRAME;
            changeMovingIndex();
        }
        drawFrame(canvas, gameDisplay, enemySpriteArray[spriteIndex]);

    }

    private void changeMovingIndex() {
        spriteIndex = (spriteIndex + 1) % 3;
    }

    private void drawFrame(Canvas canvas, GameDisplay gameDisplay, Sprite sprite) {
        sprite.draw(canvas,
                (int) gameDisplay.coordinatesX(positionX) - sprite.getWidth(),
                (int) gameDisplay.coordinatesY(positionY) - sprite.getHeight()
        );
    }

    public boolean readyToShoot(){
        if(remainingUpdatesToShoot == 0){
            remainingUpdatesToShoot = MAX_UPDATES_TO_SHOOT;
            return true;
        }
        else{
            remainingUpdatesToShoot--;
        }
        return false;
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

    public boolean isDead(Bullet bullet) {
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
}
