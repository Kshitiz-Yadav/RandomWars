/*
    This class is used to run the game loop
    The game loop is responsible for calling the draw and update methods in draw area
    It also maintains the UPS close to the decided value and counts the frames as well
    The loop is paused when the activity is paused and resumed when when the activity resumes
 */

package com.example.randomwars;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread{
    public static final double MAX_UPS = 30.0;
    public static final double UPDATE_DURATION = 1E+3 / MAX_UPS;

    private final GameArea gameArea;
    private final SurfaceHolder surfaceHolder;

    private boolean isRunning = false;
    private static double averageUPS;
    private static double averageFPS;

    public GameLoop(GameArea gameArea, SurfaceHolder surfaceHolder) {
        this.gameArea = gameArea;
        this.surfaceHolder = surfaceHolder;
    }

    // Method to start the loop in the beginning or on calls from onPause methods
    public void startLoop(){
        isRunning = true;
        start();
    }

    // Method to stop the loop when the activity pauses or when the game gets over
    public void stopLoop(){
        isRunning = false;
        try{
            join();
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }

    // Method to return the average UPS so that it can be drawn
    public static double getAverageUPS(){return averageUPS;}

    // Method to return the average FPS so that it can be drawn
    public static double getAverageFPS(){return averageFPS;}

    // Thread's run method running the loop
    @Override
    public void run(){
        super.run();

        long startTime, elapsedTime, sleepTime;

        int updateCount = 0;
        int frameCount = 0;
        Canvas canvas = null;
        startTime = System.currentTimeMillis();
        while(isRunning){
            try{
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    // Calling update and draw and updating updateCount
                    gameArea.update();
                    updateCount++;
                    gameArea.draw(canvas);
                }
            }
            catch (IllegalArgumentException iae){
                iae.printStackTrace();
            }
            finally {
                if(canvas != null){
                    try{
                        // Updating frameCount
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long) (updateCount * UPDATE_DURATION - elapsedTime);
            // Condition when updates are going faster than time, sleep in this case so that MAX_UPS is not exceeded
            if(sleepTime > 0){
                try{
                    sleep(sleepTime);
                }
                catch (InterruptedException ie){
                    ie.printStackTrace();
                }
            }

            // Condition when updates are going slower than time, call update again in this case and increment updateCount
            while(sleepTime < 0 && updateCount < MAX_UPS - 1){
                gameArea.update();;
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount * UPDATE_DURATION - elapsedTime);
            }

            // Update the values of average FPS and UPS after every second so that they can be displayed on the screen
            elapsedTime = System.currentTimeMillis() - startTime;
            if(elapsedTime >= 1000){
                averageUPS = updateCount / (1E-3 * elapsedTime);
                averageFPS = frameCount / (1E-3 * elapsedTime);
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }
        }
    }
}