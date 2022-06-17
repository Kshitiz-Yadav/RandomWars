// Class to display the FPS and UPS of the game on the screen

package com.example.randomwars.gamePanel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;
import com.example.randomwars.GameLoop;
import com.example.randomwars.R;

public class Performance {
    private final Context context;

    public Performance(Context context){
        this.context = context;
    }

    // Method to call the methods to draw the FPS and UPS
    public void draw(Canvas canvas) {
        drawFPS(canvas);
        drawUPS(canvas);
    }

    // Method to draw FPS
    @SuppressLint("DefaultLocale")
    private void drawFPS(Canvas canvas) {
        String averageFPS = String.format("%.2f", GameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);
        paint.setColor(color);
        paint.setTextSize(30);
        canvas.drawText("FPS: " + averageFPS, 200, 100, paint);
    }

    // Method to draw UPS
    @SuppressLint("DefaultLocale")
    private void drawUPS(Canvas canvas) {
        String averageUPS = String.format("%.2f",GameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);
        paint.setColor(color);
        paint.setTextSize(30);
        canvas.drawText("UPS: " + averageUPS, 200, 150, paint);
    }
}