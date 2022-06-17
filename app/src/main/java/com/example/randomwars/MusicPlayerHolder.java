/*
    Class that allows us to pass the MusicPlayer object from one activity to another
    IntroPageActivity, HighScoreActivity, HowToPlayActivity and SettingsActivity use the same musicPlayer object to keep the music in continuation
    The musicPlayer object is created in IntroPageActivity and then passed on to the corresponding activities when the user visits them
 */

package com.example.randomwars;

import android.annotation.SuppressLint;

public class MusicPlayerHolder {

    /*
        A musicPlayer uses "context" of an activity to create the mediaPlayer,
        so making such object static means that the "context" info can be accessed by other activities as well.
        Hence, we get a memory leak warning which is suppressed below.
     */
    @SuppressLint("StaticFieldLeak")
    public static MusicPlayer musicPlayer;

    // Function to set musicPlayer object from IntroPageActivity
    public static void setMusicPlayer(MusicPlayer player){
        musicPlayer = player;
    }

    // Function to get musicPlayer object from the other above-mentioned activities
    public static MusicPlayer getMusicPlayer(){
        return musicPlayer;
    }
}