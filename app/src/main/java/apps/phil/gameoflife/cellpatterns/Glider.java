package apps.phil.gameoflife.cellpatterns;

import apps.phil.gameoflife.model.Cell;
import apps.phil.gameoflife.util.CellSizeInvestigator;

/**
 * Created by phil on 01.02.18.
 */

public class Glider extends CellPattern {

    public Glider() {
    	super();
    	initField();
		setPattern();
    }

    @Override
    protected void setPattern() {
		field[0][1].setIsAlive(true);
		field[1][2].setIsAlive(true);
		field[2][0].setIsAlive(true);
		field[2][1].setIsAlive(true);
		field[2][2].setIsAlive(true);
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
