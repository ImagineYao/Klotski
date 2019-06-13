package com.imagine.klotski;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private AudioPlayer audioPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioPlayer = new AudioPlayer(this);
    }

    public void selectLevel(View view) {
        audioPlayer.playSelectSound();
        Intent intent = new Intent(this, LevelActivity.class);
        startActivity(intent);
    }

    public void exit(View view) {
        audioPlayer.playSelectSound();
        finish();
        System.exit(0);
    }
}
