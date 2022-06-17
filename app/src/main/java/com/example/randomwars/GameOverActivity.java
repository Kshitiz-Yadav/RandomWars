/*
       This activity is for the "Game Over" page which is reached when the player dies in the game
       Here, the score and the level reached by the player in that round is displayed
       If the player has made a new high-score, an appreciation message is displayed
       The SharedPreferences keeping track of high-scores is also updated here
       It has a "Restart" button to restart the game from beginning, a "Main Menu" button to take us to the main menu and an "Exit" button
 */

package com.example.randomwars;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

public class GameOverActivity extends AppCompatActivity implements View.OnClickListener {

    Button toMainMenu, restart, exit;
    Button denyExit, confirmExit;
    Dialog exitDialog;
    MusicPlayer gameOverMusicPlayer;
    private int score;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        // Code to set activity as fullscreen, remove title bar, hide navigation buttons and hiding system bars.
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        // Unpacking the bundle and retrieving the score and level that have been passed as extras so that they can be displayed
        Bundle bundle = getIntent().getExtras();
        score = bundle.getInt("Score");
        int level = bundle.getInt("Level");
        // Displaying score and level reached

        TextView scoreDisplay = findViewById(R.id.scoreDisplay);
        scoreDisplay.setText(String.format("Your score:\n%s", score));
        TextView levelDisplay = findViewById(R.id.levelDisplay);
        levelDisplay.setText(String.format("Level Reached:\n%s", level));

        // Calling the isHighScore() function to check if the score is a highScore or not
        boolean isHighScore = isHighScore();

        // Displaying the "It's a new High Score" if it is a high-score
        TextView highScoreMsg = findViewById(R.id.isHighscore);
        if(isHighScore){
            highScoreMsg.setText("It's a new High Score");
        }
        else{
            highScoreMsg.setText("");
        }

        // Initializing the buttons and adding listeners to them
        toMainMenu = findViewById(R.id.gameOverToMainMenu);
        restart = findViewById(R.id.gameOverToRestart);
        exit = findViewById(R.id.settingsToMainMenuButton);

        toMainMenu.setOnClickListener(this);
        restart.setOnClickListener(this);
        exit.setOnClickListener(this);

        // Creating a MusicPlayer object to play "GameOver" music
        gameOverMusicPlayer = new MusicPlayer(getApplicationContext(), 3);
        gameOverMusicPlayer.playMusic();
    }

    /*
        Function to check if the current score is a high-score
        Also updating the SharedPreferences if it is
        Algorithm->
            The index and the scores of the previous top 5 players are stored in a 2D Array
            The index and the player names are entered in a HashMap
            Current score is compared to the stored scores from the end of the array and the brought to its correct position by swapping
            Then with the help of the array and the HasMap, the SharedPreferences values are updated
     */
    private boolean isHighScore() {
        SharedPreferences scores = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        int[][] scoreArr = new int[5][2];
        HashMap<Integer, String> scorePlayer = new HashMap<Integer, String>();
        String name;

        // Storing the values in the array and HashMap
        scoreArr[0][0] = scores.getInt("S1", 0);
        scoreArr[0][1] = 1;
        name = scores.getString("P1", "Player");
        scorePlayer.put(scoreArr[0][1], name);
        scoreArr[1][0] = scores.getInt("S2", 0);
        scoreArr[1][1] = 2;
        name = scores.getString("P2", "Player");
        scorePlayer.put(scoreArr[1][1], name);
        scoreArr[2][0] = scores.getInt("S3", 0);
        scoreArr[2][1] = 3;
        name = scores.getString("P3", "Player");
        scorePlayer.put(scoreArr[2][1], name);
        scoreArr[3][0] = scores.getInt("S4", 0);
        scoreArr[3][1] = 4;
        name = scores.getString("P4", "Player");
        scorePlayer.put(scoreArr[3][1], name);
        scoreArr[4][0] = scores.getInt("S5", 0);
        scoreArr[4][1] = 5;
        name = scores.getString("P5", "Player");
        scorePlayer.put(scoreArr[4][1], name);
        name = scores.getString("PlayerName", "Player1");
        scorePlayer.put(6, name);

        boolean isHighScore = (score != 0) && (score >= scoreArr[4][0]);

        // Comparing and swapping current score to get it to the right position
        int[] temp = new int[2];
        for(int i=4;i>=0;i--){
            if(scoreArr[i][0] <= score){
                temp[0] = scoreArr[i][0];
                temp[1] = scoreArr[i][1];
                scoreArr[i][0] = score;
                scoreArr[i][1] = 6;
                if(i < 4){
                    scoreArr[i+1][0] = temp[0];
                    scoreArr[i+1][1] = temp[1];
                }
            }
        }

        // Updating the SharedPreferences
        SharedPreferences.Editor scoreManager = scores.edit();
        scoreManager.putInt("S1", scoreArr[0][0]);
        scoreManager.putString("P1", scorePlayer.get(scoreArr[0][1]));
        scoreManager.putInt("S2", scoreArr[1][0]);
        scoreManager.putString("P2", scorePlayer.get(scoreArr[1][1]));
        scoreManager.putInt("S3", scoreArr[2][0]);
        scoreManager.putString("P3", scorePlayer.get(scoreArr[2][1]));
        scoreManager.putInt("S4", scoreArr[3][0]);
        scoreManager.putString("P4", scorePlayer.get(scoreArr[3][1]));
        scoreManager.putInt("S5", scoreArr[4][0]);
        scoreManager.putString("P5", scorePlayer.get(scoreArr[4][1]));
        scoreManager.apply();

        return isHighScore;
    }

    /*
        Overriding the onClick method to to define working of buttons when they are pressed
     */
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
            case R.id.settingsToMainMenuButton:
                confirmExitPopUp();
                break;
            default:
                break;
        }
    }

    /*
        Method to display a "confirm-exit" popup when the exit button is clicked
        It has a confirmation and a denial button
     */
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

    // Overriding the onPause method to pause the music whenever the user leaves the activity by pressing home button etc.
    @Override
    protected void onPause() {
        gameOverMusicPlayer.pauseMusic();
        super.onPause();
    }

    // Overriding the OnResume method to resume the music whenever the user returns back to this activity
    @Override
    protected void onResume() {
        gameOverMusicPlayer.playMusic();
        super.onResume();
    }
}