package apps.phil.gameoflife.model;

/**
 * Created by phil on 02.02.18.
 */

public interface GameRunningObserver {
    public void updateGameRunning(boolean running, boolean paused);
}
