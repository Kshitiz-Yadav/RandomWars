package com.example.randomwars;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.splash_screen_music);
        mediaPlayer.start();
        Thread t = new Thread() {
            public void run() {
                try{
                    sleep(2500);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally{
                    Intent toMain = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(toMain);
                }
            }
        };

        t.start();
    }
}