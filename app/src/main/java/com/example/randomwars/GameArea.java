/*
    This is the main class which binds the entire game together
    The update and draw methods here are responsible for calling the update and draw methods of all the objects and the map
    The touch events of the joysticks are handled here
 */

package com.example.randomwars;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.randomwars.gamePanel.Joystick;
import com.example.randomwars.gamePanel.Performance;
import com.example.randomwars.map.TileMap;
import com.example.randomwars.animation.PlayerAnimator;
import com.example.randomwars.animation.SpriteSheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameArea extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoop gameLoop;
    private final Player player;
    private final TileMap tileMap;
    private final Performance performance;
    private final GameDisplay gameDisplay;

    private final Joystick moveJoystick;
    private final Joystick shootJoystick;
    private int moveJoystickPointerID = 0;
    private int shootJoystickPointerID = 0;

    private int score = 0;
    private int level = 1;
    private final Context context;
    private final SoundPlayer soundPlayer;

    private final List<Bullet> bulletsList = new ArrayList<>();
    private final List<SoldierEnemy> soldierEnemyList = new ArrayList<>();
    private final List<TankEnemy> tankEnemyList = new ArrayList<>();
    private final List<Bullet> tankBulletList = new ArrayList<>();
    private final List<FirstAid> firstAidList = new ArrayList<>();
    private final List<AtomBomb> atomBombList = new ArrayList<>();
    private int updatesBeforeNextBullet = (int) GameLoop.MAX_UPS / 3;

    // Creating all required objects in the constructor
    public GameArea(Context context) {
        super(context);
        this.context = context;

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        performance = new Performance(context);
        moveJoystick = new Joystick((int)(displayMetrics.widthPixels * 0.15), (int)(displayMetrics.heightPixels * 0.70), (int)Math.sqrt(displayMetrics.widthPixels * displayMetrics.heightPixels * 0.014), (int)Math.sqrt(displayMetrics.widthPixels * displayMetrics.heightPixels * 0.0040));
        shootJoystick = new Joystick((int)(displayMetrics.widthPixels * 0.83), (int)(displayMetrics.heightPixels * 0.70), (int)Math.sqrt(displayMetrics.widthPixels * displayMetrics.heightPixels * 0.014), (int)Math.sqrt(displayMetrics.widthPixels * displayMetrics.heightPixels * 0.0040));

        SpriteSheet spriteSheet = new SpriteSheet(context);
        PlayerAnimator animator = new PlayerAnimator(spriteSheet.getPlayerArray());
        player = new Player(
                Math.random() * (GameObjects.MAX_POS_X - GameObjects.MIN_POS_X - 2 * GameObjects.OFFSET) + GameObjects.MIN_POS_X + GameObjects.OFFSET,
                Math.random() * (GameObjects.MAX_POS_Y - GameObjects.MIN_POS_Y - 2 * GameObjects.OFFSET) + GameObjects.MIN_POS_Y + GameObjects.OFFSET,
                context,
                moveJoystick,
                animator
        );

        gameDisplay = new GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player);

        tileMap = new TileMap(spriteSheet);

        soundPlayer = new SoundPlayer(context);

        setFocusable(true);
    }

    /*
        Method to handle the onTouch events
        Pointer IDs have been used to handle multi-touch events
            On Action Down: When a joystick is clicked, its isPressed is set to true and pointerID is stored
            On Action Move: Then if the joystick is moved around while isPressed is true,
                            the actuator values get update and the player moves or shoots accordingly
            On Action Up:   When the joystick is released, isPressed is set to false and the actuator is reset to 0
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerCounter = event.getPointerCount();
        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                for(int i = 0; i< pointerCounter; i++){
                    if (moveJoystick.isPressed((double) event.getX(i), (double) event.getY(i))) {
                        moveJoystickPointerID = event.getPointerId(i);
                        moveJoystick.setIsPressed(true);
                    }
                    else if (shootJoystick.isPressed((double) event.getX(i), (double) event.getY(i))) {
                        shootJoystickPointerID = event.getPointerId(i);
                        shootJoystick.setIsPressed(true);

                    }
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                for(int i = 0; i< pointerCounter; i++){
                    if (moveJoystick.getIsPressed() && moveJoystickPointerID == event.getPointerId(i)) {
                        moveJoystick.setActuator((double) event.getX(i), (double) event.getY(i));
                    }
                    else if (shootJoystick.getIsPressed() && shootJoystickPointerID == event.getPointerId(i)) {
                        shootJoystick.setActuator((double) event.getX(i), (double) event.getY(i));
                    }
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (moveJoystickPointerID == event.getPointerId(event.getActionIndex())) {
                    moveJoystick.setIsPressed(false);
                    moveJoystick.resetActuator();
                }
                if (shootJoystickPointerID == event.getPointerId(event.getActionIndex())) {
                    shootJoystick.setIsPressed(false);
                    shootJoystick.resetActuator();
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    // This method is used to spawn new objects, remove old objects, call the update methods of existing objects and check the game over condition
    public void update() {
        // Checking the game over condition, ending the activity is the game is over
        if(player.getHealthPoint() <= 0){
            soundPlayer.playSound(9);

            SoldierEnemy.resetLevel();
            TankEnemy.resetLevel();

            Intent toGameOver = new Intent((context), GameOverActivity.class);
            toGameOver.putExtra("Score", score);
            toGameOver.putExtra("Level", level);
            ((Activity)context).startActivity(toGameOver);
            ((Activity)context).finish();
        }

        // Updating the level
        if(score >= level * 50){
            level++;
            incrementLevel();
        }

        // Updating joystick and player positions so that they appear moving
        moveJoystick.update();
        shootJoystick.update();
        player.update();

        // Shooting bullets when shoot joystick is moved around, making the bullet sound and ensuring that the bullets are shot after a given interval of time
        if(shootJoystick.getIsPressed()){
            if(updatesBeforeNextBullet == 0){
                if(shootJoystick.getActuatorX() != 0 || shootJoystick.getActuatorY() != 0){
                    bulletsList.add(new Bullet(getContext(), player, shootJoystick.getActuatorX(), shootJoystick.getActuatorY()));
                    updatesBeforeNextBullet = (int) GameLoop.MAX_UPS / 3;
                    soundPlayer.playSound(1);
                }
            }
            else{
                updatesBeforeNextBullet--;
            }
        }

        // Updating player and tank bullet positions so that they appear moving
        for(Bullet bullet: bulletsList){
            bullet.update();
        }
        for(Bullet bullet: tankBulletList){
            bullet.update();
        }

        // Spawning power-ups when they are ready to spawn after certain number of updates
        if(FirstAid.readyToSpawn()){
            firstAidList.add(new FirstAid(getContext(), player));
        }
        if(AtomBomb.readyToSpawn()){
            atomBombList.add(new AtomBomb(getContext(), player));
        }

        // Spawning new soldier enemies when they are ready to spawn and also updating their positions
        if(SoldierEnemy.readyToSpawn()){
            soldierEnemyList.add(new SoldierEnemy(getContext(), player));
        }
        for(SoldierEnemy soldierEnemy: soldierEnemyList){
            soldierEnemy.update();
        }

        // Spawning new tank enemies, updating their positions and adding a new bullet when they are ready to shoot
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
                soundPlayer.playSound(2);
            }
        }

        /*
            Checking if a soldier is hit by a bullet, if it is, either its health is reduced or it dies.
            It is removed from the list if dead and the death sound is played, also the score is updated.
            Also checking if any soldier has hit the player, if yes,
            then that soldier is removed, hit sound is played and the player health is decremented.
         */
        int KILL_SOLDIER_ENEMY_POINTS = 2;
        Iterator<SoldierEnemy> iteratorSoldierEnemy = soldierEnemyList.iterator();
        while (iteratorSoldierEnemy.hasNext()) {
            SoldierEnemy soldierEnemy = iteratorSoldierEnemy.next();
            Iterator<Bullet> iteratorBullets = bulletsList.iterator();
            while (iteratorBullets.hasNext()) {
                Bullet bullet = iteratorBullets.next();
                if (soldierEnemy.isDead(bullet)) {
                    iteratorBullets.remove();
                    iteratorSoldierEnemy.remove();
                    soundPlayer.playSound(4);
                    score += KILL_SOLDIER_ENEMY_POINTS;
                    break;
                }
            }
            if(player.isHit(soldierEnemy)){
                soundPlayer.playSound(3);
                iteratorSoldierEnemy.remove();
            }
        }

        /*
            Checking if a tank is hit by a bullet, if it is, either its health is reduced or it dies.
            It is removed from the list if dead and the death sound is played, also the score is updated.
            Also checking if any tank has hit the player, if yes,
            then that tank is removed, hit sound is played and the player health is decremented.
         */
        int KILL_TANK_ENEMY_POINTS = 5;
        Iterator<TankEnemy> iteratorTankEnemy = tankEnemyList.iterator();
        while (iteratorTankEnemy.hasNext()) {
            TankEnemy tankEnemy = iteratorTankEnemy.next();
            Iterator<Bullet> iteratorBullets = bulletsList.iterator();
            while (iteratorBullets.hasNext()) {
                Bullet bullet = iteratorBullets.next();
                if (tankEnemy.isDead(bullet)) {
                    iteratorBullets.remove();
                    iteratorTankEnemy.remove();
                    soundPlayer.playSound(5);
                    score += KILL_TANK_ENEMY_POINTS;
                    break;
                }
            }
            if(player.isHit(tankEnemy)){
                soundPlayer.playSound(3);
                iteratorTankEnemy.remove();
            }
        }

        // Removing a bullet if it has spent a certain amount of time on the screen without hitting the player
        Iterator<Bullet> bulletIterator = bulletsList.iterator();
        while((bulletIterator.hasNext())){
            Bullet bullet = bulletIterator.next();
            if(bullet.timeOut()){
                bulletIterator.remove();
            }
        }

        /*
            Removing a tank bullet if it has spent a certain amount of time on the screen without hitting the player
            also, checking if a player has been hit by a tank bullet, if yes,
            then that bullet is removed, hit sound is played and the player health is decremented.
         */
        Iterator<Bullet> tankBulletIterator = tankBulletList.iterator();
        while(tankBulletIterator.hasNext()){
            Bullet bullet = tankBulletIterator.next();
            if(bullet.timeOut()){
                tankBulletIterator.remove();
            }
            if(player.isHit(bullet)){
                soundPlayer.playSound(3);
                tankBulletIterator.remove();
            }
        }

        /*
            Removing the first-aid power-up if the player hasn't interacted with it before a certain time
            Also if the player gets the first-aid, the first-aid sound is played and health is updated in the player class
         */
        Iterator<FirstAid> firstAidIterator = firstAidList.iterator();
        while(firstAidIterator.hasNext()){
            FirstAid firstAid = firstAidIterator.next();
            if(firstAid.timeOut()){
                firstAidIterator.remove();
            }
            if(player.isHit(firstAid)){
                firstAidIterator.remove();
                soundPlayer.playSound(6);
            }
        }

        /*
            Removing the atom-bomb power-up if the player hasn't interacted with it before a certain time
            Also if the player gets the atom-bomb, the bomb sound is played and all the soldiers, tanks and tank bullets die
            The score is updated accordingly
         */
        Iterator<AtomBomb> atomBombIterator = atomBombList.iterator();
        while(atomBombIterator.hasNext()){
            AtomBomb atomBomb = atomBombIterator.next();
            if(atomBomb.timeOut()){
                atomBombIterator.remove();
            }
            if(player.isHit(atomBomb)){
                atomBombIterator.remove();
                soundPlayer.playSound(7);
                score += soldierEnemyList.size() * KILL_SOLDIER_ENEMY_POINTS + tankEnemyList.size() * KILL_TANK_ENEMY_POINTS;
                soldierEnemyList.clear();
                tankEnemyList.clear();
                tankBulletList.clear();
            }
        }
        gameDisplay.update();
    }

    // Method to draw all the game objects and the map on the screen
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        // Drawing the map
        tileMap.draw(canvas, gameDisplay);

        // Drawing the player
        player.draw(canvas, gameDisplay);

        // Drawing the first-aids
        for(FirstAid firstAid: firstAidList){
            firstAid.draw(canvas, gameDisplay);
        }

        // Drawing the atom bombs
        for(AtomBomb atomBomb: atomBombList){
            atomBomb.draw(canvas, gameDisplay);
        }

        // Drawing the player bullets
        for(Bullet bullet: bulletsList){
            bullet.draw(canvas, gameDisplay);
        }

        // Drawing the tank bullets
        for(Bullet bullet: tankBulletList){
            bullet.draw(canvas, gameDisplay);
        }

        // Drawing all the soldier enemies
        for(SoldierEnemy soldierEnemy: soldierEnemyList){
            soldierEnemy.draw(canvas, gameDisplay);
        }

        // Drawing all the tank enemies
        for(TankEnemy tankEnemy: tankEnemyList){
            tankEnemy.draw(canvas, gameDisplay);
        }

        // Drawing the joysticks
        moveJoystick.draw(canvas);
        shootJoystick.draw(canvas);

        // Drawing the FPS and UPS
        performance.draw(canvas);

        // Displaying the score and level on the screen
        String scoreString = Integer.toString(score);
        String levelString = Integer.toString(level);
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);
        paint.setColor(color);
        paint.setTextSize(40);
        canvas.drawText("LEVEL:  " + levelString, 1600, 100, paint);
        canvas.drawText("SCORE: " + scoreString, 1800, 100, paint);
    }

    // Method to increase the level of the game and play level-up sound
    private void incrementLevel() {
        soundPlayer.playSound(8);
        SoldierEnemy.incrementLevel();
        TankEnemy.incrementLevel();
    }

    // Method to pause the game by stopping the gameloop
    public void pause(){gameLoop.stopLoop();}

    // Overriding the surfaceCreated method to start loop when game game begins
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

    // Overriding the surfaceChanged method
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d("GameArea: ", "surfaceChanged() called");
    }

    // Overriding the surfaceDestroyed method
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.d("GameArea: ", "surfaceDestroyed() called");
    }
}