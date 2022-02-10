package csp.base_classes;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Variable<T> {
    protected final String name;
    protected Set<T> domain;

    public Variable(String name) {
        this.name = name;
        this.domain = new HashSet<>();
    }

    public Set<T> getCurrentDomain(){
        return domain;
    }

    public void removeFromDomain(T value){
        domain.remove(value);
    }

    public void setDomain(Set<T> domain){
        this.domain = domain;
    }

    public Variable<T> clone() {
        Variable<T> varClone = new Variable<>(name);
        varClone.domain.addAll(domain);

        return varClone;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Variable<?> variable = (Variable<?>) o;
        return Objects.equals(name, variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
