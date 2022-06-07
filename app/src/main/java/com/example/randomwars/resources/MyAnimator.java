package com.example.randomwars.resources;

import android.graphics.Canvas;

import com.example.randomwars.gameObjects.Player;

public class MyAnimator {

    private Sprite[] playerSpriteArray;
    private int notMovingindex = 0;
    private int movingIndex = 1;
    private int remainingUpdates;
    private static final int MAX_UPDATES_BEFORE_NEXT_FRAME = 5;


    public MyAnimator(Sprite[] playerSpriteArray){
        this.playerSpriteArray = playerSpriteArray;
    }

    public void draw(Canvas canvas, Player player) {
        switch (player.getPlayerState().getState()){
            case NOT_MOVING:
                drawFrame(canvas, player, playerSpriteArray[notMovingindex]);
                break;
            case STARTED_MOVING:
                remainingUpdates = MAX_UPDATES_BEFORE_NEXT_FRAME;
                drawFrame(canvas, player, playerSpriteArray[movingIndex]);
                break;
            case IS_MOVING:
                remainingUpdates--;
                if(remainingUpdates == 0){
                    remainingUpdates = MAX_UPDATES_BEFORE_NEXT_FRAME;
                    changeMovingIndex();
                }
                drawFrame(canvas, player, playerSpriteArray[movingIndex]);
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

    private void drawFrame(Canvas canvas, Player player, Sprite sprite) {
        sprite.draw(canvas, (int) player.getPositionX(), (int) player.getPositionY());
    }
}
