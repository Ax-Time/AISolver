package csp.solvers;


import csp.base_classes.Assignment;
import csp.base_classes.CSP;
import csp.base_classes.Constraint;
import csp.base_classes.Variable;
import exceptions.NoSolutionException;

import java.util.*;
import java.util.stream.Collectors;

public class BacktrackingSolver<T> {
    public Assignment<T> solve(CSP<T> csp) throws NoSolutionException {
        CSP<T> cspClone = new CSP<>(csp);
        Assignment<T> startingAssignment = new Assignment<>();
        Assignment<T> result = backtrack(cspClone, startingAssignment);
        if(result == null) throw new NoSolutionException();
        return result;
    }


    private Assignment<T> backtrack(CSP<T> csp, Assignment<T> assignment) {
        if(assignment.getAssignedVariables().equals(csp.getVariables())) return assignment;
        Variable<T> chosen = selectUnassignedVariable(csp, assignment);
        if(!AC3(csp)) return null;
        for(T value : orderDomainValues(csp, chosen, assignment)){
            Assignment<T> newAssignment = new Assignment<>(assignment);
            newAssignment.assign(chosen, value);
            // When you assign a variable, reduce its domain to only the value assigned in the clone csp
            CSP<T> cspClone = new CSP<>(csp);
                cspClone.getVariables().stream().filter(v -> v.equals(chosen)).findFirst().get().setDomain(new HashSet<>(List.of(value)));
            Assignment<T> result = backtrack(cspClone, newAssignment);
            if (result != null) return result;
        }

        return null;
    }

    private boolean isVariableConsistentWithAssignment(Variable<T> chosen, Assignment<T> assignment, CSP<T> csp) {
        List<Constraint<T>> constraintsThatInvolveChosen = csp.getConstraints().stream()
                .parallel()
                .filter(constr ->
                        assignment.isVariableAssigned(constr.getVar1()) &&
                        assignment.isVariableAssigned(constr.getVar2()) &&
                        (constr.getVar1().equals(chosen) || constr.getVar2().equals(chosen))
                ).collect(Collectors.toList());

        return constraintsThatInvolveChosen.stream()
                .parallel()
                .allMatch(constr -> constr.getConstraintTester().apply(
                assignment.getValueFor(constr.getVar1()),
                assignment.getValueFor(constr.getVar2())
        ));
    }


    public boolean AC3(CSP<T> csp){
        Queue<Constraint<T>> constraintQueue = new LinkedList<>();
        for(Constraint<T> constr : csp.getConstraints()){
            constraintQueue.add(new Constraint<>(constr.getVar1(), constr.getVar2(), constr.getConstraintTester()));
            constraintQueue.add(new Constraint<>(constr.getVar2(), constr.getVar1(), constr.getConstraintTester()));
        }

        while(!constraintQueue.isEmpty()){
            Constraint<T> curr = constraintQueue.poll();
            if(AC3_Revise(csp, curr)){
                if(curr.getVar1().getCurrentDomain().size() == 0) return false;
                csp.getConstraints().stream()
                        .filter(constr -> constr.getVar2().equals(curr.getVar1()) && !constr.getVar1().equals(constr.getVar2()))
                        .forEach(constraintQueue::add);
            }
        }

        return true;
    }

    private boolean AC3_Revise(CSP<T> csp, Constraint<T> curr) {
        boolean revised = false;
        Set<T> toRemove = new HashSet<>();
        for(T value1 : curr.getVar1().getCurrentDomain()){
            boolean constrSatisfied = false;
            for(T value2 : curr.getVar2().getCurrentDomain()){
                if(curr.getConstraintTester().apply(value1, value2)){
                    constrSatisfied = true;
                    break;
                }
            }

            if(!constrSatisfied){
                toRemove.add(value1);
                revised = true;
            }
        }
        for(T value : toRemove){
            curr.getVar1().removeFromDomain(value);
        }
        return revised;
    }

    // TODO: implement least-costraining-value heuristic
    private List<T> orderDomainValues(CSP<T> csp, Variable<T> var, Assignment<T> assignment) {
        /*
        Map<T, Integer> varValueToHeuristicScore = new HashMap<>(); // maps each value in the variable's domain to the number of values it rules out from the other domains if assigned
        CSP<T> cloneCSP = new CSP<>(csp);
        Variable<T> cloneVar = cloneCSP.getVariables().stream().filter(v -> v.equals(var)).findFirst().get();
        Assignment<T> cloneAssignment = new Assignment<>();
        assignment.getAssignedVariables().forEach(v1 -> cloneAssignment.assign(
                cloneCSP.getVariables().stream().filter(v2 -> v2.equals(v1)).findFirst().get(),
                assignment.getValueFor(v1)
        ));
        int sumOfDomainSizes = cloneCSP.getVariables().stream()
                .mapToInt(v -> v.getCurrentDomain().size())
                .reduce(0, Integer::sum);
        for(T value : cloneVar.getCurrentDomain()){
            cloneAssignment.assign(cloneVar, value);

        }
         */
        return var.getCurrentDomain().stream().toList();
    }

    private Variable<T> selectUnassignedVariable(CSP<T> csp, Assignment<T> assignment) {
        Set<Variable<T>> unassignedVariables = csp.getVariables().stream()
                .filter(var -> !assignment.getAssignedVariables().contains(var))
                .collect(Collectors.toSet());

        // Apply Minimum-Remaining-Values heuristic (pick the variable with the smallest domain)
        OptionalInt minDomainSize = unassignedVariables.stream()
                .mapToInt(var -> var.getCurrentDomain().size())
                .min();

        unassignedVariables = unassignedVariables.stream()
                .filter(var -> var.getCurrentDomain().size() == minDomainSize.getAsInt())
                .collect(Collectors.toSet());


        if (unassignedVariables.size() > 1) {
            // Apply Degree heuristic to break ties (pick the variable involved in the largest number of constraints)

            OptionalInt largestNumberOfConstraints = unassignedVariables.stream()
                    .mapToInt(var -> (int) csp.getConstraints().stream()
                            .filter(constr -> constr.getVar1().equals(var) || constr.getVar2().equals(var)).count())
                    .max();

            return unassignedVariables.stream()
                    .filter(var -> largestNumberOfConstraints.getAsInt() == csp.getConstraints().stream()
                            .filter(constr -> constr.getVar1().equals(var) || constr.getVar2().equals(var)).count())
                    .collect(Collectors.toList())
                    .get(0);
        }

        return new ArrayList<>(unassignedVariables).get(0);
    }
}
