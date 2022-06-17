/*
    Class used to play music in main menu and game over page and during the game as well
    The mediaPlayer is loaded with the musics in the constructor
    musicState represents if the music has been turned on or off form setting
 */

package com.example.randomwars;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

public class MusicPlayer {
    public static MediaPlayer mediaPlayer;
    Context context;
    public static boolean musicState;

    public MusicPlayer(Context context, int music){
        // Initializing the MediaPlayer object
        mediaPlayer = new MediaPlayer();
        this.context = context;

        // Setting the value of musicState to the previously set value to determine if music should be played or not
        SharedPreferences userPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        musicState = userPreferences.getBoolean("Music", true);

        // creating the mediaPlayer depending on from which page the call has been generated
        switch (music){
            case 1:
                mediaPlayer = MediaPlayer.create(context, R.raw.intro_page_music);
                break;
            case 2:
                mediaPlayer = MediaPlayer.create(context, R.raw.game_area_music);
                break;
            case 3:
                mediaPlayer = MediaPlayer.create(context, R.raw.game_over_music);
        }

        // setting looping to true allows music to play forever
        mediaPlayer.setLooping(true);
    }

    /*
        Function to set musicState from settingsActivity
        Since these musics are continuously played, we need to set their state from play to pause or vice-versa depending on musicState
    */
    public static void setMusicState(boolean state){
        musicState = state;
        if(musicState){
            mediaPlayer.start();
        }
        else{
            mediaPlayer.pause();
        }
    }

    /*
        Function to start playing the music
        This is called whenever the the corresponding activity is created or resumed
    */
    public void playMusic() {
        mediaPlayer.start();
        if (!musicState) {
            mediaPlayer.pause();
        }
    }

    /*
        Function to pause the music
        This is called whenever the corresponding activity is paused or destroyed
     */
    public void pauseMusic(){
        mediaPlayer.pause();
    }
}