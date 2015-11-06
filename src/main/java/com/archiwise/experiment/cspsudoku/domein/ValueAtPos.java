package com.archiwise.experiment.cspsudoku.domein;

import java.io.Serializable;

/**
 * Created by nelis on 02/11/15.
 */
public class ValueAtPos implements Serializable {



    private int value;
    private int row;
    private int col;

    public ValueAtPos(final int value, final int row, final int col) {

        this.value = value;
        this.row = row;
        this.col = col;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ValueAtPos that = (ValueAtPos) o;

        if (value != that.value) {
            return false;
        }
        if (row != that.row) {
            return false;
        }
        return col == that.col;

    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + row;
        result = 31 * result + col;
        return result;
    }

    public int getValue() {
        return value;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "ValueAtPos{" +
               "value=" + value +
               ", row=" + row +
               ", col=" + col +
               '}';
    }
}
