// The opening splash page of the app containing two icons: the Madman logo and the Random Wars game icon

package com.example.randomwars;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Code to set activity as fullscreen, remove title bar, hide navigation buttons and hiding system bars.
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        // Creating a MediaPlayer object that plays splash screen music when the screen opens
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.splash_screen_music);
        mediaPlayer.start();

        // Thread to to keep the user on splash screen by timing out for some time using sleep function
        Thread t = new Thread() {
            public void run() {
                try{
                    sleep(2500);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally{
                    // Going to the main menu page once the thread awakes from sleep
                    Intent toIntro = new Intent(SplashActivity.this, IntroPageActivity.class);
                    startActivity(toIntro);
                }
            }
        };
        t.start();
    }
}