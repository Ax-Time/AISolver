package com.company;

import csp.base_classes.csp_solvers.BacktrackingSolver;
import csp.sudoku.SudokuCSP;
import csp.sudoku.SudokuVariable;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // C:\Users\giaco\Desktop\SudokuTestInput.txt

        SudokuCSP sudokuCSP = new SudokuCSP("C:\\Users\\giaco\\Desktop\\SudokuTestInput.txt");
        BacktrackingSolver<SudokuVariable, Integer> solver = new BacktrackingSolver();
        System.out.println(solver.solve(sudokuCSP));
    }
}
