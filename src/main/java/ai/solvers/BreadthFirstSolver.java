package ai.solvers;

import ai.problems.Problem;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;

import java.util.LinkedHashSet;
import java.util.Set;

public class BreadthFirstSolver implements Solver {

    @Override
    public <T extends Problem<T>> Optional<T> solve(final T initial) {
        if (initial.isSolved()) {
            return Optional.of(initial);
        }
        final Set<T> frontier = new LinkedHashSet<T>();
        frontier.add(initial);
        final Set<T> explored = Sets.newHashSet();

        while (!frontier.isEmpty()) {
            final T current = frontier.iterator().next();
            frontier.remove(current);
            explored.add(current);
            final Set<T> successors = current.getSuccessors();
            for (final T successor : successors) {
                if (!explored.contains(successor) && !frontier.contains(successor)) {
                    if (successor.isSolved()) {
                        return Optional.of(successor);
                    } else {
                        frontier.add(successor);
                    }
                }
            }
        }

        return Optional.absent();
    }
}
