package apps.phil.gameoflife.cellpatterns;


import apps.phil.gameoflife.model.Cell;
import apps.phil.gameoflife.util.CellSizeInvestigator;

/**
 * Created by phil on 01.02.18.
 */

public abstract class CellPattern {
    protected Cell[][] field;

    public CellPattern() {
        field = new Cell[CellSizeInvestigator.getCellsPerRow()][CellSizeInvestigator.getCellsPerColumn()];
    }

    public Cell[][] getField() {
        return field;
    }

    protected abstract void setPattern();

    protected abstract void initField();
}
