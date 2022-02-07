package csp.base_classes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Assignment<VarChild extends Variable<T>, T>{
    protected Map<VarChild, T> variableValueMap;

    public Assignment() {
        this.variableValueMap = new HashMap<>();
    }

    /**
     * Performs the assignment { var = value }
     * @param var the variable to be assigned
     * @param value the value to be assigned to var
     */
    public void assign(VarChild var, T value){
        variableValueMap.put(var, value);
    }

    /**
     * Removes any assignment made to var
     * @param var the variable to "unAssign"
     */
    public void unAssign(VarChild var){
        variableValueMap.remove(var);
    }

    /**
     *
     * @return a set containing all the assigned variables
     */
    public Set<VarChild> getAssignedVariables() {
        return new HashSet<>(variableValueMap.keySet());
    }

    /**
     *
     * @param var the variable to get the value from
     * @return the value assigned to that variable
     * @throws IllegalArgumentException if the variable var is not assigned
     */
    public T getValueFor(VarChild var) throws IllegalArgumentException{
        if(variableValueMap.get(var) == null) throw new IllegalArgumentException();
        return variableValueMap.get(var);
    }

    @Override
    public String toString() {
        String s = "";
        for(VarChild var : variableValueMap.keySet()){
            s += "{ " + var.toString() + " = " + variableValueMap.get(var) + " }\n";
        }
        return s;
    }
}