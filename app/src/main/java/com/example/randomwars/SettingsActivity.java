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

    Button backToIntroPageButton;
    Switch musicSwitch, soundEffectsSwitch;
    boolean musicState, soundState;
    String playerName;
    MusicPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Settings: ", "onCreate()  MusicPlayerCheck");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        backToIntroPageButton = findViewById(R.id.settingsToMainMenuButton);
        musicSwitch = findViewById(R.id.musicSwitch);
        soundEffectsSwitch = findViewById(R.id.soundSwitch);
        EditText playerNameText = findViewById(R.id.playerNameText);

        SharedPreferences userPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        playerNameText.setText(userPreferences.getString("PlayerName","Player"));
        musicState = userPreferences.getBoolean("Music", true);
        soundState = userPreferences.getBoolean("Sound", true);
        soundEffectsSwitch.setChecked(soundState);
        musicSwitch.setChecked(musicState);
        MusicPlayer.setMusicState(musicState);

        musicSwitch.setOnCheckedChangeListener(this);
        soundEffectsSwitch.setOnCheckedChangeListener(this);
        backToIntroPageButton.setOnClickListener(this);

        musicPlayer = MusicPlayerHolder.getMusicPlayer();
        musicPlayer.playMusic();
    }

    @Override
    public void onClick(View v) {
        playerName = ((EditText) findViewById(R.id.playerNameText)).getText().toString();

        SharedPreferences userPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor upEditor = userPreferences.edit();
        upEditor.putString("PlayerName", playerName);
        upEditor.putBoolean("Music", musicState);
        upEditor.putBoolean("Sound", soundState);
        upEditor.apply();

        finish();
    }

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

    @Override
    protected void onDestroy() {
        Log.d("Settings: ", "onDestroy()  MusicPlayerCheck");
//        musicPlayer.pauseMusic();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d("Settings: ", "onPause()  MusicPlayerCheck");
        musicPlayer.pauseMusic();
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("Settings: ", "onResume()  MusicPlayerCheck");
        musicPlayer.playMusic();
        super.onResume();
    }
}