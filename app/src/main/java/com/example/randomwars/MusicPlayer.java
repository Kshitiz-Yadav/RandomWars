package com.example.randomwars;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicPlayer{

    public static MediaPlayer player;
    private static boolean musicOn = true;


    MusicPlayer(Context context){
        player = MediaPlayer.create(context, R.raw.intro_page_music);
    }

    public static void play() {
        if(musicOn){
            player.start();
        }
    }

    public static void setMusicOn(boolean state){
        musicOn = state;
        if(!state){
            player.pause();
        }
        else{
            player.start();
        }
    }

    public static void pause() {
        player.pause();
    }

    public static void stop(){
        player.stop();
    }
}
