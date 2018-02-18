package apps.phil.gameoflife.cellpatterns;

import apps.phil.gameoflife.model.Cell;
import apps.phil.gameoflife.util.CellSizeInvestigator;

/**
 * Created by phil on 01.02.18.
 */

public class Pulsator extends CellPattern {

    public Pulsator() {
        super();
        initField();
        setPattern();
    }

    @Override
    protected void setPattern() {
        field[10][12].setIsAlive(true);
        field[10][13].setIsAlive(true);
        field[10][14].setIsAlive(true);
        field[10][15].setIsAlive(true);
        field[10][16].setIsAlive(true);
        field[10][17].setIsAlive(true);
        field[11][11].setIsAlive(true);
        field[12][10].setIsAlive(true);
        field[13][11].setIsAlive(true);
        field[14][12].setIsAlive(true);
        field[14][13].setIsAlive(true);
        field[14][14].setIsAlive(true);
        field[14][15].setIsAlive(true);
        field[14][16].setIsAlive(true);
        field[14][17].setIsAlive(true);
        field[13][18].setIsAlive(true);
        field[12][19].setIsAlive(true);
        field[11][18].setIsAlive(true);
    }

    @Override
    protected void initField() {
        for(int row = 0; row < field.length; row++) {
            for(int column = 0; column < field[row].length; column++) {
                field[row][column] = new Cell(column,row, false, CellSizeInvestigator.getOptimalCellSize());
            }
        }
    }
}
