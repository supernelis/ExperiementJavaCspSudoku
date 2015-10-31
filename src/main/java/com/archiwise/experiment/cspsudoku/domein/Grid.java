package com.archiwise.experiment.cspsudoku.domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by nelis on 27/10/15.
 */
public class Grid {


    public static final int NUMBER_OF_ROWS = 3;
    public static final int NUMBER_OF_COLUMNS = 2;

    private Cell[][] cells = new Cell[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

    public Grid(){
        fillCellArray();

    }

    public Cell getCellAtPossition(final int row, final int column) {
        return cells[row-1][column-1];
    }

    public void setValueAtPosition(final int value, final int row, final int column){
        for (int r = 0; r < NUMBER_OF_ROWS; r++) {
            for (int col = 0; col < NUMBER_OF_COLUMNS; col++) {

                if(r == row -1 && col == column -1) cells[r][col].setValue(value);
                else
                    cells[r][col].removePossibleValue(value);

            }
        }
    }

    public void valueSetOnNeigtborRow(final int value, final int row) {
        for(int col=0; col < NUMBER_OF_COLUMNS; col++){
            cells[row-1][col].removePossibleValue(value);
        }
    }

    public void valueSetOnNeigtborCol(final int value, final int col) {
        for(int row=0; row < NUMBER_OF_ROWS; row++){
            cells[row][col-1].removePossibleValue(value);
        }
    }

    private void fillCellArray() {
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
