package com.company;

import exceptions.NoSolutionException;
import search_problems.base_classes.Action;
import search_problems.eight_puzzle.EightPuzzleState;
import search_problems.solvers.AStar_Solver;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        /* Eight Puzzle Test */
        EightPuzzleState startingState = new EightPuzzleState();
        startingState = startingState.shuffle(1000);

        System.out.println("Starting state:");
        System.out.println(startingState);
        try {
            List<Action> actionsToSolution = AStar_Solver.solve(startingState); // BFS_Solver.solve(startingState);

            System.out.println("Solution:");
            System.out.println(actionsToSolution);
        } catch (NoSolutionException e) {
            System.out.println("No solution was found for this problem.");
        }
        /**/

        /* Sudoku Test
        String sudokuTestPath = ""; // path to sudoku input file (example in the test folder)

        SudokuCSP sudokuCSP = new SudokuCSP(sudokuTestPath);
        BacktrackingSolver<Integer> bktrkSolver = new BacktrackingSolver<>();
        System.out.println(bktrkSolver.solve(sudokuCSP));
        */
    }
}
