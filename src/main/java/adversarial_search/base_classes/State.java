package adversarial_search.base_classes;

import java.util.List;

public interface State {
    Player toMove();
    List<Action> actions();
    State applyAction(Action action);
    boolean isTerminal();
    double getUtility();
    State clone();
}
