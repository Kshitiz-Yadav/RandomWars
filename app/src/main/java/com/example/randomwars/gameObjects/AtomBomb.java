package com.example.randomwars.gameObjects;

import android.content.Context;
import android.graphics.Canvas;

import com.example.randomwars.GameDisplay;
import com.example.randomwars.GameLoop;
import com.example.randomwars.resources.Sprite;
import com.example.randomwars.resources.SpriteSheet;

public class AtomBomb extends GameObjects{

    private static final double SPAWNS_PER_MINUTE = 1;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE / 60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWNS_PER_SECOND;
    private static double remainingUpdates = UPDATES_PER_SPAWN;
    private static final double UPDATES_BEFORE_TIMEOUT = GameLoop.MAX_UPS * 15;
    public double remainingUpdatesToTimeOut = UPDATES_BEFORE_TIMEOUT;

    Sprite atomBomb;
    SpriteSheet spriteSheet;

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

    public static boolean readyToSpawn() {
        if (remainingUpdates <= 0) {
            remainingUpdates += UPDATES_PER_SPAWN;
            return true;
        } else {
            remainingUpdates --;
            return false;
        }
    }

    public boolean timeOut(){
        if(remainingUpdatesToTimeOut == 0){
            return true;
        }
        remainingUpdatesToTimeOut--;
        return false;
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        atomBomb.draw(
                canvas,
                (int) gameDisplay.coordinatesX(positionX) - atomBomb.getWidth(),
                (int) gameDisplay.coordinatesY(positionY) - atomBomb.getHeight()
        );
    }

    @Override
    public void update() {

    }

    @Override
    public int getHealthPoint() {
        return 0;
    }
}
