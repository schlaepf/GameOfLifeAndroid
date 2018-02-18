package apps.phil.gameoflife.activities;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import apps.phil.gameoflife.R;
import apps.phil.gameoflife.cellpatterns.PatternDisposer;
import apps.phil.gameoflife.controller.GameOfLife;
import apps.phil.gameoflife.model.GameRunningObserver;
import apps.phil.gameoflife.model.GenerationObserver;
import apps.phil.gameoflife.util.Config;
import apps.phil.gameoflife.view.UIBoard;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,
        GenerationObserver, SharedPreferences.OnSharedPreferenceChangeListener, GameRunningObserver {

    private static final String TAG = "MainActivity";

    private UIBoard uiBoard;
    private RelativeLayout rlUIBoard;

    private SeekBar seekbarSpeed;

    private TextView tvGeneration;
    private ImageView ivReload;
    private ImageView ivPauseRun;
    private ImageView ivPatterns;

    private GameOfLife gameOfLife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uiBoard = findViewById(R.id.ui_board_layout);
        rlUIBoard = findViewById(R.id.rl_ui_board);
        initImageViews();
        initOnClickListeners();
        initSeekbar();
        initTextViews();
        int pauseTime = Config.MAX_SPEED - seekbarSpeed.getProgress();
        gameOfLife = GameOfLife.getInstance(uiBoard, Config.DEFAULT_INITIAL_CELLS_ALIVE, this, pauseTime);
        gameOfLife.registerGeneration(this);
        gameOfLife.registerGameRunning(this);
    }

    private void initSeekbar() {
        seekbarSpeed = findViewById(R.id.seekBar_speed);
        seekbarSpeed.setMax(Config.MAX_SPEED);
        seekbarSpeed.setOnSeekBarChangeListener(this);
        seekbarSpeed.setProgress(Config.MAX_SPEED);
    }

    private void initTextViews() {
        tvGeneration = findViewById(R.id.tv_generation);
    }

    private void initOnClickListeners() {
        ivPauseRun.setOnClickListener((View v) -> gameOfLife.handlePauseRunClick());

        ivReload.setOnClickListener((View v) -> gameOfLife.handleRefresh());

        ivPatterns.setOnClickListener((View v) -> showPatternList());
    }

    private void initImageViews() {
        ivPauseRun = findViewById(R.id.iv_pause_run);
        ivReload = findViewById(R.id.iv_reload);
        ivPatterns = findViewById(R.id.iv_pattern);
    }

    private void showPatternList() {
        DialogFragment patternListFragment = new PatternListFragment();
        patternListFragment.show(getFragmentManager(), "patterns");
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameOfLife.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameOfLife.pause();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int speed, boolean fromUser) {
        if(fromUser) {
            speed = Config.MAX_SPEED - speed;
            gameOfLife.setPauseTime(speed);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void updateGeneration(int generation) {
        String strGeneration = "Generation " + generation;
        tvGeneration.setText(strGeneration);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(Config.KEY_PATTERN)) {
            String pattern = PreferenceManager.getDefaultSharedPreferences(this).getString(Config.KEY_PATTERN, "");
            PatternDisposer patternDisposer = PatternDisposer.getInstance(this);
            gameOfLife.showPattern(patternDisposer.getPattern(pattern));
            // clear preference, so that the next time the user clicks on a pattern, this method will
            // be fired again
            PreferenceManager.getDefaultSharedPreferences(this).edit().clear().commit();
        }
    }

    @Override
    public void updateGameRunning(boolean running, boolean paused) {
        if(!running || paused) {
            ivPauseRun.setImageDrawable(getDrawable(R.drawable.ic_play_arrow_white_48dp));
        } else {
            ivPauseRun.setImageDrawable(getDrawable(R.drawable.ic_pause_white_48dp));
        }
    }
}
