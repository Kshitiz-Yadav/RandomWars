package com.example.randomwars;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    Button backToIntroPageButton;
    Switch musicSwitch, soundEffectsSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        MusicPlayer.play();

        backToIntroPageButton = findViewById(R.id.backButton);
        musicSwitch = findViewById(R.id.musicSwitch);
        soundEffectsSwitch = findViewById(R.id.soundSwitch);

        backToIntroPageButton.setOnClickListener(this);
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MusicPlayer.setMusicOn(musicSwitch.isChecked());
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent backToIntro = new Intent(SettingsActivity.this, IntroPageActivity.class);
        backToIntro.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(backToIntro);
    }
}