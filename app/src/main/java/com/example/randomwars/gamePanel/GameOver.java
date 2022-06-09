package com.example.randomwars.gamePanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.randomwars.R;

public class GameOver {
    private Context context;

    public GameOver(Context context) {
        this.context = context;
    }

    public void draw(Canvas canvas) {
        String text = "Game Over";

        float x = 350;
        float y = 300;

        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.red);
        paint.setColor(color);
        float textSize = 300;
        paint.setTextSize(textSize);

        canvas.drawText(text, x, y, paint);
    }
}
