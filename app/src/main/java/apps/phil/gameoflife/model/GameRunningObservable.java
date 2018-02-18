package apps.phil.gameoflife.model;

/**
 * Created by phil on 02.02.18.
 */

public interface GameRunningObservable {
    public void registerGameRunning(GameRunningObserver observer);
    public void unregisterGameRunning(GameRunningObserver observer);
    public void notifyGameRunningObservers();
}
