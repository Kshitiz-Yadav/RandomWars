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
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button pauseButton;
    Button resumeButton, settingsButton, mainMenuButton, exitButton;
    Button confirmMainMenu, denyMainMenu;
    Button confirmExit, denyExit;
    Dialog pauseDialog, exitDialog, mainMenuDialog;
    private GameArea gameArea;
    private RelativeLayout forPauseButton;
    private FrameLayout forGame;
    private MusicPlayer mainMusicPlayer;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        Log.d("MainActivity: ", "onCreate() MusicPlayerChecker");
        super.onCreate(savedInstanceState);

//        For immersive mode, hiding notification bar, hiding navigation bar and going fullscreen
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

//        Setting up pause game button to pause game loop
        gameArea = new GameArea(this);
        forGame = new FrameLayout(this);
        forPauseButton = new RelativeLayout(this);

        pauseButton = new Button(this);
        pauseButton.setText("ii");

        RelativeLayout.LayoutParams pauseButtonParams = new RelativeLayout.LayoutParams(135, 150);
        pauseButtonParams.addRule(RelativeLayout.ALIGN_TOP, RelativeLayout.TRUE);
        pauseButtonParams.addRule(RelativeLayout.ALIGN_LEFT, RelativeLayout.TRUE);
        pauseButtonParams.topMargin = 50;
        pauseButtonParams.leftMargin = 50;
        pauseButton.setLayoutParams(pauseButtonParams);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        forPauseButton.setLayoutParams(params);
        forPauseButton.addView(pauseButton);

        forGame.addView(gameArea);
        forGame.addView(forPauseButton);

        pauseButton.setOnClickListener(v -> {
            gameArea.pause();
            showPausePopUp();
        });

        mainMusicPlayer = new MusicPlayer(this, 2);
        mainMusicPlayer.playMusic();

        setContentView(forGame);
    }

//    For showing paused menu
    private void showPausePopUp() {
        pauseDialog = new Dialog(this);
        pauseDialog.setContentView(R.layout.pause_menu);

        resumeButton = pauseDialog.findViewById(R.id.resumeButton);
        settingsButton = pauseDialog.findViewById(R.id.settingsButton);
        mainMenuButton = pauseDialog.findViewById(R.id.mainMenuButton);
        exitButton = pauseDialog.findViewById(R.id.exitButton);

        resumeButton.setOnClickListener(v -> {
            pauseDialog.dismiss();
            setContentView(forGame);
        });

        settingsButton.setOnClickListener(v -> {
            pauseDialog.dismiss();
            Intent toSettings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(toSettings);
        });

        mainMenuButton.setOnClickListener(v -> confirmMainMenuPopUp());

        exitButton.setOnClickListener(v -> confirmExitPopUp());

        pauseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pauseDialog.show();
    }

//    Game exit confirmation pop-up
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

//    Pop-Up for confirmation to return to main menu
    private void confirmMainMenuPopUp() {
        mainMenuDialog = new Dialog(this);
        mainMenuDialog.setContentView(R.layout.main_menu_confirmation);

        confirmMainMenu = mainMenuDialog.findViewById(R.id.toMainMenuConfirmButton);
        denyMainMenu = mainMenuDialog.findViewById(R.id.toMainMenuDenyButton);

        confirmMainMenu.setOnClickListener(v -> {
            Intent toMainMenu = new Intent(MainActivity.this, IntroPageActivity.class);
            startActivity(toMainMenu);
        });

        denyMainMenu.setOnClickListener(v -> mainMenuDialog.dismiss());

        mainMenuDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mainMenuDialog.show();
    }

    @Override
    protected void onStart() {
        Log.d("MainActivity: ", "onStart() MusicPlayerChecker");
        mainMusicPlayer.playMusic();
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("MainActivity: ", "onResume() MusicPlayerChecker");
        mainMusicPlayer.playMusic();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.d("MainActivity: ", "onDestroy() MusicPlayerChecker");
        Intent toMainMenu = new Intent(MainActivity.this, IntroPageActivity.class);
        startActivity(toMainMenu);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        gameArea.pause();
        showPausePopUp();
    }

    @Override
    protected void onPause() {
        Log.d("MainActivity: ", "onPause() MusicPlayerChecker");
        gameArea.pause();
        mainMusicPlayer.pauseMusic();
        super.onPause();
    }
}