package com.example.randomwars;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class IntroPageActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "IntroPageActivity";
    Button startButton, settingsButton, exitButton, highScoreButton;
    Dialog dialog;
    Button confirmExit, denyExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        new MusicPlayer(this.getApplicationContext());
        MusicPlayer.play();

        startButton = findViewById(R.id.startButton);
        settingsButton = findViewById(R.id.settingButton);
        exitButton = findViewById(R.id.backButton);
        highScoreButton = findViewById(R.id.viewHighScoreButton);

        startButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
        highScoreButton.setOnClickListener(this);
    }

//    Implementing onClick Functionality for all buttons
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.startButton:
                Intent toMain = new Intent(IntroPageActivity.this, MainActivity.class);
                startActivity(toMain);
//                finish();
                break;
            case R.id.viewHighScoreButton:
                Intent toHighScores = new Intent(IntroPageActivity.this, HighScoreActivity.class);
                startActivity(toHighScores);
                break;
            case R.id.settingButton:
                Intent toSettings = new Intent(IntroPageActivity.this, SettingsActivity.class);
                startActivity(toSettings);
                break;
            case R.id.backButton:
                confirmExitDialogBox();
                break;
        }
    }

//    Game exit confirmation pop-up
    private void confirmExitDialogBox() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.exit_confirmation);

        confirmExit = dialog.findViewById(R.id.toMainMenuConfirmButton);
        denyExit = dialog.findViewById(R.id.toMainMenuDenyButton);
        confirmExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });

        denyExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

//    Implementing onDestroy, onPause, and onPostResume to handle music player
    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicPlayer.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onPostResume: main chala");
        MusicPlayer.play();
    }

//    Implementing onBackPress so that app doesn't go to splash screen
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}