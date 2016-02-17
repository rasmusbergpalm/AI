package ai.solvers;

import ai.problems.Problem;
import com.google.common.base.Optional;

import java.util.Set;

public class DepthFirstSolver implements Solver {

    private final int limit;

    public DepthFirstSolver(final int limit) {
        this.limit = limit;
    }

    /**
     * @throws LimitReachedException in case the limit is reached
     */
    @Override
    public <T extends Problem<T>> Optional<T> solve(final T initial) {
        return solve(initial, limit);
    }

    private <T extends Problem<T>> Optional<T> solve(final T problem, final int limit) {
        if (problem.isSolved()) {
            return Optional.of(problem);
        }
        if (limit == 0) {
            throw new LimitReachedException();
        }
        final Set<T> successors = problem.getSuccessors();
        boolean limitReached = false;
        for (final T successor : successors) {
            try {
                final Optional<T> solution = solve(successor, limit - 1);
                if (solution.isPresent()) {
                    return solution;
                }
            } catch (final LimitReachedException ignored) {
                limitReached = true;
            }
        }
        if (limitReached) {
            throw new LimitReachedException();
        }
        return Optional.absent();
    }

    static class LimitReachedException extends IllegalStateException {
    }

}
