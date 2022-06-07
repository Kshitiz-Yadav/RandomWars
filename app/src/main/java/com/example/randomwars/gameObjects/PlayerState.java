package com.example.randomwars.gameObjects;

public class PlayerState {

    public enum State{
        NOT_MOVING,
        STARTED_MOVING,
        IS_MOVING
    }

    private Player player;
    private State state;

    public PlayerState(Player player){
        this.player = player;
        this.state = State.NOT_MOVING;
    }

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

    public State getState(){
        return state;
    }
}
