package search_problems.eight_puzzle;

import search_problems.base_classes.Action;

public class EightPuzzleAction implements Action {
    private final int row;
    private final int col;
    private final double cost;

    public EightPuzzleAction(int row, int col, double cost) {
        this.row = row;
        this.col = col;
        this.cost = cost;
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

    @Override
    public double getCost() {
        return cost;
    }
}
