package search_problems.base_classes;

import java.util.List;

public interface State {
    public boolean isGoal();
    public List<Action> getActions();
    public State applyAction(Action action);
    public State clone();
    public double eval();
}
