package com.archiwise.experiment.cspsudoku.domein;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by nelis on 27/10/15.
 */
public class GridTest {

    private Grid grid;

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
    public void whenValueIsSetInNeighborRow_ThenValueIsNotPossibleAnyMore(){
        int value = 1;
        int row = 1;
        grid.valueSetOnNeigtborRow(value, row);

        assertFalse(grid.getCellAtPossition(row, 1).isPossibleValue(value));
        assertFalse(grid.getCellAtPossition(row, 2).isPossibleValue(value));
        assertTrue(grid.getCellAtPossition(2,1).isPossibleValue(value));
        assertTrue(grid.getCellAtPossition(3,2).isPossibleValue(value));
    }

    
}