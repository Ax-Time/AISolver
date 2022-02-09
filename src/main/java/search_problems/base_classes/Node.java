package search_problems.base_classes;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Node implements Comparable<Node>{
    private final State state;
    private Action actionToGetHere;
    private Node father;
    private List<Node> children;
    private double eval;

    public Node(State state, Action actionToGetHere, Node father) {
        this(state);
        this.actionToGetHere = actionToGetHere;
        this.father = father;
        this.eval = father.eval + actionToGetHere.getCost();
    }

    public Node(State state){
        this.state = state;
        this.actionToGetHere = null;
        this.father = null;
        this.children = new LinkedList<>();
        this.eval = state.eval();
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

    public double getEval() {
        return eval;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(state, node.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }

    @Override
    public int compareTo(Node o) {
        // Ascending order of eval
        return Double.compare(this.eval, o.eval);
    }
}
