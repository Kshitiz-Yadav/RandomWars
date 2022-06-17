/*
    Class used to play sound effects during the game.
    SoundPool object is responsible for storing and playing the sounds whenever a call is made to it.
    The HashMap allows us to access the soundPool sounds by the use of understandable integer variable names.
    soundState represents if the game sound is turned on or off in the settings.
 */

package com.example.randomwars;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import java.util.HashMap;

public class SoundPlayer {

    SoundPool soundPool;
    HashMap<Integer,Integer> soundMap;
    Context context;
    public static boolean soundState;

    public SoundPlayer(Context context){
        soundPool = new SoundPool(9, AudioManager.STREAM_MUSIC,100);
        soundMap = new HashMap<>();
        this.context = context;

        // Declaring and initializing variables for the HashMap and soundPool.
        int PLAYER_BULLET = 1;
        int TANK_BULLET = 2;
        int PLAYER_HIT = 3;
        int SOLDIER_KILL = 4;
        int TANK_DESTROY = 5;
        int FIRST_AID = 6;
        int ATOM_BOMB = 7;
        int LEVEL_UP = 8;
        int GAME_OVER = 9;

        // Loading all sounds in the soundPool as well as entering integer to sound references in the HashMap.
        if(soundPool != null){
            soundMap.put(PLAYER_BULLET, soundPool.load(context, R.raw.player_bullet, 1));
            soundMap.put(TANK_BULLET, soundPool.load(context, R.raw.tank_shoot, 1));
            soundMap.put(PLAYER_HIT, soundPool.load(context, R.raw.player_hurt, 1));
            soundMap.put(SOLDIER_KILL, soundPool.load(context, R.raw.soldier_kill, 1));
            soundMap.put(TANK_DESTROY, soundPool.load(context, R.raw.tank_destroy, 1));
            soundMap.put(FIRST_AID, soundPool.load(context, R.raw.first_aid, 1));
            soundMap.put(ATOM_BOMB, soundPool.load(context, R.raw.atom_bomb, 1));
            soundMap.put(LEVEL_UP, soundPool.load(context, R.raw.level_up, 1));
            soundMap.put(GAME_OVER, soundPool.load(context, R.raw.game_over, 1));
        }

        // Setting the value of soundState to the previously set value to determine if sounds should be played or not
        SharedPreferences userPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        soundState = userPreferences.getBoolean("Sound", true);
    }

    /*
        Function to set soundState from settingsActivity
        Since these sounds are not continuously played, there is no need to change the state of soundPool from play to stop or vice-versa
        Refer to musicState in MusicPLayer for better understanding
    */
    public static void setSoundState(boolean state){
        soundState = state;
    }

    /*
        Function to play the corresponding sound whenever an activity happens
        Using if-statement to bring soundState to use
     */
    public void playSound(int sound) {
        AudioManager mgr = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = streamVolumeCurrent / streamVolumeMax;

        if(soundPool != null && soundState){
            // Suppressing soundMap.get(sound) may produce NULL pointer warning ->
            // noinspection ConstantConditions
            soundPool.play(soundMap.get(sound), volume, volume, 1, 0, 1.0f);
        }
    }
}