package com.example.randomwars;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HighScoreActivity extends AppCompatActivity {

    Button backButton;
    MusicPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        backButton = findViewById(R.id.settingsToMainMenuButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        musicPlayer = MusicPlayerHolder.getMusicPlayer();
        musicPlayer.playMusic();

        SharedPreferences highScores = getSharedPreferences("UserPreferences", MODE_PRIVATE);

        ((TextView) findViewById(R.id.Player1)).setText(highScores.getString("P1", "Player"));
        ((TextView) findViewById(R.id.Player2)).setText(highScores.getString("P2", "Player"));
        ((TextView) findViewById(R.id.Player3)).setText(highScores.getString("P3", "Player"));
        ((TextView) findViewById(R.id.Player4)).setText(highScores.getString("P4", "Player"));
        ((TextView) findViewById(R.id.Player5)).setText(highScores.getString("P5", "Player"));

        ((TextView) findViewById(R.id.Score1)).setText(String.format("%s",highScores.getInt("S1", 0)));
        ((TextView) findViewById(R.id.Score2)).setText(String.format("%s",highScores.getInt("S2", 0)));
        ((TextView) findViewById(R.id.Score3)).setText(String.format("%s",highScores.getInt("S3", 0)));
        ((TextView) findViewById(R.id.Score4)).setText(String.format("%s",highScores.getInt("S4", 0)));
        ((TextView) findViewById(R.id.Score5)).setText(String.format("%s",highScores.getInt("S5", 0)));

    }

    @Override
    protected void onPause() {
        musicPlayer.pauseMusic();
        super.onPause();
    }

    @Override
    protected void onResume() {
        musicPlayer.playMusic();
        super.onResume();
    }
}