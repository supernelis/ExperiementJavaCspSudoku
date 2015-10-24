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

    @Test
    public void WhenMakingACell_ThenItIsNotSolved(){
        List<Integer> possibleValues = Arrays.asList(1,2,3,4,5,6);

        //Then
        assertFalse(cell.isSolved());
    }

    @Test
    public void WhenSettingAPossibleValue_ThenNoOtherValueIsPossibleAndCellIsSolved(){
        for (int value : Arrays.asList(1,2,3,4,5,6)) {
            List<Integer> possibleValues = Arrays.asList(value);

            // When
            cell.setValue(value);

            //Then
            assertThat(cell.getPossibleValues(),is(possibleValues));
            assertTrue(cell.isSolved());
        }

    }

    @Test
    public void WhenSettingAValueThatIsNotPossible_ThenNoOtherValueIsPossibleAndCellIsSolved(){
        List<Integer> possibleValues = Arrays.asList(1,2,3,4,5,6);

        cell.setValue(7);

        //Then
        assertThat(cell.getPossibleValues(),is(possibleValues));
        assertFalse(cell.isSolved());
    }

    @Before
    public void setup() {
        cell = new Cell();
    }


}