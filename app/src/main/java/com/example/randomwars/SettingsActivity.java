package com.example.randomwars;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    Button backToIntroPageButton;
    Switch musicSwitch, soundEffectsSwitch;
    boolean musicState = true, soundState = true;
    String playerName;
    MusicPlayer musicPlayer;
    DatabaseReference userDB;

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

        userDB = FirebaseDatabase.getInstance().getReference("UserPreferences");
        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    playerNameText.setText(snapshot.child("PlayerName").getValue(String.class));
                    musicSwitch.setChecked(snapshot.child("Music").getValue(Boolean.class));
                    soundEffectsSwitch.setChecked(snapshot.child("Sound").getValue(Boolean.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        musicSwitch.setOnCheckedChangeListener(this);
        soundEffectsSwitch.setOnCheckedChangeListener(this);
        backToIntroPageButton.setOnClickListener(this);

        musicPlayer = MusicPlayerHolder.getMusicPlayer();
        musicPlayer.playMusic();

    }

    @Override
    public void onClick(View v) {
        playerName = ((EditText) findViewById(R.id.playerNameText)).getText().toString();

        userDB = FirebaseDatabase.getInstance().getReference("UserPreferences").child("PlayerName");
        userDB.setValue(playerName, null);

        userDB = FirebaseDatabase.getInstance().getReference("UserPreferences").child("Music");
        userDB.setValue(musicState, null);

        userDB = FirebaseDatabase.getInstance().getReference("UserPreferences").child("Sound");
        userDB.setValue(soundState, null);

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