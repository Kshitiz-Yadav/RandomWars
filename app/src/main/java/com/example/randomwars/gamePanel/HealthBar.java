package com.example.randomwars.gamePanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.randomwars.GameDisplay;
import com.example.randomwars.R;
import com.example.randomwars.gameObjects.GameObjects;

public class HealthBar {
    private GameObjects object;
    private Paint borderPaint, healthPaint;
    private int width, height, margin;

    public HealthBar(Context context, GameObjects object){
        this.object = object;
        this.width = 100;
        this.height = 20;
        this.margin = 2;

        this.borderPaint = new Paint();
        int borderColor = ContextCompat.getColor(context, R.color.black);
        borderPaint.setColor(borderColor);

        this.healthPaint = new Paint();
        int healthColor = ContextCompat.getColor(context, R.color.green);
        healthPaint.setColor(healthColor);
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay){
        float x = (float) object.getPositionX() + 20;
        float y = (float) object.getPositionY();
        float distanceToPlayer = 30;
        float healthPointPercentage = (float) object.getHealthPoint()/object.MAX_HEALTH_POINTS;

        // Draw border
        float borderLeft, borderTop, borderRight, borderBottom;
        borderLeft = x - width/2;
        borderRight = x + width/2;
        borderBottom = y - distanceToPlayer;
        borderTop = borderBottom - height;
        canvas.drawRect(
                (float) gameDisplay.coordinatesX(borderLeft),
                (float) gameDisplay.coordinatesY(borderTop),
                (float) gameDisplay.coordinatesX(borderRight),
                (float) gameDisplay.coordinatesY(borderBottom),
                borderPaint);

        // Draw health
        float healthLeft, healthTop, healthRight, healthBottom, healthWidth, healthHeight;
        healthWidth = width - 2*margin;
        healthHeight = height - 2*margin;
        healthLeft = borderLeft + margin;
        healthRight = healthLeft + healthWidth*healthPointPercentage;
        healthBottom = borderBottom - margin;
        healthTop = healthBottom - healthHeight;
        canvas.drawRect(
                (float) gameDisplay.coordinatesX(healthLeft),
                (float) gameDisplay.coordinatesY(healthTop),
                (float) gameDisplay.coordinatesX(healthRight),
                (float) gameDisplay.coordinatesY(healthBottom),
                healthPaint);
    }
}
