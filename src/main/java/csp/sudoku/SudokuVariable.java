package csp.sudoku;

import csp.base_classes.Variable;

import java.util.Arrays;
import java.util.HashSet;

public class SudokuVariable extends Variable<Integer> {
    private final int row;
    private final int col;

    public SudokuVariable(String name, int row, int col) throws IllegalArgumentException {
        super(name);
        domain = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        if(row < 0 || row >= 9 || col < 0 || col >= 9)
            throw new IllegalArgumentException();
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
