/*
    This class defines the state the player is in, on the basis of this state, the sprite to be drawn is chosen.
    The states are:
        NOT_MOVING- When the player is standing still, the "standing still" sprite is drawn on the screen
        IS_MOVING- When the player is moving, the two "moving" sprites are drawn on the screen on after another
        STARTED_MOVING- To smooth out the transition between NOT_MOVING and IS_MOVING state, occurs for only one update (1/30th of a second)
 */

package com.example.randomwars.gameObjects;

public class PlayerState {

    public enum State{
        NOT_MOVING,
        STARTED_MOVING,
        IS_MOVING
    }

    private final Player player;
    private State state;

    // Initial state is set to NOT_MOVING
    public PlayerState(Player player){
        this.player = player;
        this.state = State.NOT_MOVING;
    }

    // The update method constantly updates the state of player depending on its velocity
    public void update(){
        switch (state){
            case NOT_MOVING:
                if(player.velocityX != 0 || player.velocityY != 0){
                    state = State.STARTED_MOVING;
                }
                break;
            case STARTED_MOVING:
                if(player.velocityX != 0 || player.velocityY != 0){
                    state = State.IS_MOVING;
                }
                break;
            case IS_MOVING:
                if (player.velocityX == 0 && player.velocityY == 0){
                    state = State.NOT_MOVING;
                }
                break;
            default:
                break;
        }
    }

    // Returns the current state of the player to the animator so that sprite is drawn accordingly
    public State getState(){return state;}
}