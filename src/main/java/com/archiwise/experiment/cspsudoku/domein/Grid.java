package com.archiwise.experiment.cspsudoku.domein;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelis on 27/10/15.
 */
public class Grid {


    public static final int NUMBER_OF_ROWS = 3;
    public static final int NUMBER_OF_COLUMNS = 2;

    private Cell[][] cells = new Cell[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
    private List<ValueListener> listeners = new ArrayList<>();

    public Grid() {
        initializeCellArray();

    }

    public Cell getCellAtPossition(final int row, final int column) {
        return cells[row - 1][column - 1];
    }

    public void setValueAtPosition(final ValueAtPos valueAtPos) {
        for (int row = 0; row < NUMBER_OF_ROWS; row++) {
            for (int col = 0; col < NUMBER_OF_COLUMNS; col++) {

                if (row == valueAtPos.getRow() - 1 && col == valueAtPos.getCol() - 1) {
                    setValueOnPos(valueAtPos);
                } else {
                    removePossibleValueFromPos(valueAtPos.getValue(), row, col);
                }
            }
        }
    }

    private void removePossibleValueFromPos(final int value, final int row, final int col) {
        Cell cell = cells[row][col];
        if (!cell.isSolved()) {
            cell.removePossibleValue(value);
            if (cell.isSolved()) {
                notifyValue(new ValueAtPos(cell.getValue(), row+1, col+1));
            }
        }
    }

    public void setValueAtPosition(final int value, final int row, final int col) {
        setValueAtPosition(new ValueAtPos(value, row, col));
    }

    public void addObserver(final ValueListener listener) {
        listeners.add(listener);
    }

    private void setValueOnPos(final ValueAtPos valueAtPos) {
        Cell cell = cells[valueAtPos.getRow() - 1][valueAtPos.getCol() - 1];
        cell.setValue(valueAtPos.getValue());
        if (cell.isSolved()) {
            notifyValue(valueAtPos);
        }
    }

    private void notifyValue(final ValueAtPos valueAtPos) {
        for (ValueListener listener : listeners) {
            listener.notifyValue(valueAtPos);
        }
    }

    public void valueSetOnNeigtborRow(final int value, final int row) {
        for (int col = 0; col < NUMBER_OF_COLUMNS; col++) {
            removePossibleValueFromPos(value, row - 1, col);

        }
    }

    public void valueSetOnNeigtborCol(final int value, final int col) {
        for (int row = 0; row < NUMBER_OF_ROWS; row++) {
            removePossibleValueFromPos(value, row, col - 1);

        }
    }

    private void initializeCellArray() {
        for (int row = 0; row < NUMBER_OF_ROWS; row++) {
            for (int col = 0; col < NUMBER_OF_COLUMNS; col++) {
                cells[row][col] = new Cell();
            }
        }
    }

    public boolean isSolved() {
        return getCells().stream().allMatch(c -> c.isSolved());
    }

    public List<Cell> getCells() {
        List<Cell> listOfCells = new ArrayList<Cell>();
        for (int row = 0; row < NUMBER_OF_ROWS; row++) {
            for (int col = 0; col < NUMBER_OF_COLUMNS; col++) {
                listOfCells.add(cells[row][col]);
            }
        }
        return listOfCells;
    }

    public boolean isImpossible() {
        return getCells().stream().anyMatch(c -> c.isImpossible());
    }
}
