package search_problems.solvers;

import exceptions.NoSolutionException;
import search_problems.base_classes.Action;
import search_problems.base_classes.Node;
import search_problems.base_classes.State;

import java.util.*;

public class AStar_Solver {
    public static List<Action> solve(State startingState) throws NoSolutionException {
        List<Action> actionsToSolution = new LinkedList<>();
        Node goalNode = findGoal(startingState);
        if(goalNode == null) throw new NoSolutionException();
        while(goalNode != null){
            if(goalNode.getActionToGetHere() != null) actionsToSolution.add(0, goalNode.getActionToGetHere());
            goalNode = goalNode.getFather();
        }

        return actionsToSolution;
    }

    private static Node findGoal(State startingState) {
        Node startingNode = new Node(startingState);
        PriorityQueue<Node> frontier = new PriorityQueue<>(); frontier.add(startingNode);
        Map<State, Node> reached = new HashMap<>();

        while(!frontier.isEmpty()){
            Node curr = frontier.poll();
            if(curr.getState().isGoal()) return curr;
            curr.generateChildren();
            for(Node child : curr.getChildren()){
                State s = child.getState();
                if(!reached.keySet().contains(s) || child.getEval() < reached.get(s).getEval()){
                    reached.put(s, child);
                    frontier.add(child);
                }
            }
        }

        return null;
    }
}
