package adversarial_search.tic_tac_toe;

import adversarial_search.base_classes.Action;

public class TicTacToeAction implements Action {
    private final int row;
    private final int col;

    public TicTacToeAction(int row, int col) {
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
