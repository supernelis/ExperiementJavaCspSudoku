package com.archiwise.experiment.cspsudoku.domein;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by nelis on 27/10/15.
 */
public class GridTest {

    @Test
    public void whenGridGreated_TheGridIsNotSolved(){
        Grid grid = new Grid();

        assertFalse(grid.isSolved());

    }
}