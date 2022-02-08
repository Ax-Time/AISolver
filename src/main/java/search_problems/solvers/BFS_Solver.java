package search_problems.solvers;

import exceptions.NoSolutionException;
import search_problems.base_classes.Action;
import search_problems.base_classes.State;

import java.util.*;

public class BFS_Solver {
    /**
     * @return list of actions to get from starting state to goal
     */
    public List<Action> solve(State startingState) throws NoSolutionException {
        List<Action> actionsToSolution = new LinkedList<>();
        Node goalNode = findGoal(startingState);
        while(goalNode != null){
            if(goalNode.getActionToGetHere() != null) actionsToSolution.add(0, goalNode.getActionToGetHere());
            goalNode = goalNode.getFather();
        }

        return actionsToSolution;
    }

    private Node findGoal(State startingState){
        Node startingNode = new Node(startingState);
        if(startingNode.getState().isGoal()) return startingNode;
        Queue<Node> frontier = new LinkedList<>();
            frontier.add(startingNode);
        Set<Node> reached = new HashSet<>();
            reached.add(startingNode);

        while(!frontier.isEmpty()){
            Node curr = frontier.poll();
            curr.generateChildren();
            for(Node child : curr.getChildren()){
                if(child.getState().isGoal()) return child;
                if(!reached.contains(child)){
                    reached.add(child);
                    frontier.add(child);
                }
            }
        }

        return null;
    }
}
