package search_problems.solvers;

import search_problems.base_classes.Action;
import search_problems.base_classes.State;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Node {
    private final State state;
    private Action actionToGetHere;
    private Node father;
    private List<Node> children;

    public Node(State state, Action actionToGetHere, Node father) {
        this(state, actionToGetHere);
        this.father = father;
    }

    public Node(State state, Action actionToGetHere) {
        this(state);
        this.actionToGetHere = actionToGetHere;
    }

    public Node(State state){
        this.state = state;
        this.actionToGetHere = null;
        this.father = null;
        this.children = new LinkedList<>();
    }

    public void addChild(State state, Action action){
        children.add(new Node(state, action));
    }

    public State getState() {
        return state;
    }

    public Action getActionToGetHere() {
        return actionToGetHere;
    }

    public void generateChildren() {
        for(Action action : state.getActions()){
            children.add(new Node(state.applyAction(action), action, this));
        }
    }

    public List<Node> getChildren() {
        return children;
    }

    public Node getFather() {
        return father;
    }

    @Override
    public boolean equals(Object o) {
        Node node = (Node) o;
        return Objects.equals(state, node.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }
}
