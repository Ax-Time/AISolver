package search_problems.eight_puzzle;

import search_problems.base_classes.Action;
import search_problems.base_classes.State;

import java.util.*;

public class EightPuzzleState implements State {
    private int[][] board;
    private int zeroRow, zeroCol;

    public EightPuzzleState() {
        board = new int[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                board[i][j] = 3*i + j;
            }
        }
        zeroRow = 0;
        zeroCol = 0;
    }

    public EightPuzzleState shuffle(int n_permutations) {
        State startingState = new EightPuzzleState();
        Random rand = new Random();
        while(n_permutations > 0){
            List<Action> actions = startingState.getActions();
            startingState = startingState.applyAction(actions.get(rand.nextInt(0, actions.size())));
            n_permutations--;
        }

        return (EightPuzzleState) startingState;
    }

    private void swap(int i_src, int j_src, int i_dst, int j_dst) {
        int temp = board[i_src][j_src];
        board[i_src][j_src] = board[i_dst][j_dst];
        board[i_dst][j_dst] = temp;

        if(i_src == zeroRow && j_src == zeroCol) {
            zeroRow = i_dst;
            zeroCol = j_dst;
        }
        else if(i_dst == zeroRow && j_dst == zeroCol) {
            zeroRow = i_src;
            zeroCol = j_src;
        }
    }

    @Override
    public boolean isGoal() {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[i][j] != 3*i + j) return false;
            }
        }
        return true;
    }

    @Override
    public List<Action> getActions() {
        List<Action> actions = new ArrayList<>(4);

        // Create actions
        // Up
        if(zeroRow >= 1) actions.add(new EightPuzzleAction(zeroRow - 1, zeroCol));
        // Down
        if(zeroRow <= 1) actions.add(new EightPuzzleAction(zeroRow + 1, zeroCol));
        // Left
        if(zeroCol >= 1) actions.add(new EightPuzzleAction(zeroRow, zeroCol - 1));
        // Right
        if(zeroCol <= 1) actions.add(new EightPuzzleAction(zeroRow, zeroCol + 1));

        return actions;
    }

    @Override
    public State applyAction(Action action) {
        EightPuzzleState nextState = (EightPuzzleState) this.clone();
        EightPuzzleAction actionAfterCast = (EightPuzzleAction) action;
        nextState.swap(zeroRow, zeroCol, actionAfterCast.getRow(), actionAfterCast.getCol());
        return nextState;
    }

    @Override
    public State clone() {
        EightPuzzleState cloneState = new EightPuzzleState();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                cloneState.board[i][j] = this.board[i][j];
            }
        }
        cloneState.zeroRow = zeroRow;
        cloneState.zeroCol = zeroCol;
        return cloneState;
    }

    @Override
    public String toString() {
        String s = "";
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                s += (board[i][j] == 0 ? " " : board[i][j]) + "  ";
            }
            s += '\n';
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EightPuzzleState that = (EightPuzzleState) o;
        return Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}
