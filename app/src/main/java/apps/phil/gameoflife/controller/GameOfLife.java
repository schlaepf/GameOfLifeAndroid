package apps.phil.gameoflife.controller;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import apps.phil.gameoflife.cellpatterns.CellPattern;
import apps.phil.gameoflife.model.Cell;
import apps.phil.gameoflife.model.GameRunningObservable;
import apps.phil.gameoflife.model.GameRunningObserver;
import apps.phil.gameoflife.model.GenerationObservable;
import apps.phil.gameoflife.model.GenerationObserver;
import apps.phil.gameoflife.util.CellSizeInvestigator;
import apps.phil.gameoflife.view.UIBoard;

public class GameOfLife implements Runnable, GenerationObservable, GameRunningObservable {

    private static final String TAG = "GameOfLifeController";

    // Singleton
    private static GameOfLife instance;

    // variables to pause and resume this thread/the game
    private boolean running;
    private boolean paused;
    private final Object pauseLock;

    private int numberOfColumns;
    private int numberOfRows;

    private Cell[][] field;
    private Cell[][] newField;
    private int generation;
    private int pauseTime;
    private int initialCellsAlive;

    private UIBoard ui;
    private Activity mainActivity;

    // observers
    private ArrayList<GenerationObserver> generationObservers;
    private ArrayList<GameRunningObserver> gameRunningObservers;

    // changed cells
    private LinkedBlockingQueue<Cell> changedCells;

    public GameOfLife(UIBoard ui, int initialCellsAlive, Activity mainActivity, int pauseTime) {
        this.ui = ui;
        changedCells = new LinkedBlockingQueue<>();
        this.pauseTime = pauseTime;
        paused = false;
        pauseLock = new Object();
        this.initialCellsAlive = initialCellsAlive;
        this.mainActivity = mainActivity;
        initDimensions();
        initObservers();
        field = new Cell[numberOfRows][numberOfColumns];
        newField = new Cell[numberOfRows][numberOfColumns];
        initializeFields();
        setCells();
        ui.updateWholeField(field);
    }

    private void initObservers() {
        generationObservers = new ArrayList<>();
        gameRunningObservers = new ArrayList<>();
    }

    private void initDimensions() {
        numberOfColumns = CellSizeInvestigator.getCellsPerColumn();
        numberOfRows = CellSizeInvestigator.getCellsPerRow();
    }

