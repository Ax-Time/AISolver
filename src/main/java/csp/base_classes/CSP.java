package csp.base_classes;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CSP<VarChild extends Variable<T>, T> {
    protected Set<VarChild> variables;
    protected List<Constraint<VarChild, T>> constraints;

    public CSP(){
        variables = new HashSet<>();
        constraints = new LinkedList<>();
    }

    /**
     *
     * @param variable the variable to be added to the csp
     */
    public void addVariable(VarChild variable){
        variables.add(variable);
    }

    /**
     *
     * @param constraint the constraint to be added to the csp
     */
    public void addBinaryConstraint(Constraint<VarChild, T> constraint){
        constraints.add(constraint);
    }

    public Set<VarChild> getVariables() {
        return new HashSet<>(variables);
    }

    public List<Constraint<VarChild, T>> getConstraints() {
        return new LinkedList<>(constraints);
    }
}
