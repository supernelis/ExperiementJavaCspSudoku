package com.archiwise.experiment.cspsudoku.domein;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by nelis on 24/10/15.
 */
public class CellTest {

    private Cell cell;

    @Test
    public void whenMakingACell_ThenAllValuesArePossible() {
        List<Integer> possibleValues = Arrays.asList(1, 2, 3, 4, 5, 6);

        //Then
        assertThat(cell.getPossibleValues(), is(possibleValues));
        assertThat(cell.getNumberOfPossibleValues(), is(6));
    }

    @Test
    public void whenMakingACell_ThenItIsNotSolved() {
        //Then
        assertFalse(cell.isSolved());
    }

    @Test
    public void whenSettingAValue_ThenNoOtherValueIsPossibleAndCellIsSolved() {

        //Given

        final int value = 6;
        List<Integer> possibleValues = Arrays.asList(value);

        // When
        cell.setValue(value);

        //Then
        assertThat(cell.getPossibleValues(), is(possibleValues));
        assertTrue(cell.isPossibleValue(6));
        assertFalse(cell.isPossibleValue(7));
        assertEquals(1,cell.getNumberOfPossibleValues());
        assertTrue(cell.isSolved());
    }



    @Test
    public void whenSettingAValueThatIsNotPossible_ThenNoValueIsPossibleAnymore() {
        List<Integer> possibleValues = Collections.emptyList();

        cell.setValue(7);

        //Then
        assertThat(cell.getPossibleValues(), is(possibleValues));
        assertFalse(cell.isSolved());
        assertTrue(cell.isImpossible());
    }



    @Before
    public void setup() {
        cell = new Cell();
    }


}