package ai.solvers;

import ai.problems.Problem;

public interface Solver {
    <T extends Problem<T>> T solve(T initial);
}
