package com.archiwise.experiment.cspsudoku.domein;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by nelis on 24/10/15.
 */
public class CellTest {

    private Cell cell;

    @Test
    public void WhenMakingACell_ThenAllValuesArePossible(){
        List<Integer> possibleValues = Arrays.asList(1,2,3,4,5,6);

        //Then
        assertThat(cell.getPossibleValues(),is(possibleValues));
        assertThat(cell.getNumberOfPossibleValues(),is(6));
    }

    @Before
    private void setup() {
        cell = new Cell();
    }


}