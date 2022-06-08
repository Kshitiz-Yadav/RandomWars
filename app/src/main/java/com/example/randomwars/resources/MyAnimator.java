package com.example.randomwars.resources;

import android.graphics.Canvas;

import com.example.randomwars.GameDisplay;
import com.example.randomwars.GameLoop;
import com.example.randomwars.gameObjects.Player;

public class MyAnimator {

    private Sprite[] playerSpriteArray;
    private int notMovingindex = 0;
    private int movingIndex = 1;
    private int remainingUpdates;
    private static final int MAX_UPDATES_BEFORE_NEXT_FRAME = (int) GameLoop.MAX_UPS / 6;


    public MyAnimator(Sprite[] playerSpriteArray){
        this.playerSpriteArray = playerSpriteArray;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay, Player player) {
        switch (player.getPlayerState().getState()){
            case NOT_MOVING:
                drawFrame(canvas, player, gameDisplay, playerSpriteArray[notMovingindex]);
                break;
            case STARTED_MOVING:
                remainingUpdates = MAX_UPDATES_BEFORE_NEXT_FRAME;
                drawFrame(canvas, player, gameDisplay, playerSpriteArray[movingIndex]);
                break;
            case IS_MOVING:
                remainingUpdates--;
                if(remainingUpdates == 0){
                    remainingUpdates = MAX_UPDATES_BEFORE_NEXT_FRAME;
                    changeMovingIndex();
                }
                drawFrame(canvas, player, gameDisplay, playerSpriteArray[movingIndex]);
        }
    }

    private void changeMovingIndex() {
        if(movingIndex == 1){
            movingIndex = 2;
        }
        else{
            movingIndex = 1;
        }
    }

    private void drawFrame(Canvas canvas, Player player, GameDisplay gameDisplay, Sprite sprite) {
        sprite.draw(canvas,
                (int) gameDisplay.coordinatesX(player.getPositionX()) - sprite.getWidth(),
                (int)gameDisplay.coordinatesY(player.getPositionY()) - sprite.getHeight()
        );
    }
}
