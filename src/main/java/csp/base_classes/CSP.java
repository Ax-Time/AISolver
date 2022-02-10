package csp.base_classes;


import java.util.*;

public class CSP<T> {
    protected Set<Variable<T>> variables;
    protected List<Constraint<T>> constraints;

    public CSP(){
        variables = new HashSet<>();
        constraints = new LinkedList<>();
    }

    public CSP(CSP<T> csp){
        Set<Variable<T>> variablesClone = new HashSet<>(csp.variables.size());
        List<Constraint<T>> constraintsClone = new ArrayList<>(csp.constraints.size());

        csp.variables.forEach(var -> variablesClone.add(var.clone()));
        for(Constraint<T> constr : csp.constraints){
            // Find variablesClone's variable that matches var1, same for var2
            Variable<T> var1Clone = null, var2Clone = null;
            for(Variable<T> varClone : variablesClone){
                if(varClone.equals(constr.getVar1())) var1Clone = varClone;
                if(varClone.equals(constr.getVar2())) var2Clone = varClone;
            }
            constraintsClone.add(new Constraint<>(var1Clone, var2Clone, constr.getConstraintTester()));
        }

        this.variables = variablesClone;
        this.constraints = constraintsClone;
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
        return variables;
    }

    public List<Constraint<T>> getConstraints() {
        return constraints;
    }
}
