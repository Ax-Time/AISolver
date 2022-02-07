package csp.base_classes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Assignment<T>{
    protected Map<Variable<T>, T> variableValueMap;

    public Assignment() {
        this.variableValueMap = new HashMap<>();
    }

    /**
     * Performs the assignment { var = value }
     * @param var the variable to be assigned
     * @param value the value to be assigned to var
     */
    public void assign(Variable<T> var, T value){
        variableValueMap.put(var, value);
    }

    /**
     * Removes any assignment made to var
     * @param var the variable to "unAssign"
     */
    public void unAssign(Variable<T> var){
        variableValueMap.remove(var);
    }

    /**
     *
     * @return a set containing all the assigned variables
     */
    public Set<Variable<T>> getAssignedVariables() {
        return new HashSet<>(variableValueMap.keySet());
    }

    /**
     *
     * @param var the variable to get the value from
     * @return the value assigned to that variable
     * @throws IllegalArgumentException if the variable var is not assigned
     */
    public T getValueFor(Variable<T> var) throws IllegalArgumentException{
        if(variableValueMap.get(var) == null) throw new IllegalArgumentException();
        return variableValueMap.get(var);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(Variable<T> var : variableValueMap.keySet()){
            s.append("{ ").append(var.toString()).append(" = ").append(variableValueMap.get(var)).append(" }\n");
        }
        return s.toString();
    }
}
