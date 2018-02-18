package apps.phil.gameoflife.cellpatterns;

import apps.phil.gameoflife.model.Cell;
import apps.phil.gameoflife.util.CellSizeInvestigator;

/**
 * Created by phil on 01.02.18.
 */

public class LightWeightSpaceShip extends CellPattern {

    public LightWeightSpaceShip() {
        super();
        initField();
        setPattern();
    }

    @Override
    protected void setPattern() {

        field[10][10].setIsAlive(true);
        field[12][10].setIsAlive(true);
        field[10][13].setIsAlive(true);
        field[11][14].setIsAlive(true);
        field[12][14].setIsAlive(true);
        field[13][14].setIsAlive(true);
        field[13][13].setIsAlive(true);
        field[13][12].setIsAlive(true);
        field[13][11].setIsAlive(true);
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
