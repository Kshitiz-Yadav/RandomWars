// AtomBomb gameObject- when player touches this power-up, all enemies on the screen are killed, also giving the player points

package com.example.randomwars.gameObjects;

import android.content.Context;
import android.graphics.Canvas;
import com.example.randomwars.GameDisplay;
import com.example.randomwars.GameLoop;
import com.example.randomwars.animation.Sprite;
import com.example.randomwars.animation.SpriteSheet;

public class AtomBomb extends GameObjects{
    private static final double SPAWNS_PER_MINUTE = 1;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE / 60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWNS_PER_SECOND;
    private static double remainingUpdates = UPDATES_PER_SPAWN;

    private static final double UPDATES_BEFORE_TIMEOUT = GameLoop.MAX_UPS * 15;
    public double remainingUpdatesToTimeOut = UPDATES_BEFORE_TIMEOUT;

    Sprite atomBomb;
    SpriteSheet spriteSheet;

    // Defining positions such that the bomb spawns within the playable game area.
    public AtomBomb(Context context, Player player){
        super(
                Math.random() * 4000 + player.positionX - 2000,
                Math.random() * 2000 + player.positionY - 1000
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
        this.spriteSheet = new SpriteSheet(context);
        atomBomb = spriteSheet.getAtomBomb();
    }

    // Method which returns true if it is time to spawn the next bomb and false otherwise
    public static boolean readyToSpawn() {
        if (remainingUpdates <= 0) {
            remainingUpdates += UPDATES_PER_SPAWN;
            return true;
        } else {
            remainingUpdates --;
            return false;
        }
    }

    // Method which returns true if it is time to remove the bomb from the screen if it hasn't interacted with the player
    public boolean timeOut(){
        if(remainingUpdatesToTimeOut == 0){
            return true;
        }
        remainingUpdatesToTimeOut--;
        return false;
    }

    // Method to draw the bomb to the screen
    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        atomBomb.draw(
                canvas,
                (int) gameDisplay.coordinatesX(positionX) - atomBomb.getWidth(),
                (int) gameDisplay.coordinatesY(positionY) - atomBomb.getHeight()
        );
    }

    // Since the bomb is stationary, we don't need to override the update method
    @Override
    public void update() {}

    // Method to return health points, returning zero as bomb has no health
    @Override
    public int getHealthPoint() {return 0;}
}