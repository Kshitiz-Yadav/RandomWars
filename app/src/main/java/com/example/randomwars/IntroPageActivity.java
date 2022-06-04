package com.example.randomwars;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class IntroPageActivity extends AppCompatActivity implements View.OnClickListener{

    Button startButton, settingsButton, exitButton;
    Dialog dialog;
    Button confirmExit, denyExit;
    MusicPlayer musicPlayer;

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

        musicPlayer = new MusicPlayer(this.getApplicationContext());
        musicPlayer.play();

        startButton = findViewById(R.id.startButton);
        settingsButton = findViewById(R.id.settingButton);
        exitButton = findViewById(R.id.backButton);

        startButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.startButton:
                Intent toMain = new Intent(IntroPageActivity.this, MainActivity.class);
                startActivity(toMain);
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

    private void confirmExitDialogBox() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.exit_confirmation);

        confirmExit = dialog.findViewById(R.id.confirmButton);
        denyExit = dialog.findViewById(R.id.denyButton);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        musicPlayer.pause();
    }

    @Override
    protected void onPause() {
        super.onPause();
        musicPlayer.pause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        musicPlayer.play();
    }
}