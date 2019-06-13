package com.imagine.klotski;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

class AudioPlayer {
    private static SoundPool soundPool;
    private static int moveSoundID;
    private static int victorySoundID;
    private static int selectSoundID;

    AudioPlayer(Context context) {
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        moveSoundID = soundPool.load(context, R.raw.move, 0);
        victorySoundID = soundPool.load(context, R.raw.victory, 1);
        selectSoundID = soundPool.load(context, R.raw.select, 2);
    }

    void playMoveSound() {
        soundPool.play(moveSoundID, 0.5f, 0.5f, 0, 0, 0);
    }

    void playVictorySound() {
        soundPool.play(victorySoundID, 0.5f, 0.5f, 1, 0, 0);
    }

    void playSelectSound() {
        soundPool.play(selectSoundID, 0.5f, 0.5f, 2, 0, 0);
    }
}
