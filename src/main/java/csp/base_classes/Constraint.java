package csp.base_classes;

import java.util.Set;
import java.util.function.BiFunction;

/**
 * In this implementation a constraint is a binary constraint. Remember that every csp can be reduced to an equivalent csp with only
 * binary constraints.
 */
public class /* pure */ Constraint<VarChild extends Variable<T>, T> {
    private final VarChild var1;
    private final VarChild var2;
    private final BiFunction<T, T, Boolean> constraintTester;

    public Constraint(VarChild var1, VarChild var2, BiFunction<T, T, Boolean> constraintTester) {
        this.var1 = var1;
        this.var2 = var2;
        this.constraintTester = constraintTester;
    }

    /**
     *
     * @param assignment the assignment to be tested
     * @return true if the assignment satisfies the constraint, false otherwise
     */
    public boolean test(Assignment<VarChild, T> assignment){
        Set<VarChild> assignedVariables = assignment.getAssignedVariables();
        boolean containsVar1 = assignedVariables.contains(var1);
        boolean containsVar2 = assignedVariables.contains(var2);
        if(containsVar1 && containsVar2){
            return constraintTester.apply(assignment.getValueFor(var1), assignment.getValueFor(var2));
        }
        return true;
    }

    public VarChild getVar1() {
        return var1;
    }

    public VarChild getVar2() {
        return var2;
    }

    public BiFunction<T, T, Boolean> getConstraintTester() {
        return constraintTester;
    }
}
