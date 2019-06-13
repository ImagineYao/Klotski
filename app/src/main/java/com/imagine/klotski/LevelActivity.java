package com.imagine.klotski;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class LevelActivity extends AppCompatActivity {

    private static LevelActivity levelActivity = null;
    private final String[] levelKeys =
            {"LEVEL_ONE", "LEVEL_TWO", "LEVEL_THREE", "LEVEL_FOUR", "LEVEL_FIVE"};
    private SharedPreferences mPreferences;
    private int[] levelSteps = new int[5];

    private AudioPlayer audioPlayer;

    public LevelActivity() {
        levelActivity = this;
    }

    public static LevelActivity getActivity() {
        return levelActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        audioPlayer = new AudioPlayer(this);

        String sharedPrefFile = "com.imagine.klotski";
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreference();
        initData();
    }

    private void getPreference() {
        for (int i = 0; i < 5; i++)
            levelSteps[i] = mPreferences.getInt(levelKeys[i], 0);
    }

    void initData() {
        TextView levelOneStepText = findViewById(R.id.level_one_step);
        TextView levelTwoStepText = findViewById(R.id.level_two_step);
        TextView levelThreeStepText = findViewById(R.id.level_three_step);
        TextView levelFourStepText = findViewById(R.id.level_four_step);
        TextView levelFiveStepText = findViewById(R.id.level_five_step);

        String text = getString(R.string.best_step);

        levelOneStepText.setText(text + getStep(levelSteps[0]));
        levelTwoStepText.setText(text + getStep(levelSteps[1]));
        levelThreeStepText.setText(text + getStep(levelSteps[2]));
        levelFourStepText.setText(text + getStep(levelSteps[3]));
        levelFiveStepText.setText(text + getStep(levelSteps[4]));
    }

    String getStep(int step) {
        if (step == 0) {
            return "暂无数据";
        } else {
            return Integer.toString(step);
        }
    }

    void setData(int step) {
        SharedPreferences.Editor editor = mPreferences.edit();
        int level = GameBoard.level;
        if (levelSteps[level - 1] == 0) {
            editor.putInt(levelKeys[level - 1], step);
        } else if (step < levelSteps[level - 1]) {
            editor.putInt(levelKeys[level - 1], step);
        }
        editor.apply();
    }

    public void enterGame(View view) {
        audioPlayer.playSelectSound();
        startGame(view.getId());
    }

    private void startGame(int levelId) {
        getPreference();
        String levelName = null;
        String bestStep = null;
        Intent intent = new Intent(getActivity(), GameActivity.class);

        switch (levelId) {
            case R.id.level_one:
                GameBoard.level = 1;
                levelName = getString(R.string.level_one_name);
                bestStep = getStep(levelSteps[0]);
                break;
            case R.id.level_two:
                GameBoard.level = 2;
                levelName = getString(R.string.level_two_name);
                bestStep = getStep(levelSteps[1]);
                break;
            case R.id.level_three:
                GameBoard.level = 3;
                levelName = getString(R.string.level_three_name);
                bestStep = getStep(levelSteps[2]);
                break;
            case R.id.level_four:
                GameBoard.level = 4;
                levelName = getString(R.string.level_four_name);
                bestStep = getStep(levelSteps[3]);
                break;
            case R.id.level_five:
                GameBoard.level = 5;
                levelName = getString(R.string.level_five_name);
                bestStep = getStep(levelSteps[4]);
                break;
        }
        intent.putExtra("levelName", levelName);
        intent.putExtra("bestStep", bestStep);
        startActivity(intent);
    }

    public void startLevel(int level) {
        getPreference();
        String levelName = null;
        String bestStep = null;
        Intent intent = new Intent(getActivity(), GameActivity.class);

        switch (level) {
            case 1:
                GameBoard.level = 1;
                levelName = getString(R.string.level_one_name);
                bestStep = getStep(levelSteps[0]);
                break;
            case 2:
                GameBoard.level = 2;
                levelName = getString(R.string.level_two_name);
                bestStep = getStep(levelSteps[1]);
                break;
            case 3:
                GameBoard.level = 3;
                levelName = getString(R.string.level_three_name);
                bestStep = getStep(levelSteps[2]);
                break;
            case 4:
                GameBoard.level = 4;
                levelName = getString(R.string.level_four_name);
                bestStep = getStep(levelSteps[3]);
                break;
            case 5:
                GameBoard.level = 5;
                levelName = getString(R.string.level_five_name);
                bestStep = getStep(levelSteps[4]);
                break;
        }
        intent.putExtra("levelName", levelName);
        intent.putExtra("bestStep", bestStep);
        startActivity(intent);
    }
}
