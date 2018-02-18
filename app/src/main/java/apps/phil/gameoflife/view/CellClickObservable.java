package apps.phil.gameoflife.view;

/**
 * Created by phil on 03.02.18.
 */

public interface CellClickObservable {
    public void registerCellClick(CellClickObserver observer);
    public void unregisterCellClick(CellClickObserver observer);
    public void notifyCellClickObservers(int row, int column);
}
