package apps.phil.gameoflife.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by phil on 30.01.18.
 */

public class CellSizeInvestigator {



    // given: one cell takes 10 pixels height and width, the game of life board takes two thirds of the screen
    // height and the controls take one third of the screen height -> the rest is calculated based
    // on these facts

    private static String TAG = "CellSizeInvestigator";

    private static int SCREEN_DIVIDER = 3;
    private static int SCREEN_UPPER = 2;
    private static int SCREEN_LOWER = 1;

    private static int screenWidth;
    private static int totalScreenHeight;
    private static int screenHeightUpper;
    private static int screenHeightLower;
    private static int cellsPerColumn;
    private static int cellsPerRow;

    private static int cellSize = 10;       // cell size in pixel


    private WindowManager wm;

    private Context context;

    public CellSizeInvestigator(Context context) {
        this.context = context;
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        setScreenWidth();
        setTotalScreenHeight();
        setScreenHeightLower();
        setScreenHeightUpper();
        setCellsPerColumn();
        setCellsPerRow();

        Log.d(TAG, "total height " + totalScreenHeight);
        Log.d(TAG, "height " + screenHeightUpper);
        Log.d(TAG, "row " + cellsPerRow);
        Log.d(TAG, "column " + cellsPerColumn);
    }

    public static int getOptimalCellSize() {
        return cellSize;
    }

    private void setScreenHeightLower() {
        screenHeightLower = totalScreenHeight/SCREEN_DIVIDER;
    }

    public int getLowerScreenHeight() {
        return screenHeightLower;
    }

    private void setScreenHeightUpper() {
        screenHeightUpper = (totalScreenHeight/SCREEN_DIVIDER)*SCREEN_UPPER;
    }

    public int getUpperScreenHeight() {
        return screenHeightUpper;
    }

    private void setCellsPerColumn() {
        cellsPerColumn = screenHeightUpper/cellSize;
    }

    private void setCellsPerRow() {
        cellsPerRow = screenWidth / cellSize;
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
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        totalScreenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
    }

    public static int getCellsPerRow() {
        return cellsPerRow;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }
}
