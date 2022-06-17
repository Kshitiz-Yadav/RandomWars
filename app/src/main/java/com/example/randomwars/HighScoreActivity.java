/*
    This is the activity for the "HighScores" Page which is reached by the "High Scores" Button in main menu.
    It has a textView table which stores the position, name and score of the top 5 high-scoring players and a back button to take user back to the main menu.
    SharedPreferences have been used to keep track of these scores, the high-scores are updates in the GameOverActivity
 */

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

        //Getting the shared preferences and then setting the table entries to data (scores and names of player) fetched from it using keys
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