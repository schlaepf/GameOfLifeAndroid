package apps.phil.gameoflife.view;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.LinkedList;

import apps.phil.gameoflife.model.Cell;
import apps.phil.gameoflife.util.CellSizeInvestigator;

/**
 * Created by phil on 28.01.18.
 */

public class UIBoard extends SurfaceView implements SurfaceHolder.Callback, CellClickObservable {

    //TODO make this view render in layout preview
    // --> https://developer.android.com/training/custom-views/create-view.html

    //TODO only redraw cells, which have changed (for example: put all changes in a list and then
    // iterate through it)

    private Cell[][] grid;
    private Rect rect;

    private Paint colorAlive;
    private Paint colorDead;

    private Context context;

    private SurfaceHolder surfaceHolder;

    private int sizeOfSquare;

    private static final String TAG = "UIBoard";

    CellSizeInvestigator cellSizeInvestigator;

    private ArrayList<CellClickObserver> cellClickObservers;

    private LinkedList<Cell> updatedCells;

    public UIBoard(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        cellClickObservers = new ArrayList<>();
        this.context = context;
        cellSizeInvestigator = new CellSizeInvestigator(context);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        initialize();
        rect = new Rect();

        colorAlive = new Paint();
        colorAlive.setColor(Color.MAGENTA);
        colorAlive.setStyle(Paint.Style.FILL);

        colorDead = new Paint();
        colorDead.setColor(Color.argb(255, 53,53,53));
        colorDead.setStyle(Paint.Style.FILL);
        updatedCells = new LinkedList<>();
    }

    private void initialize() {
        grid = new Cell[CellSizeInvestigator.getCellsPerRow()][CellSizeInvestigator.getCellsPerColumn()];
        int x = 0;
        int y = 0;
        sizeOfSquare = CellSizeInvestigator.getOptimalCellSize();
        for(int row = 0; row < grid.length; row++) {
            for(int column = 0; column < grid[row].length; column++) {
                Cell square = new Cell(x,y, false, sizeOfSquare);
                grid[row][column] = square;
                x+=sizeOfSquare;
            }
            y += sizeOfSquare;
            x = 0;
        }
    }

    public void update(LinkedList<Cell> updatedCells) {
        while(updatedCells.size() > 0) {
            Cell cell = updatedCells.removeFirst();
            grid[cell.getY()][cell.getX()].setIsAlive(cell.isAlive());
        }
        this.updatedCells = updatedCells;
        updateCanvas();
        invalidate();
    }

    public void updateWholeField(Cell[][] field) {
        for(int row = 0; row < field.length; row++) {
            for (int column = 0; column < field[row].length; column++) {
                Cell cell = field[row][column];
                grid[row][column].setIsAlive(cell.isAlive());
            }
        }
        updateCanvas();
        invalidate();
    }

    public void updateCanvas() {
        Canvas canvas = surfaceHolder.lockCanvas();
        if(canvas == null) {
            Log.d(TAG, "Canvas is null");
            return;
        }
        for(int row = 0; row < grid.length; row++) {
            for(int column = 0; column < grid[row].length; column++) {
                Cell square = grid[row][column];
                rect.set(square.getX(), square.getY(), square.getX()+square.getSize(), square.getY()+square.getSize());
                if(square.isAlive()) {
                    canvas.drawRect(rect,colorAlive);
                } else {
                    canvas.drawRect(rect,colorDead);
                }
            }
        }
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        updateCanvas();
        invalidate();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int column = (int)event.getX()/sizeOfSquare;
        int row = (int)event.getY()/sizeOfSquare;
        notifyCellClickObservers(row, column);
        performClick();
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        // Calls the super implementation, which generates an AccessibilityEvent
        // and calls the onClick() listener on the view, if any
        super.performClick();

        // Handle the action for the custom click here
        return true;
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        Log.d(TAG, "drag");
        return super.onDragEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(CellSizeInvestigator.getScreenWidth() ,cellSizeInvestigator.getUpperScreenHeight());
    }

    @Override
    public void registerCellClick(CellClickObserver observer) {
        cellClickObservers.add(observer);
    }

    @Override
    public void unregisterCellClick(CellClickObserver observer) {
        cellClickObservers.remove(observer);
    }

    @Override
    public void notifyCellClickObservers(int row, int column) {
        for(CellClickObserver observer : cellClickObservers) {
            observer.updateCellClickObserver(row, column);
        }
    }
}
