package com.example.randomwars;

public class MusicPlayerHolder {

    public static MusicPlayer musicPlayer;

    public static void setMusicPlayer(MusicPlayer player){
        musicPlayer = player;
    }

    public static MusicPlayer getMusicPlayer(){
        return musicPlayer;
    }
}
