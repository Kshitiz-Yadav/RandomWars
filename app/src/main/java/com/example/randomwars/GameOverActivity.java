package com.example.randomwars;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity implements View.OnClickListener {

    Button toMainMenu, restart, exit;
    Button denyExit, confirmExit;
    Dialog exitDialog;
    MusicPlayer gameOverMusicPlayer;

    private int score;
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        Bundle bundle = getIntent().getExtras();
        score = bundle.getInt("Score");
        level = bundle.getInt("Level");

        TextView scoreDisplay = findViewById(R.id.scoreDisplay);
        scoreDisplay.setText(String.format("Your score:\n%s", score));
        TextView levelDisplay = findViewById(R.id.levelDisplay);
        levelDisplay.setText(String.format("Level Reached:\n%s", level));

        toMainMenu = findViewById(R.id.gameOverToMainMenu);
        restart = findViewById(R.id.gameOverToRestart);
        exit = findViewById(R.id.gameOverToExit);

        toMainMenu.setOnClickListener(this);
        restart.setOnClickListener(this);
        exit.setOnClickListener(this);

        gameOverMusicPlayer = new MusicPlayer(getApplicationContext(), 3);
        gameOverMusicPlayer.playMusic();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gameOverToRestart:
                Intent toMain = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(toMain);
                finish();
                break;
            case R.id.gameOverToMainMenu:
                Intent toMainMenu = new Intent(GameOverActivity.this, IntroPageActivity.class);
                startActivity(toMainMenu);
                break;
            case R.id.gameOverToExit:
                confirmExitPopUp();
                break;
            default:
                break;
        }
    }

    private void confirmExitPopUp() {
        exitDialog = new Dialog(this);
        exitDialog.setContentView(R.layout.exit_confirmation);

        confirmExit = exitDialog.findViewById(R.id.toMainMenuConfirmButton);
        denyExit = exitDialog.findViewById(R.id.toMainMenuDenyButton);

        confirmExit.setOnClickListener(v -> {
            finishAffinity();
            System.exit(0);
        });

        denyExit.setOnClickListener(v -> exitDialog.dismiss());

        exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exitDialog.show();
    }

    @Override
    protected void onPause() {
        gameOverMusicPlayer.pauseMusic();
        super.onPause();
    }

    @Override
    protected void onResume() {
        gameOverMusicPlayer.playMusic();
        super.onResume();
    }
}