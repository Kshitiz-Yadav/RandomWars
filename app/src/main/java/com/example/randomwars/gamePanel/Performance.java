package com.example.randomwars.gamePanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.randomwars.GameLoop;
import com.example.randomwars.R;

public class Performance {

    private GameLoop gameLoop;
    private Context context;

    public Performance(GameLoop gameLoop, Context context){
        this.gameLoop = gameLoop;
        this.context = context;

    }


    private void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);
        paint.setColor(color);
        paint.setTextSize(30);
        canvas.drawText("FPS: " + averageFPS, 200, 100, paint);
    }

    private void drawUPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);
        paint.setColor(color);
        paint.setTextSize(30);
        canvas.drawText("UPS: " + averageFPS, 200, 150, paint);
    }

    public void draw(Canvas canvas) {
        drawFPS(canvas);
        drawUPS(canvas);
    }
}
