package com.example.randomwars;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

public class MusicPlayer {

    public static MediaPlayer mediaPlayer;
    Context context;
    public static boolean musicState;

    public MusicPlayer(Context context, int music){
        mediaPlayer = new MediaPlayer();
        this.context = context;
        
        SharedPreferences userPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        musicState = userPreferences.getBoolean("Music", true);
        
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
        mediaPlayer.setLooping(true);
    }

    public static void setMusicState(boolean state){
        musicState = state;
        if(musicState){
            mediaPlayer.start();
        }
        else{
            mediaPlayer.pause();
        }
    }

    public void playMusic() {
        mediaPlayer.start();
        if (!musicState) {
            mediaPlayer.pause();
        }
    }

    public void pauseMusic(){
        mediaPlayer.pause();
    }
}
