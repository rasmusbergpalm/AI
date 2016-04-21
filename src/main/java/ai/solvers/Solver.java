package ai.solvers;

import ai.problems.Problem;
import com.google.common.base.Optional;

/**
 * An algorithm that can solve {@link Problem}s
 */
public interface Solver {
    /**
     * Solve the given problem
     *
     * @param initial The problem in the initial state
     * @param <T> The problem type.
     * @return {@link Optional#absent()} if no solution was found otherwise returns the problem in a solution state.
     */
    <T extends Problem<T>> Optional<T> solve(T initial);
}
