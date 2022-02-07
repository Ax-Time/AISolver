package csp.base_classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstraintTest {

    @Test
    void test1() {
        Variable<Integer> v1 = new Variable<>("v1");
        Variable<Integer> v2 = new Variable<>("v2");

        Constraint<Integer> c1, c2, c3;

        c1 = new Constraint<>(
                v1, v2,
                (a, b) -> {
                    return a != b;
                }
        );

        c2 = new Constraint<>(
                v1, v2,
                (a, b) -> {
                    return a + b == 3;
                }
        );

        c3 = new Constraint<>(
                v1, v2,
                (a, b) -> {
                    return a == 0 && b == 3;
                }
        );

        Assignment<Integer> assignment1, assignment2, assignment3;

        assignment1 = new Assignment<>();
        assignment2 = new Assignment<>();
        assignment3 = new Assignment<>();

        // assignment1 satisfies c1 and c2
        assignment1.assign(v1, 1);
        assignment1.assign(v2, 2);
        assignment1.assign(new Variable<>("useless"), 1000);

        assertTrue(c1.test(assignment1));
        assertTrue(c2.test(assignment1));
        assertFalse(c3.test(assignment1));

        // assignment2 satisfies c1, c2 and c3
        assignment2.assign(v1, 0);
        assignment2.assign(v2, 3);
        assignment2.assign(new Variable<>("useless2"), -1);

        assertTrue(c1.test(assignment2));
        assertTrue(c2.test(assignment2));
        assertTrue(c3.test(assignment2));

        // assignment3 satisfies nothing
        assignment3.assign(v1, 10);
        assignment3.assign(v2, 10);
        assertFalse(c1.test(assignment3));
        assertFalse(c2.test(assignment3));
        assertFalse(c3.test(assignment3));

    }
}