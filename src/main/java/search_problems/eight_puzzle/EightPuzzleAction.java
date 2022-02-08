package search_problems.eight_puzzle;

import search_problems.base_classes.Action;

public class EightPuzzleAction implements Action {
    private final int row;
    private final int col;

    public EightPuzzleAction(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
