package apps.phil.gameoflife.model;

/**
 * Created by phil on 29.01.18.
 */

public interface GenerationObservable {
    public void registerGeneration(GenerationObserver observer);
    public void unregisterGeneration(GenerationObserver observer);
    public void notifyGenerationObservers();
}
