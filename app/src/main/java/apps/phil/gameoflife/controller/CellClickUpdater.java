package apps.phil.gameoflife.controller;

/**
 * Created by phil on 26.02.18.
 */

public class CellClickUpdater implements Runnable {

    private static CellClickUpdater instance;

    private int row;
    private int column;

    @Override
    public void run() {
        updateCellClick();
    }

    private void updateCellClick() {
        GameOfLife.getInstance().reviveCell(row, column);
    }

    public static CellClickUpdater getInstance() {
        if(instance ==  null) {
            instance = new CellClickUpdater();
        }
        return instance;
    }

    /**
     * Call this method before updateCellClick is called.
     * @param row       the row of the cell which has to be revived
     * @param column    the column of the cell which has to be revived
     */
    public void setNewCoordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }


}
