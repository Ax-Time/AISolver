package csp.solvers;

import csp.base_classes.Assignment;
import csp.sudoku.SudokuCSP;
import exceptions.NoSolutionException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static csp.sudoku.SudokuCSP.printSudoku;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class BacktrackingSolverTest {

    @Test
    void solve() {
        /* Sudoku Test */
        String sudokuTestPath = "src/test/sudokuTestInput.txt"; // path to sudoku input file (example in the test folder)

        SudokuCSP sudokuCSP = null;
        try {
            sudokuCSP = new SudokuCSP(sudokuTestPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BacktrackingSolver<Integer> bktrkSolver = new BacktrackingSolver<>();
        try {
            Assignment<Integer> solution = bktrkSolver.solve(sudokuCSP);
            printSudoku(solution);

            // Check solution
            assert sudokuCSP != null;
            if(sudokuCSP.getConstraints().stream().allMatch(constr -> constr.test(solution))) {
                System.out.println("Solution is correct");
                assertTrue(true);
            }
            else {
                System.out.println("Solution is wrong");
                fail();
            }
        } catch (NoSolutionException e) {
            e.printStackTrace();
        }
    }
}