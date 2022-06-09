package com.example.randomwars;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.randomwars.gameObjects.AtomBomb;
import com.example.randomwars.gameObjects.Bullet;
import com.example.randomwars.gameObjects.FirstAid;
import com.example.randomwars.gameObjects.GameObjects;
import com.example.randomwars.gameObjects.Player;
import com.example.randomwars.gameObjects.SoldierEnemy;
import com.example.randomwars.gameObjects.TankEnemy;
import com.example.randomwars.gamePanel.GameOver;
import com.example.randomwars.gamePanel.Joystick;
import com.example.randomwars.gamePanel.Performance;
import com.example.randomwars.map.TileMap;
import com.example.randomwars.resources.MyAnimator;
import com.example.randomwars.resources.SpriteSheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    private List<Bullet> bulletsList = new ArrayList<Bullet>();
    private int updatesBeforeNextBullet = (int) GameLoop.MAX_UPS / 3;
    private List<SoldierEnemy> soldierEnemyList = new ArrayList<SoldierEnemy>();
    private List<TankEnemy> tankEnemyList = new ArrayList<TankEnemy>();
    private List<Bullet> tankBulletList = new ArrayList<Bullet>();
    private List<FirstAid> firstAidList = new ArrayList<FirstAid>();
    private List<AtomBomb> atomBombList = new ArrayList<AtomBomb>();
    private GameOver gameOver;
    private SurfaceHolder surfaceHolder;
    private Context context;

    private final int KILL_SOLDIER_ENEMY_POINTS = 2;
    private final int KILL_TANK_ENEMY_POINTS = 5;
    private int score = 0;
    private int level = 1;

    public GameArea(Context context) {
        super(context);

        this.surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        this.context = context;

        gameLoop = new GameLoop(this, surfaceHolder);

        performance = new Performance(gameLoop, context);
        moveJoystick = new Joystick(350, 750, 150, 100);
        shootJoystick = new Joystick(1850, 750, 150, 100);

        spriteSheet = new SpriteSheet(context);
        animator = new MyAnimator(spriteSheet.getPlayerArray());
        player = new Player(500,500, context, moveJoystick, animator);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gameDisplay = new GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player);

        tileMap = new TileMap(spriteSheet);

        gameOver = new GameOver(context);

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

        if(player.getHealthPoint() <= 0){
            gameLoop.stopLoop();
            surfaceHolder.getSurface().release();
            synchronized (surfaceHolder) {
                ((Activity) context).finish();
            }
        }

        moveJoystick.update();
        shootJoystick.update();
        player.update();

        if(score >= level * 50){
            level++;
            incrementLevel();
        }

        if(shootJoystick.getIsPressed()){
            if(updatesBeforeNextBullet == 0){
                bulletsList.add(new Bullet(getContext(), player, shootJoystick.getActuatorX(), shootJoystick.getActuatorY()));
                updatesBeforeNextBullet = (int) GameLoop.MAX_UPS / 3;
            }
            else{
                updatesBeforeNextBullet--;
            }
        }
        for(Bullet bullet: bulletsList){
            bullet.update();
        }
        for(Bullet bullet: tankBulletList){
            bullet.update();
        }

        if(FirstAid.readyToSpawn()){
            firstAidList.add(new FirstAid(getContext(), player));
        }

        if(AtomBomb.readyToSpawn()){
            atomBombList.add(new AtomBomb(getContext(), player));
        }

        if(SoldierEnemy.readyToSpawn()){
            soldierEnemyList.add(new SoldierEnemy(getContext(), player));
        }
        for(SoldierEnemy soldierEnemy: soldierEnemyList){
            soldierEnemy.update();
        }

        if(TankEnemy.readyToSpawn()){
            tankEnemyList.add(new TankEnemy(getContext(), player));
        }
        for(TankEnemy tankEnemy: tankEnemyList){
            tankEnemy.update();
            if(tankEnemy.readyToShoot()){
                tankBulletList.add(new Bullet(getContext(),
                        tankEnemy,
                        (player.getPositionX() - tankEnemy.getPositionX()) / (GameObjects.getDistanceBetween(player, tankEnemy) * TankEnemy.bulletSpeedPoison),
                        (player.getPositionY() - tankEnemy.getPositionY()) / (GameObjects.getDistanceBetween(player, tankEnemy) * TankEnemy.bulletSpeedPoison)
                        )
                );
            }
        }

        Iterator<SoldierEnemy> iteratorSoldierEnemy = soldierEnemyList.iterator();
        while (iteratorSoldierEnemy.hasNext()) {
            SoldierEnemy soldierEnemy = iteratorSoldierEnemy.next();
            Iterator<Bullet> iteratorBullets = bulletsList.iterator();
            while (iteratorBullets.hasNext()) {
                Bullet bullet = iteratorBullets.next();
                if (soldierEnemy.isDead(bullet)) {
                    iteratorBullets.remove();
                    iteratorSoldierEnemy.remove();
                    score += KILL_SOLDIER_ENEMY_POINTS;
                    break;
                }
            }
            if(player.isHit(soldierEnemy)){
                iteratorSoldierEnemy.remove();
            }
        }

        Iterator<TankEnemy> iteratorTankEnemy = tankEnemyList.iterator();
        while (iteratorTankEnemy.hasNext()) {
            TankEnemy tankEnemy = iteratorTankEnemy.next();
            Iterator<Bullet> iteratorBullets = bulletsList.iterator();
            while (iteratorBullets.hasNext()) {
                Bullet bullet = iteratorBullets.next();
                if (tankEnemy.isDead(bullet)) {
                    iteratorBullets.remove();
                    iteratorTankEnemy.remove();
                    score += KILL_TANK_ENEMY_POINTS;
                    break;
                }
            }
            if(player.isHit(tankEnemy)){
                iteratorTankEnemy.remove();
            }
        }

        Iterator<Bullet> bulletIterator = bulletsList.iterator();
        while((bulletIterator.hasNext())){
            Bullet bullet = bulletIterator.next();
            if(bullet.timeOut()){
                bulletIterator.remove();
            }
        }

        Iterator<Bullet> tankBulletIterator = tankBulletList.iterator();
        while(tankBulletIterator.hasNext()){
            Bullet bullet = tankBulletIterator.next();
            if(bullet.timeOut()){
                tankBulletIterator.remove();
            }
            if(player.isHit(bullet)){
                tankBulletIterator.remove();
            }
        }

        Iterator<FirstAid> firstAidIterator = firstAidList.iterator();
        while(firstAidIterator.hasNext()){
            FirstAid firstAid = firstAidIterator.next();
            if(firstAid.timeOut()){
                firstAidIterator.remove();
            }
            if(player.isHit(firstAid)){
                firstAidIterator.remove();
            }
        }

        Iterator<AtomBomb> atomBombIterator = atomBombList.iterator();
        while(atomBombIterator.hasNext()){
            AtomBomb atomBomb = atomBombIterator.next();
            if(atomBomb.timeOut()){
                atomBombIterator.remove();
            }
            if(player.isHit(atomBomb)){
                atomBombIterator.remove();
                score += soldierEnemyList.size() * KILL_SOLDIER_ENEMY_POINTS + tankEnemyList.size() * KILL_TANK_ENEMY_POINTS;
                soldierEnemyList.clear();
                tankEnemyList.clear();
                tankBulletList.clear();
            }
        }

        gameDisplay.update();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        tileMap.draw(canvas, gameDisplay);

        player.draw(canvas, gameDisplay);

        for(FirstAid firstAid: firstAidList){
            firstAid.draw(canvas, gameDisplay);
        }

        for(AtomBomb atomBomb: atomBombList){
            atomBomb.draw(canvas, gameDisplay);
        }

        for(Bullet bullet: bulletsList){
            bullet.draw(canvas, gameDisplay);
        }

        for(Bullet bullet: tankBulletList){
            bullet.draw(canvas, gameDisplay);
        }

        for(SoldierEnemy soldierEnemy: soldierEnemyList){
            soldierEnemy.draw(canvas, gameDisplay);
        }

        for(TankEnemy tankEnemy: tankEnemyList){
            tankEnemy.draw(canvas, gameDisplay);
        }

        moveJoystick.draw(canvas);
        shootJoystick.draw(canvas);
        performance.draw(canvas);

        String scoreString = Integer.toString(score);
        String levelString = Integer.toString(level);
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);
        paint.setColor(color);
        paint.setTextSize(40);
        canvas.drawText("LEVEL:  " + levelString, 1600, 100, paint);
        canvas.drawText("SCORE: " + scoreString, 1800, 100, paint);

        if(player.getHealthPoint() <= 0){
            gameOver.draw(canvas);
        }
    }


    private void incrementLevel() {
        SoldierEnemy.incrementLevel();
        TankEnemy.incrementLevel();
    }

    public void pause() {
        gameLoop.stopLoop();
    }

//    public void resume(){
//        gameLoop.resumeLoop();
//    }
}
