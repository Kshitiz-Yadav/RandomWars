/*
    This is the activity for the "How to Play" Page which is reached by the "How to Play" Button in main menu.
    It has a textView which has instructions written in it and a back button to take user back to the main menu.
*/

package com.example.randomwars;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HowToPlayActivity extends AppCompatActivity{

    Button backButton;
    MusicPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

        // Code to set activity as fullscreen, remove title bar, hide navigation buttons and hiding system bars.
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        // Initializing the back button, on click the back button will finish the current activity thus taking us back to the main-menu page
        backButton = findViewById(R.id.settingsToMainMenuButton);
        backButton.setOnClickListener(v -> finish());

        // Getting the musicPlayer from the MusicPlayerHolder and playing it
        musicPlayer = MusicPlayerHolder.getMusicPlayer();
        musicPlayer.playMusic();
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
}