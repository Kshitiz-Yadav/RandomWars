/*
    This is the activity for the "Settings" Page which is reached by the "Settings" Button in main menu.
    It has a editText which allows the user to set his player name,
    a music switch and a sound effects switch which allow the user to turn the music and sound effects on and off respectively
    and a back button to take user back to the main menu.
    SharedPreferences have been used to keep track of the previous music and sound effects preferences of the user,
    so that they can be set to the previous choice as soon as the game opens.
 */

package com.example.randomwars;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch musicSwitch, soundEffectsSwitch;
    boolean musicState, soundState;
    Button backToIntroPageButton;
    String playerName;
    MusicPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Settings: ", "onCreate()  MusicPlayerCheck");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Code to set activity as fullscreen, remove title bar, hide navigation buttons and hiding system bars.
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        // Initializing the buttons, switches and editText with layout components
        backToIntroPageButton = findViewById(R.id.settingsToMainMenuButton);
        musicSwitch = findViewById(R.id.musicSwitch);
        soundEffectsSwitch = findViewById(R.id.soundSwitch);
        EditText playerNameText = findViewById(R.id.playerNameText);

        // Setting the state of switch to previously set state using shared preferences
        SharedPreferences userPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        playerNameText.setText(userPreferences.getString("PlayerName","Player"));
        musicState = userPreferences.getBoolean("Music", true);
        soundState = userPreferences.getBoolean("Sound", true);
        soundEffectsSwitch.setChecked(soundState);
        musicSwitch.setChecked(musicState);
        MusicPlayer.setMusicState(musicState);

        // Adding listeners to the buttons
        musicSwitch.setOnCheckedChangeListener(this);
        soundEffectsSwitch.setOnCheckedChangeListener(this);
        backToIntroPageButton.setOnClickListener(this);

        // Getting the musicPlayer from the musicPlayerHolder and playing music
        musicPlayer = MusicPlayerHolder.getMusicPlayer();
        musicPlayer.playMusic();
    }

    /*
        Overriding the onClick method to define the functionality of the back button.
        On clicking the back button, the new preferences of player name, music state and sound effects state is stored in SharedPreferences.
        Ultimately the activity is finished which takes us back to the main-menu.
     */
    @Override
    public void onClick(View v) {
        SharedPreferences userPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor upEditor = userPreferences.edit();

        playerName = ((EditText) findViewById(R.id.playerNameText)).getText().toString();
        upEditor.putString("PlayerName", playerName);
        upEditor.putBoolean("Music", musicState);
        upEditor.putBoolean("Sound", soundState);
        upEditor.apply();

        finish();
    }

    // Overriding the onCheckChanged method to set the music switch to right state and change state of music and sound player
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.musicSwitch:
                musicState = musicSwitch.isChecked();
                MusicPlayer.setMusicState(musicState);
                break;
            case R.id.soundSwitch:
                soundState = soundEffectsSwitch.isChecked();
                SoundPlayer.setSoundState(soundState);
                break;
            default:
                break;
        }
    }

    // Overriding the onPause method to pause the music whenever the user leaves the activity by pressing home button etc.
    @Override
    protected void onPause() {
        Log.d("Settings: ", "onPause()  MusicPlayerCheck");
        musicPlayer.pauseMusic();
        super.onPause();
    }

    // Overriding the OnResume method to resume the music whenever the user returns back to this activity
    @Override
    protected void onResume() {
        Log.d("Settings: ", "onResume()  MusicPlayerCheck");
        musicPlayer.playMusic();
        super.onResume();
    }
}