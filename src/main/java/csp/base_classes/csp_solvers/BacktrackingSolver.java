package csp.base_classes.csp_solvers;

import csp.base_classes.Assignment;
import csp.base_classes.CSP;
import csp.base_classes.Constraint;
import csp.base_classes.Variable;

import java.util.*;
import java.util.stream.Collectors;

public class BacktrackingSolver<VarChild extends Variable<T>, T> {
    public Assignment<VarChild, T> solve(CSP<VarChild, T> csp){
        Set<VarChild> variables = new HashSet<>();
        csp.getVariables().forEach(var -> variables.add((VarChild) var.clone()));

        Assignment<VarChild, T> startingAssignment = new Assignment<>();
        for(VarChild var : variables){
            if(var.getCurrentDomain().size() == 1){
                startingAssignment.assign(var, var.getCurrentDomain().stream().toList().get(0));
            }
        }

        return backtrack(variables, csp.getConstraints(), startingAssignment);
    }

    private Assignment<VarChild, T> backtrack(Set<VarChild> variables,
                                              List<Constraint<VarChild, T>> constraints,
                                              Assignment<VarChild, T> assignment)
    {
        // Display progress info
        System.out.println("Progress: " + assignment.getAssignedVariables().size() / (float)variables.size() * 100.0 + "%");
        if(assignment.getAssignedVariables().equals(variables)) return assignment; // Assignment is complete
        VarChild var = selectUnassignedVariable(variables, constraints, assignment);
        Set<T> oldVarDomain = var.getCurrentDomain();

        for(T value : orderByLeastConstrainingValue(var, constraints)){
            assignment.assign(var, value);
            if(isAssignmentConsistent(assignment, constraints)) {
                var.setDomain(new HashSet<>(List.of(value)));

                Set<VarChild> oldVariables = new HashSet<>();
                variables.forEach(v -> oldVariables.add((VarChild) v.clone()));
                if (AC3(variables, constraints)) {
                    Assignment<VarChild, T> result = backtrack(variables, constraints, assignment);
                    if (result != null) return result;
                }
                variables = oldVariables;
                var.setDomain(oldVarDomain);
            }
            assignment.unAssign(var);
        }

        return null;
    }

    private boolean isAssignmentConsistent(Assignment<VarChild, T> assignment, List<Constraint<VarChild, T>> constraints){
        for(Constraint<VarChild, T> constr : constraints){
            if(!constr.test(assignment)) return false;
        }
        return true;
    }

    // TODO: implement the least-constraining-value heuristic
    private List<T> orderByLeastConstrainingValue(VarChild variable, List<Constraint<VarChild, T>> constraints){
        return variable.getCurrentDomain().stream().toList();
    }

    private VarChild selectUnassignedVariable(Set<VarChild> variables,
                                              List<Constraint<VarChild, T>> constraints,
                                              Assignment<VarChild, T> assignment)
    {
        Set<VarChild> unassignedVariables = variables.stream()
                .filter(var -> !assignment.getAssignedVariables().contains(var))
                .collect(Collectors.toSet());

        // Apply Minimum-Remaining-Values heuristic (pick the variable with the smallest domain)
        OptionalInt minDomainSize = unassignedVariables.stream()
                .mapToInt(var -> var.getCurrentDomain().size())
                .min();

        unassignedVariables = unassignedVariables.stream()
                .filter(var -> var.getCurrentDomain().size() == minDomainSize.getAsInt())
                .collect(Collectors.toSet());

        if(unassignedVariables.size() > 1){
            // Apply Degree heuristic to break ties (pick the variable involved in the largest number of constraints)
            OptionalInt largestNumberOfConstraints = unassignedVariables.stream()
                    .mapToInt(var -> {
                        return constraints.stream()
                                .filter(constr -> constr.getVar1().equals(var) || constr.getVar2().equals(var))
                                .collect(Collectors.toList())
                                .size();
                    })
                    .max();

            return unassignedVariables.stream()
                    .filter(var -> {
                        return largestNumberOfConstraints.getAsInt() == constraints.stream()
                                .filter(constr -> constr.getVar1().equals(var) || constr.getVar2().equals(var))
                                .collect(Collectors.toList())
                                .size();
                    })
                    .collect(Collectors.toList())
                    .get(0);
        }

        return new ArrayList<>(unassignedVariables).get(0);
    }

    /**
     * Applies the AC-3 algorithm to the csp, reducing the domain of the variables to remove inconsistencies and to speed up
     * the solving process.
     *
     * @param variables variables of the csp
     * @param constraints constraints of the csp
     * @return false if the csp is inconsistent, true otherwise
     */
    protected boolean AC3(Set<VarChild> variables, List<Constraint<VarChild, T>> constraints){
        Queue<Constraint<VarChild, T>> constraintQueue = new LinkedList<>();
        // Every constraint becomes two arcs, one for each direction
        constraints.forEach(constr -> {
                    constraintQueue.add(new Constraint<>(constr.getVar1(), constr.getVar2(), constr.getConstraintTester()));
                    constraintQueue.add(new Constraint<>(constr.getVar2(), constr.getVar1(), constr.getConstraintTester()));
                }
        );

        while(!constraintQueue.isEmpty()){
            Constraint<VarChild, T> currentConstraint = constraintQueue.poll(); // Constraint on (X, Y)
            if(AC3_Revise(currentConstraint)){
                if(currentConstraint.getVar1().getCurrentDomain().size() == 0) return false; // If X's domain is empty, return false
                // Add all the constraints of type (Z, X) where Z != Y to the queue
                constraints.stream()
                        .filter(constr -> constr.getVar2().equals(currentConstraint.getVar1()) &&
                                          !constr.getVar1().equals(currentConstraint.getVar2()))
                        .forEach(constraintQueue::add);
            }
        }
        return true;
    }

    private boolean AC3_Revise(Constraint<VarChild, T> constraint /* constraint on (X, Y) */){
        boolean revised = false;
        for(T xValue : constraint.getVar1().getCurrentDomain()){
            // For each value in X's domain
            // Check if it exists a value in the domain of Y that satisfies the constraint on (X, Y) with { X = xValue }
            // If such value doesn't exist, remove xValue from the domain of X and mark revised as true
            boolean satisfied = false;
            Assignment<VarChild, T> assignment = new Assignment<>();
            assignment.assign(constraint.getVar1(), xValue);

            for(T yValue : constraint.getVar2().getCurrentDomain()){
                assignment.assign(constraint.getVar2(), yValue);
                if(constraint.test(assignment)) satisfied = true;
                assignment.unAssign(constraint.getVar2());
            }

            if(!satisfied){
                constraint.getVar1().removeFromDomain(xValue);
                revised = true;
            }
        }
        return revised;
    }
}
