/*
    Activity containing the layouts having the game area and pause button
    The game runs in the gameArea activity whereas the pause button is present in the relative layout
    These two are then added to the frame layout to which content view is set
 */

package com.example.randomwars;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
    private FrameLayout forGame;
    private MusicPlayer mainMusicPlayer;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Code to set activity as fullscreen, remove title bar, hide navigation buttons and hiding system bars.
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        // Setting up relative layout and the the pause button
        RelativeLayout forPauseButton = new RelativeLayout(this);
        RelativeLayout.LayoutParams pauseButtonParams = new RelativeLayout.LayoutParams(135, 150);
        pauseButtonParams.addRule(RelativeLayout.ALIGN_TOP, RelativeLayout.TRUE);
        pauseButtonParams.addRule(RelativeLayout.ALIGN_LEFT, RelativeLayout.TRUE);
        pauseButtonParams.topMargin = 50;
        pauseButtonParams.leftMargin = 50;

        pauseButton = new Button(this);
        pauseButton.setText("ii");
        pauseButton.setLayoutParams(pauseButtonParams);
        pauseButton.setOnClickListener(v -> {
            gameArea.pause();
            showPausePopUp();
        });

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        forPauseButton.setLayoutParams(params);
        forPauseButton.addView(pauseButton);

        // Creating the game area and the frame layout
        gameArea = new GameArea(this);

        forGame = new FrameLayout(this);
        forGame.addView(gameArea);
        forGame.addView(forPauseButton);
        setContentView(forGame);

        // Creating the music player for the game and playing ti
        mainMusicPlayer = new MusicPlayer(this, 2);
        mainMusicPlayer.playMusic();
    }

    // Method to display the pause menu dialog when pause button is clicked
    private void showPausePopUp() {
        pauseDialog = new Dialog(this);
        pauseDialog.setCancelable(false);
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

    // Method to pop-up game exit confirmation when "Exit" button in pause menu is clicked
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

    // Method to pop-up main-menu confirmation when "Main Menu" button in pause menu is clicked
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

    // Overriding the onPause method to pause the music whenever the user leaves the activity by pressing home button etc.
    @Override
    protected void onPause() {
        gameArea.pause();
        mainMusicPlayer.pauseMusic();
        super.onPause();
    }

    // Overriding the OnResume method to resume the music whenever the user returns back to this activity
    @Override
    protected void onResume() {
        mainMusicPlayer.playMusic();
        super.onResume();
    }

    // Overriding the onBackPress method to open the pause menu when back button is clicked
    @Override
    public void onBackPressed() {
        gameArea.pause();
        showPausePopUp();
    }
}