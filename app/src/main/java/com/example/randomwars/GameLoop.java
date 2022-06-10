package com.example.randomwars;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameLoop extends Thread{

    public static final double MAX_UPS = 30.0;
    public static final double UPDATE_DURATION = 1E+3 / MAX_UPS;

    private GameArea gameArea;
    private SurfaceHolder surfaceHolder;

    private boolean isRunning = false;
    private double averageUPS;
    private double averageFPS;

    public GameLoop(GameArea gameArea, SurfaceHolder surfaceHolder) {
        this.gameArea = gameArea;
        this.surfaceHolder = surfaceHolder;
    }

    public void startLoop(){
        Log.d("GameLoop: ", "startLoop() called");
        isRunning = true;
        start();
    }

    public void stopLoop(){
        Log.d("GameLoop: ", "stopLoop() called");
        isRunning = false;
        try{
            join();
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }

    public double getAverageUPS(){
        return averageUPS;
    }

    public double getAverageFPS(){
        return averageFPS;
    }

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
            if(sleepTime > 0){
                try{
                    sleep(sleepTime);
                }
                catch (InterruptedException ie){
                    ie.printStackTrace();
                }
            }

            while(sleepTime < 0 && updateCount < MAX_UPS - 1){
                gameArea.update();;
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount * UPDATE_DURATION - elapsedTime);
            }

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
