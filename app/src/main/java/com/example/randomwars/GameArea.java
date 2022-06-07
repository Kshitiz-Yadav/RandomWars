package com.example.randomwars;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.randomwars.gameObjects.Player;
import com.example.randomwars.gamePanel.Joystick;
import com.example.randomwars.gamePanel.Performance;
import com.example.randomwars.map.TileMap;
import com.example.randomwars.resources.MyAnimator;
import com.example.randomwars.resources.SpriteSheet;

public class GameArea extends SurfaceView implements SurfaceHolder.Callback {

    private Player player;
    private TileMap tileMap;
    private SpriteSheet spriteSheet;
    private GameLoop gameLoop;
    private Performance performance;
    private MyAnimator animator;
    private GameDisplay gameDisplay;
    private Joystick moveJoystick;
    private int pointerCounter;
    private int moveJoystickPointerID = 0;
    private int shootJoystickPointerID = 0;
    private Joystick shootJoystick;

    public GameArea(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        performance = new Performance(gameLoop, context);
        moveJoystick = new Joystick(350, 750, 150, 75);
        shootJoystick = new Joystick(1850, 750, 150, 75);

        spriteSheet = new SpriteSheet(context);
        animator = new MyAnimator(spriteSheet.getPlayerArray());
        player = new Player(500,500, moveJoystick, animator);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pointerCounter = event.getPointerCount();
        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                for(int i=0;i<pointerCounter;i++){
                    if (moveJoystick.isPressed((double) event.getX(i), (double) event.getY(i))) {
                        // Joystick is pressed in this event -> setIsPressed(true) and store pointer id
                        moveJoystickPointerID = event.getPointerId(i);
                        moveJoystick.setIsPressed(true);
                    }
                    else if (shootJoystick.isPressed((double) event.getX(i), (double) event.getY(i))) {
                        // Joystick is pressed in this event -> setIsPressed(true) and store pointer id
                        shootJoystickPointerID = event.getPointerId(i);
                        shootJoystick.setIsPressed(true);
                    }
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                for(int i=0;i<pointerCounter;i++){
                    if (moveJoystick.getIsPressed() && moveJoystickPointerID == event.getPointerId(i)) {
                        // Joystick was pressed previously and is now moved
                        moveJoystick.setActuator((double) event.getX(i), (double) event.getY(i));
                    }
                    else if (shootJoystick.getIsPressed() && shootJoystickPointerID == event.getPointerId(i)) {
                        // Joystick was pressed previously and is now moved
                        shootJoystick.setActuator((double) event.getX(i), (double) event.getY(i));
                    }
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (moveJoystickPointerID == event.getPointerId(event.getActionIndex())) {
                    // joystick pointer was let go off -> setIsPressed(false) and resetActuator()
                    moveJoystick.setIsPressed(false);
                    moveJoystick.resetActuator();
                }
                if (shootJoystickPointerID == event.getPointerId(event.getActionIndex())) {
                    // joystick pointer was let go off -> setIsPressed(false) and resetActuator()
                    shootJoystick.setIsPressed(false);
                    shootJoystick.resetActuator();
                }
                return true;
        }
        return super.onTouchEvent(event);
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
        moveJoystick.update();
        shootJoystick.update();
        player.update();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        player.draw(canvas);
        moveJoystick.draw(canvas);
        shootJoystick.draw(canvas);
        performance.draw(canvas);
    }


    public void pause() {
        gameLoop.stopLoop();
    }

//    public void resume(){
//        gameLoop.resumeLoop();
//    }
}
