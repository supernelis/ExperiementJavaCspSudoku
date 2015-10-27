package com.archiwise.experiment.cspsudoku.domein;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by nelis on 24/10/15.
 */
public class Cell {

    private List<Integer> possibleValues = Arrays.asList(1,2,3,4,5,6);

    public List<Integer> getPossibleValues() {
        return possibleValues;
    }

    public int getNumberOfPossibleValues() {
        return possibleValues.size();
    }

    public boolean isSolved() {
        return (getNumberOfPossibleValues() == 1);
    }

    public void setValue(final int value) {
        if(possibleValues.contains(value))
            possibleValues = Arrays.asList(value);
        else{
            possibleValues = Collections.emptyList();
        }
    }

    public boolean isImpossible() {
        return (getNumberOfPossibleValues() == 0);
    }
}
