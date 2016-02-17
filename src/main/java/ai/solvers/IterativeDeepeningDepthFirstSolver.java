package ai.solvers;

import ai.problems.Problem;
import ai.solvers.DepthFirstSolver.LimitReachedException;
import com.google.common.base.Optional;

public class IterativeDeepeningDepthFirstSolver implements Solver {
    @Override
    public <T extends Problem<T>> Optional<T> solve(final T initial) {
        int limit = 1;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                return new DepthFirstSolver(limit).solve(initial);
            } catch (final LimitReachedException ignored) {
                limit++;
            }
        }
        throw new IllegalStateException("Was interrupted while searching for solution.");
    }
}
