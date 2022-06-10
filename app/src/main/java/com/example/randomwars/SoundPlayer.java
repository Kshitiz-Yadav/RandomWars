package com.example.randomwars;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

public class SoundPlayer {

    SoundPool soundPool;
    HashMap<Integer,Integer> soundMap;
    Context context;
    public static boolean soundState = true;

    private final int PLAYER_BULLET = 1;
    private final int TANK_BULLET = 2;
    private final int PLAYER_HIT = 3;
    private final int SOLDIER_KILL = 4;
    private final int TANK_DESTROY = 5;
    private final int FIRST_AID = 6;
    private final int ATOM_BOMB = 7;
    private final int LEVEL_UP = 8;
    private final int GAME_OVER = 9;

    public SoundPlayer(Context context){
        soundPool = new SoundPool(9, AudioManager.STREAM_MUSIC,100);
        soundMap = new HashMap<>();
        this.context = context;

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
    }

    public static void setSoundState(boolean state){
        soundState = state;
    }

    public void playSound(int sound) {
        AudioManager mgr = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = streamVolumeCurrent / streamVolumeMax;

        if(soundPool != null && soundState){
            soundPool.play(soundMap.get(sound), volume, volume, 1, 0, 1.0f);
        }
    }
}
