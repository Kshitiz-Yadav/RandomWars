/*
    This is the activity for the "Main Menu" Page which appears right after the splash screen ends.
    It has a 5 buttons and a textView. The textView is a tip for the user related to the game.
    The buttons are:
        Start- To go to the MainActivity and start plating the game
        Settings- To go to the SettingsActivity for user settings
        High Scores- To go to the HighScoreActivity to view top 5 high scores on the device
        How to Play- To go to the HowToPlayActivity to get instructions regarding the game
        Exit- To quit the game
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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IntroPageActivity extends AppCompatActivity implements View.OnClickListener{

    Button startButton, settingsButton, highScoreButton, exitButton, howToPlayButton;
    Dialog dialog;
    Button confirmExit, denyExit;
    MusicPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);

        // Code to set activity as fullscreen, remove title bar, hide navigation buttons and hiding system bars.
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        // Adding tips to be displayed in the textView to an array and then randomly displaying one of them.
        String[] gameTips = {
                "You die sooner when closer to Lava",
                "Appreciate the map",
                "Like the music?",
                "Do you have a better game name?",
                "An enemy can spawn real close",
                "Running away makes it worse",
                "Yes you can run on the water, it's shallow",
                "I bet you'll get a high score",
                "Did you do your homework?",
                "Let's go!!!"
        };
        TextView gameTip = findViewById(R.id.gameTip);
        gameTip.setText(String.format("Tip: %s", gameTips[(int) (Math.random() * 9)]));

        // Initializing all the buttons and adding listener to them
        startButton = findViewById(R.id.startButton);
        highScoreButton = findViewById(R.id.highScoreButton);
        settingsButton = findViewById(R.id.settingButton);
        exitButton = findViewById(R.id.settingsToMainMenuButton);
        howToPlayButton = findViewById(R.id.howToPlayButton);

        startButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
        howToPlayButton.setOnClickListener(this);
        highScoreButton.setOnClickListener(this);

        // Creating a new musicPlayer, passing it to the musicPlayerHolder and then playing the music
        musicPlayer = new MusicPlayer(this, 1);
        MusicPlayerHolder.setMusicPlayer(musicPlayer);
        musicPlayer.playMusic();
    }

    // Overriding the onClick method to define the functionality of all the buttons
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.startButton:
                Intent toMain = new Intent(IntroPageActivity.this, MainActivity.class);
                startActivity(toMain);
                /*
                    Finishing the main-menu activity when going to MainActivity but not in case of other Activities
                    as other activities return to main-menu by finishing themselves,
                    while MainActivity creates new main-menu to return to.
                    This allows continuity in the music for main-menu, setting, highScore and howToPlay activities.
                */
                finish();
                break;
            case R.id.howToPlayButton:
                Intent toHowToPlay = new Intent(IntroPageActivity.this, HowToPlayActivity.class);
                startActivity(toHowToPlay);
                break;
            case R.id.highScoreButton:
                Intent toHighScores = new Intent(IntroPageActivity.this, HighScoreActivity.class);
                startActivity(toHighScores);
                break;
            case R.id.settingButton:
                Intent toSettings = new Intent(IntroPageActivity.this, SettingsActivity.class);
                startActivity(toSettings);
                break;
            case R.id.settingsToMainMenuButton:
                confirmExitDialogBox();
                break;
        }
    }

    /*
        Method to display a "confirm-exit" popup when the exit button is clicked
        It has a confirmation and a denial button
     */
    private void confirmExitDialogBox() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.exit_confirmation);

        confirmExit = dialog.findViewById(R.id.toMainMenuConfirmButton);
        denyExit = dialog.findViewById(R.id.toMainMenuDenyButton);

        confirmExit.setOnClickListener(v -> {
            finishAffinity();
            System.exit(0);
        });
        denyExit.setOnClickListener(v -> dialog.dismiss());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    // Overriding the onPause method to pause the music whenever the user leaves the activity by pressing home button etc.
    @Override
    protected void onPause() {
        musicPlayer.pauseMusic();
        super.onPause();
    }

    // Overriding the OnResume method to resume the music whenever the user returns back to this activity
    @Override
    protected void onResume() {
        musicPlayer.playMusic();
        super.onResume();
    }

    // Overriding the onBackPress method so that app doesn't go to the splash screen and exits directly
    @Override
    public void onBackPressed(){finishAffinity();}
}