package apps.phil.gameoflife.cellpatterns;

import apps.phil.gameoflife.model.Cell;
import apps.phil.gameoflife.util.CellSizeInvestigator;

/**
 * Created by phil on 02.02.18.
 */

public class GliderGun extends CellPattern {

    public GliderGun() {
        super();
        initField();
        setPattern();
    }

    @Override
    protected void setPattern() {
        field[22][20].setIsAlive(true);
        field[23][20].setIsAlive(true);
        field[22][21].setIsAlive(true);
        field[23][21].setIsAlive(true);
        field[22][29].setIsAlive(true);
        field[22][30].setIsAlive(true);
        field[23][28].setIsAlive(true);
        field[23][30].setIsAlive(true);
        field[24][28].setIsAlive(true);
        field[24][29].setIsAlive(true);
        field[21][42].setIsAlive(true);
        field[22][42].setIsAlive(true);
        field[20][43].setIsAlive(true);
        field[22][43].setIsAlive(true);
        field[20][44].setIsAlive(true);
        field[21][44].setIsAlive(true);
        field[20][54].setIsAlive(true);
        field[21][54].setIsAlive(true);
        field[20][55].setIsAlive(true);
        field[21][55].setIsAlive(true);
        field[27][55].setIsAlive(true);
        field[28][55].setIsAlive(true);
        field[29][55].setIsAlive(true);
        field[27][56].setIsAlive(true);
        field[28][57].setIsAlive(true);
        field[32][44].setIsAlive(true);
        field[33][44].setIsAlive(true);
        field[32][45].setIsAlive(true);
        field[32][46].setIsAlive(true);
        field[34][45].setIsAlive(true);
        field[24][36].setIsAlive(true);
        field[24][37].setIsAlive(true);
        field[25][36].setIsAlive(true);
        field[26][36].setIsAlive(true);
        field[25][38].setIsAlive(true);
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
