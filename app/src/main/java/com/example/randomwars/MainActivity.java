package com.example.randomwars;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button pauseButton;
    Button resumeButton, settingsButton, mainMenuButton, exitButton;
    Button confirmMainMenu, denyMainMenu;
    Button confirmExit, denyExit;
    Dialog pauseDialog, exitDialog, mainMenuDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        pauseButton = findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPausePopUp();
            }
        });
    }

//    For showing paused menu
    private void showPausePopUp() {
        pauseDialog = new Dialog(this);
        pauseDialog.setContentView(R.layout.pause_menu);

        resumeButton = pauseDialog.findViewById(R.id.resumeButton);
        settingsButton = pauseDialog.findViewById(R.id.settingsButton);
        mainMenuButton = pauseDialog.findViewById(R.id.mainMenuButton);
        exitButton = pauseDialog.findViewById(R.id.exitButton);

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseDialog.dismiss();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSettings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(toSettings);
            }
        });

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmMainMenuPopUp();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmExitPopUp();
            }


        });

        pauseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pauseDialog.show();
    }

//    Game exit confirmation pop-up
    private void confirmExitPopUp() {
        exitDialog = new Dialog(this);
        exitDialog.setContentView(R.layout.exit_confirmation);

        confirmExit = exitDialog.findViewById(R.id.toMainMenuConfirmButton);
        denyExit = exitDialog.findViewById(R.id.toMainMenuDenyButton);

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
                exitDialog.dismiss();
            }
        });

        exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exitDialog.show();
    }

//    Pop-Up for confirmation to return to main menu
    private void confirmMainMenuPopUp() {
        mainMenuDialog = new Dialog(this);
        mainMenuDialog.setContentView(R.layout.main_menu_confirmation);

        confirmMainMenu = mainMenuDialog.findViewById(R.id.toMainMenuConfirmButton);
        denyMainMenu = mainMenuDialog.findViewById(R.id.toMainMenuDenyButton);

        confirmMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMainMenu = new Intent(MainActivity.this, IntroPageActivity.class);
                startActivity(toMainMenu);
            }
        });

        denyMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMenuDialog.dismiss();
            }
        });

        mainMenuDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mainMenuDialog.show();
    }
}