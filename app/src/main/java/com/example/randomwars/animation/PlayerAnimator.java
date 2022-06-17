// This class is used to add animation to the player by using an array of player sprites and switching the display sprite between them

package com.example.randomwars.animation;

import android.graphics.Canvas;
import com.example.randomwars.GameDisplay;
import com.example.randomwars.GameLoop;
import com.example.randomwars.gameObjects.Player;

public class PlayerAnimator {
    private static final int MAX_UPDATES_BEFORE_NEXT_FRAME = (int) GameLoop.MAX_UPS / 6;
    private int remainingUpdates;

    private final Sprite[] playerSpriteArray;
    private int movingIndex = 1;
    int notMovingIndex = 0;


    public PlayerAnimator(Sprite[] playerSpriteArray){this.playerSpriteArray = playerSpriteArray;}

    // Method to select the correct sprite of the player to be drawn on the screen and also updating moving sprite after given updates
    public void draw(Canvas canvas, GameDisplay gameDisplay, Player player) {
        switch (player.getPlayerState().getState()){
            case NOT_MOVING:
                drawFrame(canvas, player, gameDisplay, playerSpriteArray[notMovingIndex]);
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

    // Method to switch the moving sprite by changing index in spriteArray
    private void changeMovingIndex() {
        if(movingIndex == 1){
            movingIndex = 2;
        }
        else{
            movingIndex = 1;
        }
    }

    // Method to draw the chosen player sprite on the screen
    private void drawFrame(Canvas canvas, Player player, GameDisplay gameDisplay, Sprite sprite) {
        sprite.draw(canvas,
                (int) gameDisplay.coordinatesX(player.getPositionX()) - sprite.getWidth(),
                (int)gameDisplay.coordinatesY(player.getPositionY()) - sprite.getHeight()
        );
    }
}