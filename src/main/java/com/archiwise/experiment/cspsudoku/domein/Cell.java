package com.archiwise.experiment.cspsudoku.domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by nelis on 24/10/15.
 */
public class Cell {

    public static List<Integer> POSSIBLE_VALUES_INIT = Arrays.asList(1,2,3,4,5,6);

    private List<Integer> possibleValues = new ArrayList<Integer>(POSSIBLE_VALUES_INIT);


    public List<Integer> getPossibleValues() {
        return possibleValues;
    }

    public int getNumberOfPossibleValues() {
        return possibleValues.size();
    }

    public boolean isSolved() {
        return (getNumberOfPossibleValues() == 1);
    }

    public void removePossibleValue(final int value) {
        possibleValues.remove(Integer.valueOf(value));
    }

    void setValue(final int value) {
        if(isPossibleValue(value))
            possibleValues = new ArrayList<>(Arrays.asList(value));
        else{
            possibleValues = Collections.emptyList();
        }
    }

    public boolean isImpossible() {
        return (getNumberOfPossibleValues() == 0);
    }

    public boolean isPossibleValue(final int value) {
        return possibleValues.contains(value);
    }
}
