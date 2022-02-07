package com.company;

import csp.base_classes.csp_solvers.BacktrackingSolver;
import csp.sudoku.SudokuCSP;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        /* Sudoku Test */
        String sudokuTestPath = "C:\\Users\\giaco\\Desktop\\CSprojects\\JAVA projects\\FAI\\src\\test\\sudokuTestInput.txt";

        SudokuCSP sudokuCSP = new SudokuCSP(sudokuTestPath);
        BacktrackingSolver<Integer> solver = new BacktrackingSolver<>();
        System.out.println(solver.solve(sudokuCSP));
        /* */
    }
}