    private void runGame() {
        running = true;
        notifyGameRunningObservers();
        generation = 1;
        while (running) {
            ui.update(changedCells);
            try {
                Thread.sleep(pauseTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // iterate over game field and count the number of living neighbour cells
            for (int row = 0; row < field.length; row++) {
                for (int column = 0; column < field[row].length; column++) {
                    int numberOfLivingNeighbours = getNumberOfLivingNeighbours(row, column);
                    if (numberOfLivingNeighbours < 2) {
                        newField[row][column].setIsAlive(false);
                    } else {
                        if ((numberOfLivingNeighbours == 2 || numberOfLivingNeighbours == 3) && field[row][column].isAlive()) {
                            newField[row][column].setIsAlive(true);
                        } else {
                            if (numberOfLivingNeighbours == 3 && !field[row][column].isAlive()) {
                                newField[row][column].setIsAlive(true);
                            } else {
                                if (numberOfLivingNeighbours > 3 && field[row][column].isAlive()) {
                                    newField[row][column].setIsAlive(false);
                                }
                            }
                        }
                    }
                    // if the state of the cell has changed, add it to the list of changed cells
                    if(field[row][column].isAlive() != newField[row][column].isAlive()) {
                        try {
                            changedCells.put(newField[row][column]);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            replaceOldFieldWithNewField();
            generation++;
            notifyGenerationObservers();
            // wait thread, if paused
            synchronized (pauseLock) {
                while(paused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {

                    }
                }
            }
            checkOrganismDead();
        }
        ui.update(changedCells);
        System.out.println("Organism dead");
    }

    private void checkOrganismDead() {
        new Thread() {
            @Override
            public void run() {
                if(isOrganismDead()) {
                    running = false;
                    notifyGameRunningObservers();
                }
            }

            private boolean isOrganismDead() {
                boolean isDead = true;
                for(int row = 0; row < field.length; row++) {
                    for(int column = 0; column < field[row].length; column++) {
                        if(field[row][column].isAlive()) {
                            isDead = false;
                            return isDead;
                        }
                    }
                }
                return isDead;
            }
        }.start();

    }

    private void replaceOldFieldWithNewField() {
        for(Cell cell : changedCells) {
            field[cell.getY()][cell.getX()].setIsAlive(cell.isAlive());
        }
    }

    private void setCells(Cell[][] newField) {
        for (int row = 0; row < newField.length; row++) {
            System.arraycopy(newField[row], 0, field[row], 0, newField.length);
        }
    }

    private void setCells() {
        Random rand = new Random();
        int remainingCellsToFill = initialCellsAlive;
        while(remainingCellsToFill > 0) {
            int randomRow = rand.nextInt(numberOfRows);
            int randomColumn = rand.nextInt(numberOfColumns);
            boolean emptyCellFound = false;
            while (!emptyCellFound) {
                if (field[randomRow][randomColumn].isAlive()) {
                    randomColumn++;
                    if(randomColumn >= numberOfColumns) {
                        randomColumn = 0;
                        randomRow++;
                    }
                    if(randomRow >= numberOfRows) {
                        randomColumn = 0;
                        randomRow = 0;
                    }
                } else {
                    emptyCellFound = true;
                    remainingCellsToFill--;
                    field[randomRow][randomColumn].setIsAlive(true);
                }
            }
        }
    }

    private int getNumberOfLivingNeighbours(int row, int column) {
        int numberOfNeighboursAlive = 0;

        numberOfNeighboursAlive += getNeighbour(row - 1, column - 1);
        numberOfNeighboursAlive += getNeighbour(row - 1, column);
        numberOfNeighboursAlive += getNeighbour(row - 1, column + 1);
        numberOfNeighboursAlive += getNeighbour(row, column - 1);
        numberOfNeighboursAlive += getNeighbour(row, column + 1);
        numberOfNeighboursAlive += getNeighbour(row + 1, column - 1);
        numberOfNeighboursAlive += getNeighbour(row + 1, column);
        numberOfNeighboursAlive += getNeighbour(row + 1, column + 1);

        return numberOfNeighboursAlive;
    }

    private int getNeighbour(int rowOfNeighbour, int columnOfNeighbour) {
        int neighbourAlive = 0;
        try {
            if(field[rowOfNeighbour][columnOfNeighbour].isAlive()){
                neighbourAlive = 1;
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        return neighbourAlive;
    }

    private void initializeFields(){
        for(int row = 0; row < field.length; row++) {
            for(int column = 0; column < field[row].length; column++) {
                field[row][column] = new Cell(column, row, false, CellSizeInvestigator.getOptimalCellSize());
                newField[row][column]= new Cell(column, row, false, CellSizeInvestigator.getOptimalCellSize());
            }
        }
    }

    @Override
    public void run() {
        runGame();
    }

    public void pause() {
        synchronized (pauseLock) {
            paused = true;
            notifyGameRunningObservers();
        }
    }

    private void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
            notifyGameRunningObservers();
        }
    }

    public void handlePauseRunClick() {
        if(!running) {
            Thread t = new Thread(this);
            t.start();
        } else if(paused) {
            resume();
        } else {
            pause();
        }
    }

    public void handleRefresh() {
        initializeFields();
        setCells();
        resetGeneration();
        notifyGenerationObservers();
        ui.updateWholeField(field);
        clearChangedCells();
    }

    private void resetGeneration() {
        generation = 0;
    }

    private void clearChangedCells() {
        changedCells.clear();
    }

    public void setPauseTime(int pauseTime) {
        this.pauseTime = pauseTime;
    }

    @Override
    public void registerGeneration(GenerationObserver observer) {
        generationObservers.add(observer);
    }

    @Override
    public void unregisterGeneration(GenerationObserver observer) {
        generationObservers.remove(observer);
    }

    @Override
    public void notifyGenerationObservers() {
        for(GenerationObserver observer : generationObservers) {
            mainActivity.runOnUiThread(() -> observer.updateGeneration(generation));
        }
    }

    public void showPattern(CellPattern pattern) {
        initializeFields();
        setCells(pattern.getField());
        resetGeneration();
        notifyGenerationObservers();
        clearChangedCells();
        ui.updateWholeField(field);
    }

    public static GameOfLife getInstance() {
        return instance;
    }

    // Singleton method
    public static GameOfLife getInstance(UIBoard ui, int initialCellsAlive, Activity mainActivity, int pauseTime) {
        if(instance == null) {
            instance = new GameOfLife(ui, initialCellsAlive, mainActivity, pauseTime);
        }
        return instance;
    }

    @Override
    public void registerGameRunning(GameRunningObserver observer) {
        gameRunningObservers.add(observer);
    }

    @Override
    public void unregisterGameRunning(GameRunningObserver observer) {
        gameRunningObservers.remove(observer);
    }

    @Override
    public void notifyGameRunningObservers() {
        for(GameRunningObserver observer: gameRunningObservers) {
            mainActivity.runOnUiThread(() -> observer.updateGameRunning(running, paused));
        }
    }

    public void reviveCell(int row, int column) {
        Log.d(TAG, "row: " + row);
        Log.d(TAG, "column: " + column);
        Cell revivedCell = field[row][column];
        revivedCell.setIsAlive(true);
        try {
            changedCells.put(revivedCell);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ui.update(changedCells);
    }
}