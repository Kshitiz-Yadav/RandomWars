package com.example.randomwars;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.randomwars.gameObjects.Player;
import com.example.randomwars.gamePanel.Performance;
import com.example.randomwars.map.TileMap;
import com.example.randomwars.resources.SpriteSheet;

public class GameArea extends SurfaceView implements SurfaceHolder.Callback {

    private Player player;
    private TileMap tileMap;
    private SpriteSheet spriteSheet;
    private GameLoop gameLoop;
    private Performance performance;
    private GameDisplay gameDisplay;

    public GameArea(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        performance = new Performance(gameLoop, context);

//        player = new Player();

//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        gameDisplay = new GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player);

//        tileMap = new TileMap(spriteSheet);

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Log.d("GameArea: ", "surfaceCreated() called");
        if(gameLoop.getState().equals(Thread.State.TERMINATED)){
            SurfaceHolder surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
            gameLoop = new GameLoop(this, surfaceHolder);
        }
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d("GameArea: ", "surfaceChanged() called");
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.d("GameArea: ", "surfaceDestroyed() called");
    }

    public void update() {

    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        performance.draw(canvas);
    }


    public void pause() {
        gameLoop.stopLoop();
    }

//    public void resume(){
//        gameLoop.resumeLoop();
//    }
}
