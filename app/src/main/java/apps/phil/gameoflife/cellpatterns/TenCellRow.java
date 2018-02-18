package apps.phil.gameoflife.cellpatterns;

import apps.phil.gameoflife.model.Cell;
import apps.phil.gameoflife.util.CellSizeInvestigator;

/**
 * Created by phil on 02.02.18.
 */

public class TenCellRow extends CellPattern {

    public TenCellRow() {
        super();
        initField();
        setPattern();
    }

    @Override
    protected void setPattern() {
        field[20][20].setIsAlive(true);
        field[20][21].setIsAlive(true);
        field[20][22].setIsAlive(true);
        field[20][23].setIsAlive(true);
        field[20][24].setIsAlive(true);
        field[20][25].setIsAlive(true);
        field[20][26].setIsAlive(true);
        field[20][27].setIsAlive(true);
        field[20][28].setIsAlive(true);
        field[20][29].setIsAlive(true);
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
