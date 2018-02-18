package apps.phil.gameoflife.cellpatterns;

import apps.phil.gameoflife.model.Cell;
import apps.phil.gameoflife.util.CellSizeInvestigator;

/**
 * Created by phil on 01.02.18.
 */

public class FPentomino extends CellPattern {

    public FPentomino() {
        super();
        initField();
        setPattern();
    }

    @Override
    protected void setPattern() {
        field[50][51].setIsAlive(true);
        field[50][52].setIsAlive(true);
        field[51][50].setIsAlive(true);
        field[51][51].setIsAlive(true);
        field[52][51].setIsAlive(true);
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
