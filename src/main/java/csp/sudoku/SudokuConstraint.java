package csp.sudoku;

import csp.base_classes.Constraint;

import java.util.Objects;

public class SudokuConstraint extends Constraint<SudokuVariable, Integer> {
    public SudokuConstraint(SudokuVariable var1, SudokuVariable var2) {
        super(var1, var2, (a, b) -> !Objects.equals(a, b));
    }
}
