package com.example.randomwars;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity{

    Button backToIntroPageButton;
    Switch musicSwitch, soundEffectsSwitch;
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

        backToIntroPageButton = findViewById(R.id.gameOverToExit);
        musicSwitch = findViewById(R.id.musicSwitch);
        soundEffectsSwitch = findViewById(R.id.soundSwitch);

        backToIntroPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MusicPlayer.setMusicState(musicSwitch.isChecked());
            }
        });
        soundEffectsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SoundPlayer.setSoundState(soundEffectsSwitch.isChecked());
            }
        });

        musicPlayer = MusicPlayerHolder.getMusicPlayer();
        musicPlayer.playMusic();
    }

    //    Implementing onDestroy, onPause, and onPostResume to handle music player
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