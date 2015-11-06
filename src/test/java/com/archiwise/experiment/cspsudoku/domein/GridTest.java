package com.archiwise.experiment.cspsudoku.domein;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by nelis on 27/10/15.
 */
public class GridTest {

    private Grid grid;

    private ValueListener listener = mock(ValueListener.class);

    @Before
    public void setUp() throws Exception {
        grid = new Grid();
    }

    @Test
    public void whenGridGreated_TheGridIsNotSolved(){

        assertFalse(grid.isSolved());

    }

    @Test
    public void whenGridGreated_TheAllCellsHaveAllPossibleValues(){
        for (Cell c : grid.getCells()) {
            assertThat(c.getPossibleValues(),is(Cell.POSSIBLE_VALUES_INIT));
        }
    }

    @Test
    public void whenGridGreated_TheGridIsNotImpossible(){

        assertFalse(grid.isImpossible());

    }

    @Test
    public void whenOneCellIsImpossible_TheGridIsImpossibleAndUnsolved(){
        grid.setValueAtPosition(7,1,1);

        // then
        assertTrue(grid.isImpossible());
        assertFalse(grid.isSolved());
    }

    @Test
    public void whenSettingAValue_ThisValueIsNotPossibleForOtherCellsAnymore(){
        grid.setValueAtPosition(5,1,1);

        final List<Cell> cells = grid.getCells();
        cells.remove(grid.getCellAtPossition(1,1));

        assertTrue(cells.stream().allMatch(c -> !c.isPossibleValue(5)));

    }

    @Test
    public void whenSettingAValue_ThisValueIsNotified(){
       //Given
        grid.addObserver(listener);

        // When
        grid.setValueAtPosition(5,1,1);

        // Then
        verify(listener,times(1)).notifyValue(new ValueAtPos(5,1,1));

    }

    @Test
    public void whenSettingTwoTimesTheSameValue_TheGridIsImpossible(){
        grid.setValueAtPosition(5,1,1);
        grid.setValueAtPosition(5,2,1);

        assertTrue(grid.isImpossible());

    }

    @Test
    public void whenFillingIn5Values_GridIsSolved(){
        grid.setValueAtPosition(1, 1,1);
        grid.setValueAtPosition(2, 2,1);
        grid.setValueAtPosition(3, 3,1);
        grid.setValueAtPosition(4, 1,2);
        grid.setValueAtPosition(5, 2,2);

        assertTrue(grid.isSolved());
        assertFalse(grid.isImpossible());
    }

    @Test
    public void whenFillingIn5Values_SixValuesAreNotified(){
        //Given
        grid.addObserver(listener);

        grid.setValueAtPosition(1, 1,1);
        grid.setValueAtPosition(2, 2,1);
        grid.setValueAtPosition(3, 3,1);
        grid.setValueAtPosition(4, 1,2);
        grid.setValueAtPosition(5, 2,2);

        verify(listener,times(6)).notifyValue(any());
    }

    @Test
    public void whenValueIsSetInNeighborRow_ThenValueIsNotPossibleAnyMoreInRow(){
        int value = 1;
        int row = 1;
        grid.valueSetOnNeigtborRow(value, row);

        assertFalse(grid.getCellAtPossition(row, 1).isPossibleValue(value));
        assertFalse(grid.getCellAtPossition(row, 2).isPossibleValue(value));
        assertTrue(grid.getCellAtPossition(2,1).isPossibleValue(value));
        assertTrue(grid.getCellAtPossition(3,2).isPossibleValue(value));
    }

    @Test
    public void whenValueIsSetInNeighborCol_ThenValueIsNotPossibleAnyMoreInCol(){
        int value = 1;
        int col = 1;
        grid.valueSetOnNeigtborCol(value, col);

        assertFalse(grid.getCellAtPossition(1,col).isPossibleValue(value));
        assertFalse(grid.getCellAtPossition(2,col).isPossibleValue(value));
        assertFalse(grid.getCellAtPossition(3,col).isPossibleValue(value));
        assertTrue(grid.getCellAtPossition(1,2).isPossibleValue(value));

    }


}