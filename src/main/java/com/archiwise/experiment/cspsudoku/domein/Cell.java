package com.archiwise.experiment.cspsudoku.domein;

import java.util.Arrays;
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
    
}
