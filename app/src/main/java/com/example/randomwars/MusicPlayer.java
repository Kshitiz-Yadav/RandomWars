package com.example.randomwars;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicPlayer {

    public static MediaPlayer mediaPlayer;
    Context context;
    public static boolean musicState = true;

    public MusicPlayer(Context context, int music){
        mediaPlayer = new MediaPlayer();
        this.context = context;

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
        if(mediaPlayer.isPlaying() && !musicState){
            mediaPlayer.pause();
        }
        else if(!mediaPlayer.isPlaying() && musicState){
            mediaPlayer.start();
        }
    }

    public void playMusic() {
        if (musicState) {
            mediaPlayer.start();
        }
    }

    public void pauseMusic(){
        mediaPlayer.pause();
    }
}
