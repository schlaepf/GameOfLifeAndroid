package apps.phil.gameoflife.cellpatterns;

import apps.phil.gameoflife.model.Cell;
import apps.phil.gameoflife.util.CellSizeInvestigator;

/**
 * Created by phil on 01.02.18.
 */

public class TwoMagnets extends CellPattern {

    public TwoMagnets () {
        super();
        initField();
        setPattern();
    }

    @Override
    protected void setPattern() {
        field[30][30].setIsAlive(true);
        field[30][31].setIsAlive(true);
        field[30][32].setIsAlive(true);
        field[31][30].setIsAlive(true);
        field[31][32].setIsAlive(true);
        field[32][30].setIsAlive(true);
        field[32][32].setIsAlive(true);
        field[34][30].setIsAlive(true);
        field[34][32].setIsAlive(true);
        field[35][30].setIsAlive(true);
        field[35][32].setIsAlive(true);
        field[36][30].setIsAlive(true);
        field[36][31].setIsAlive(true);
        field[36][32].setIsAlive(true);
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
