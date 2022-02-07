package csp.base_classes;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CSP<T> {
    protected Set<Variable<T>> variables;
    protected List<Constraint<T>> constraints;

    public CSP(){
        variables = new HashSet<>();
        constraints = new LinkedList<>();
    }

    /**
     *
     * @param variable the variable to be added to the csp
     */
    public void addVariable(Variable<T> variable){
        variables.add(variable);
    }

    /**
     *
     * @param constraint the constraint to be added to the csp
     */
    public void addBinaryConstraint(Constraint<T> constraint){
        constraints.add(constraint);
    }

    public Set<Variable<T>> getVariables() {
        return new HashSet<>(variables);
    }

    public List<Constraint<T>> getConstraints() {
        return new LinkedList<>(constraints);
    }
}
