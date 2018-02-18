package apps.phil.gameoflife.util;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by phil on 30.01.18.
 */

public class CellSizeInvestigator {

    // given: one row consists of 100 cells, the game of life board takes two thirds of the screen
    // height and the controls take one third of the screen height -> the rest is calculated based
    // on these facts

    private static float SCREEN_DIVIDE_UPPER = 2/3f;
    private static float SCREEN_DIVIDE_LOWER = 1/3f;
    private static int CELLS_PER_ROW = 100;

    private static int screenWidth;
    private static int totalScreenHeight;
    private static int screenHeightUpper;
    private static int screenHeightLower;
    private static int cellsPerColumn;

    private static int cellSize;

    private WindowManager wm;

    public CellSizeInvestigator(Context context) {
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        setScreenWidth();
        setTotalScreenHeight();
        setOptimalCellSize();
        setScreenHeightLower();
        setScreenHeightUpper();
        setCellsPerColumn();
    }

    private void setOptimalCellSize() {
        cellSize = screenWidth / CELLS_PER_ROW;
    }

    public static int getOptimalCellSize() {
        return cellSize;
    }

    private void setScreenHeightLower() {
        screenHeightLower = (int) ((float)totalScreenHeight*SCREEN_DIVIDE_LOWER);
    }

    public int getLowerScreenHeight() {
        return screenHeightLower;
    }

    private void setScreenHeightUpper() {
        screenHeightUpper = (int) ((float)totalScreenHeight*SCREEN_DIVIDE_UPPER);
    }

    public int getUpperScreenHeight() {
        return screenHeightUpper;
    }

    private void setCellsPerColumn() {
        cellsPerColumn = screenHeightUpper/cellSize;
    }

    public static int getCellsPerColumn() {
        return cellsPerColumn;
    }

    private void setScreenWidth() {
        Display display = wm.getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        screenWidth = p.x;
    }

    private void setTotalScreenHeight() {
        Display display = wm.getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        totalScreenHeight = p.y;
    }

    public static int getCellsPerRow() {
        return CELLS_PER_ROW;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }
}
