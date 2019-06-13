package com.imagine.klotski;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    private static GameActivity gameActivity = null;
    public int step = 0;
    public AudioPlayer audioPlayer;
    private int level;
    private TextView currentStepText;
    private VictoryDialog victoryDialog;

    public GameActivity() {
        gameActivity = this;
    }

    public static GameActivity getGameActivity() {
        return gameActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        currentStepText = findViewById(R.id.current_step);
        currentStepText.setText(getResources().getString(R.string.current_step) + "0");

        TextView levelNameText = findViewById(R.id.level_name);
        TextView bestStepText = findViewById(R.id.best_step);
        levelNameText.setText(getIntent().getStringExtra("levelName"));
        bestStepText.setText(getString(R.string.best_step) + getIntent().getStringExtra("bestStep"));

        level = GameBoard.level;
        setTitle(getTitle(level));

        audioPlayer = new AudioPlayer(this);
    }

    private String getTitle(int level) {
        String[] titles = {"第一关", "第二关", "第三关", "第四关", "第五关"};
        return titles[level - 1];
    }

    public void showStep() {
        String text = getResources().getString(R.string.current_step) + Integer.toString(step);
        this.currentStepText.setText(text);
    }

    public void increaseStep(int step) {
        this.step += step;
        showStep();
    }

    public void finishGame() {
        LevelActivity.getActivity().setData(step);
        audioPlayer.playVictorySound();
        showDialog();
    }

    public void showDialog() {
        victoryDialog = new VictoryDialog();
        victoryDialog.show(getSupportFragmentManager(), "victory");
    }

    public void reselect(View view) {
        audioPlayer.playSelectSound();
        finish();
    }

    public void toPrevious(View view) {
        audioPlayer.playSelectSound();
        int level = GameBoard.level;
        if (level != 1) {
            finish();
            LevelActivity.getActivity().startLevel(level - 1);
        } else {
            Toast.makeText(this, "已经是第一关啦~", Toast.LENGTH_SHORT).show();
        }
    }

    public void restart(View view) {
        audioPlayer.playSelectSound();
        finish();
        LevelActivity.getActivity().startLevel(level);
        if (victoryDialog != null) {
            victoryDialog.dismiss();
        }
    }

    public void toNext(View view) {
        audioPlayer.playSelectSound();
        if (level != 5) {
            finish();
            LevelActivity.getActivity().startLevel(level + 1);
        } else {
            Toast.makeText(this, "已经是最后一关啦~", Toast.LENGTH_SHORT).show();
        }
    }
}
