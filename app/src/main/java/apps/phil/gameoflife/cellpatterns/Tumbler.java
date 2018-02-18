package apps.phil.gameoflife.cellpatterns;

import apps.phil.gameoflife.model.Cell;
import apps.phil.gameoflife.util.CellSizeInvestigator;

/**
 * Created by phil on 02.02.18.
 */

public class Tumbler extends CellPattern {

    public Tumbler() {
        super();
        initField();
        setPattern();
    }

    @Override
    protected void setPattern() {
        field[30][31].setIsAlive(true);
        field[30][32].setIsAlive(true);
        field[31][31].setIsAlive(true);
        field[31][32].setIsAlive(true);
        field[32][32].setIsAlive(true);
        field[33][30].setIsAlive(true);
        field[33][32].setIsAlive(true);
        field[34][30].setIsAlive(true);
        field[34][32].setIsAlive(true);
        field[35][30].setIsAlive(true);
        field[35][31].setIsAlive(true);
        field[30][34].setIsAlive(true);
        field[30][35].setIsAlive(true);
        field[31][34].setIsAlive(true);
        field[31][35].setIsAlive(true);
        field[32][34].setIsAlive(true);
        field[33][34].setIsAlive(true);
        field[33][36].setIsAlive(true);
        field[34][34].setIsAlive(true);
        field[34][36].setIsAlive(true);
        field[35][35].setIsAlive(true);
        field[35][36].setIsAlive(true);
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
