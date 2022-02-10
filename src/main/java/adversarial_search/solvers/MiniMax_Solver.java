package adversarial_search.solvers;

import adversarial_search.base_classes.Action;
import adversarial_search.base_classes.Player;
import adversarial_search.base_classes.State;

public class MiniMax_Solver {
    public static Action getBestAction(State state){
        Player player = state.toMove();
        UtilityMovePair utilityMovePair = (player == Player.MAX) ? maxValue(state.clone()) : minValue(state.clone());
        return utilityMovePair.getMove();
    }

    private static UtilityMovePair maxValue(State state){
        if(state.isTerminal()) return new UtilityMovePair(state.getUtility(), null);
        double value = -Double.MAX_VALUE;
        Action move = null;
        for(Action a : state.actions()){
            UtilityMovePair curr = minValue(state.applyAction(a));
            if(curr.getUtility() > value){
                value = curr.getUtility();
                move = a;
            }
        }

        return new UtilityMovePair(value, move);
    }

    private static UtilityMovePair minValue(State state){
        if(state.isTerminal()) return new UtilityMovePair(state.getUtility(), null);
        double value = Double.MAX_VALUE;
        Action move = null;
        for(Action a : state.actions()){
            UtilityMovePair curr = maxValue(state.applyAction(a));
            if(curr.getUtility() < value){
                value = curr.getUtility();
                move = a;
            }
        }

        return new UtilityMovePair(value, move);
    }
}

class UtilityMovePair{
    final double utility;
    final Action move;

    public UtilityMovePair(double utility, Action move) {
        this.utility = utility;
        this.move = move;
    }

    public double getUtility() {
        return utility;
    }

    public Action getMove() {
        return move;
    }
}
