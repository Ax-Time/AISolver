package adversarial_search.tic_tac_toe;

import adversarial_search.base_classes.Action;
import adversarial_search.base_classes.Player;
import adversarial_search.base_classes.State;

import java.util.LinkedList;
import java.util.List;

public class TicTacToeState implements State {
    private int[][] board;
    private Player playerToMove;

    public TicTacToeState() {
        board = new int[3][3];
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                board[i][j] = 0;

        playerToMove = Player.MAX;
    }

    @Override
    public Player toMove() {
        return playerToMove;
    }

    @Override
    public List<Action> actions() {
        List<Action> actions = new LinkedList<>();
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                if(board[i][j] == 0) actions.add(new TicTacToeAction(i, j));

        return actions;
    }

    @Override
    public State applyAction(Action action) {
        TicTacToeState newState = (TicTacToeState) clone();
        newState.board[((TicTacToeAction)action).getRow()][((TicTacToeAction)action).getCol()] = playerToMove == Player.MAX ? 1 : -1;
        newState.playerToMove = playerToMove == Player.MAX ? Player.MIN : Player.MAX;
        return newState;
    }

    @Override
    public boolean isTerminal() {
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                if(board[i][j] == 0) return false;

        return true;
    }

    @Override
    public double getUtility() {
        if(isTerminal()){
            // Check rows
            for(int i = 0; i < 3; i++){
                if(board[i][0] == board[i][1] && board[i][0] == board[i][2]){
                    return board[i][0] > 0 ? 1 : -1;
                }
            }
            // Check columns
            for(int j = 0; j < 3; j++){
                if(board[0][j] == board[1][j] && board[0][j] == board[2][j]){
                    return board[0][j] > 0 ? 1 : -1;
                }
            }

            // Check diagonals
            if(board[0][0] == board[1][1] && board[0][0] == board[2][2])
                return board[0][0] > 0 ? 1 : -1;

            if(board[0][2] == board[1][1] && board[0][2] == board[2][0])
                return board[0][2] > 0 ? 1 : -1;
        }
        return 0;
    }

    @Override
    public State clone() {
        TicTacToeState s = new TicTacToeState();
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                s.board[i][j] = board[i][j];
        s.playerToMove = playerToMove;
        return s;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                s.append(board[i][j] == 0 ? "_  " : board[i][j] > 0 ? "x  " : "o  ");
            }
            s.append('\n');
        }
        return s.toString();
    }
}
