// This is the TankEnemy class which is responsible for the functionalities of the tank enemy.

package com.example.randomwars.gameObjects;

import android.content.Context;
import android.graphics.Canvas;
import com.example.randomwars.GameDisplay;
import com.example.randomwars.GameLoop;
import com.example.randomwars.gamePanel.HealthBar;
import com.example.randomwars.animation.Sprite;
import com.example.randomwars.animation.SpriteSheet;

public class TankEnemy extends GameObjects{
    public static double SPEED_PIXELS_PER_SECOND = 100.0;
    private static double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    private static double SPAWNS_PER_MINUTE = 2;
    private static double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE / 60.0;
    private static double UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWNS_PER_SECOND;
    private static double remainingUpdates = UPDATES_PER_SPAWN;

    private final int MAX_UPDATES_BEFORE_NEXT_FRAME = (int) GameLoop.MAX_UPS / 6;
    private int remainingUpdatesForFrameChange = MAX_UPDATES_BEFORE_NEXT_FRAME;

    private final int MAX_UPDATES_TO_SHOOT = (int) GameLoop.MAX_UPS;
    private int remainingUpdatesToShoot = MAX_UPDATES_TO_SHOOT;

    private final Player player;
    private final Sprite[] enemySpriteArray;
    private int spriteIndex = 0;
    private final HealthBar healthBar;
    private int healthPoints;
    public static double bulletSpeedPoison = 2;

    // Defining positions such that the soldier spawns within the playable game area.
    public TankEnemy(Context context, Player player){
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

        SpriteSheet spriteSheet = new SpriteSheet(context);
        enemySpriteArray = spriteSheet.getTankEnemyArray();
        this.player = player;
        healthBar = new HealthBar(context, this);
        MAX_HEALTH_POINTS = 5;
        healthPoints = MAX_HEALTH_POINTS;
    }

    // Method which returns true if it is time to spawn the next tank and false otherwise
    public static boolean readyToSpawn() {
        if (remainingUpdates <= 0) {
            remainingUpdates += UPDATES_PER_SPAWN;
            return true;
        } else {
            remainingUpdates --;
            return false;
        }
    }

    // Method to make spawning of tanks faster, increase their speed and increase their bullet speed on level up
    public static void incrementLevel() {
        SPAWNS_PER_MINUTE += 2;
        SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE / 60.0;
        UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWNS_PER_SECOND;

        SPEED_PIXELS_PER_SECOND += 2;
        MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
        if(bulletSpeedPoison > 0.5){
            bulletSpeedPoison -= 0.2;
        }
    }

    // Setting the speeds and spawn count to default once the game gets over
    public static void resetLevel(){
        SPAWNS_PER_MINUTE = 2;
        SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE / 60.0;
        UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWNS_PER_SECOND;
        remainingUpdates = UPDATES_PER_SPAWN;

        SPEED_PIXELS_PER_SECOND = 100.0;
        MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
        bulletSpeedPoison = 2;
    }

    /*
        Method to check if the tank has been hit by a bullet on not
        If the tank is not hit, nothing happens and false is returned
        If the tank is hit then its healths gets decremented and false is returned
        If the tank is hit and its health depletes completely, true is returned
    */
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

    // Method to draw tank's healthBar on screen and choose the tank frame to be drawn which changes after a certain number of updates
    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        remainingUpdatesForFrameChange--;
        if(remainingUpdatesForFrameChange == 0){
            remainingUpdatesForFrameChange = MAX_UPDATES_BEFORE_NEXT_FRAME;
            // Moving to the next frame
            spriteIndex = (spriteIndex + 1) % 3;
        }
        drawFrame(canvas, gameDisplay, enemySpriteArray[spriteIndex]);

        healthBar.draw(canvas, gameDisplay);
    }

    // This method draws the chosen soldier sprite on the screen
    private void drawFrame(Canvas canvas, GameDisplay gameDisplay, Sprite sprite) {
        sprite.draw(canvas,
                (int) gameDisplay.coordinatesX(positionX) - sprite.getWidth(),
                (int) gameDisplay.coordinatesY(positionY) - sprite.getHeight()
        );
    }

    // Method which returns true if it is time to spawn the next tank bullet and false otherwise
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

    // Method to update the position of the soldier on the screen so that it appears moving
    @Override
    public void update() {
        // Calculating vector from enemy to player
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;

        // Calculating absolute distance between soldier and player
        double distanceToPlayer = GameObjects.getDistanceBetween(this, player);

        // Calculating direction from enemy to player
        double directionX = distanceToPlayerX/distanceToPlayer;
        double directionY = distanceToPlayerY/distanceToPlayer;

        // Setting velocity in the direction to the player
        if(distanceToPlayer > 0) {
            velocityX = directionX * MAX_SPEED;
            velocityY = directionY * MAX_SPEED;
        }
        else{
            velocityX = 0;
            velocityY = 0;
        }

        // Updating the position
        positionX += velocityX;
        positionY += velocityY;
    }

    // Method to return health points, used to draw health bars
    @Override
    public int getHealthPoint() {return healthPoints;}
}