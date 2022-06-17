// FirstAid gameObject- when player touches this power-up, it gets its health set to maximum, i.e., regains his lost health

package com.example.randomwars.gameObjects;

import android.content.Context;
import android.graphics.Canvas;
import com.example.randomwars.GameDisplay;
import com.example.randomwars.GameLoop;
import com.example.randomwars.animation.Sprite;
import com.example.randomwars.animation.SpriteSheet;

public class FirstAid extends GameObjects{
    private static final double SPAWNS_PER_MINUTE = 2;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE / 60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWNS_PER_SECOND;
    private static double remainingUpdates = UPDATES_PER_SPAWN;

    private static final double UPDATES_BEFORE_TIMEOUT = GameLoop.MAX_UPS * 15;
    public double remainingUpdatesToTimeOut = UPDATES_BEFORE_TIMEOUT;

    Sprite firstAid;
    SpriteSheet spriteSheet;

    // Defining positions such that the bomb spawns within the playable game area.
    public FirstAid(Context context, Player player){
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
        firstAid = spriteSheet.getFirstAid();
    }

    // Method which returns true if it is time to spawn the next first aid and false otherwise
    public static boolean readyToSpawn() {
        if (remainingUpdates <= 0) {
            remainingUpdates += UPDATES_PER_SPAWN;
            return true;
        } else {
            remainingUpdates --;
            return false;
        }
    }

    // Method which returns true if it is time to remove the first aid from the screen if it hasn't interacted with the player
    public boolean timeOut(){
        if(remainingUpdatesToTimeOut == 0){
            return true;
        }
        remainingUpdatesToTimeOut--;
        return false;
    }

    // Method to draw the first aid to the screen
    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        firstAid.draw(
                canvas,
                (int) gameDisplay.coordinatesX(positionX) - firstAid.getWidth(),
                (int) gameDisplay.coordinatesY(positionY) - firstAid.getHeight()
        );
    }

    // Since the first aid is stationary, we don't need to override the update method
    @Override
    public void update(){}

    // Method to return health points, returning zero as first-aid has no health
    @Override
    public int getHealthPoint() {return 0;}
}