package ai.solvers;

import ai.problems.Problem;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;

import java.util.LinkedHashMap;
import java.util.Set;

public class BreadthFirstSolver implements Solver {

    @Override
    public <T extends Problem<T>> Optional<T> solve(final T initial) {
        if (initial.isSolved()) {
            return Optional.of(initial);
        }
        final LinkedHashMap<String, T> frontier = new LinkedHashMap<>();
        frontier.put(initial.getState(), initial);
        final Set<String> explored = Sets.newHashSet();

        while (!frontier.isEmpty()) {
            final T current = frontier.values().iterator().next();
            frontier.remove(current.getState());
            explored.add(current.getState());
            final Set<T> successors = current.getSuccessors();
            for (final T successor : successors) {
                if (!explored.contains(successor.getState()) && !frontier.containsKey(successor.getState())) {
                    if (successor.isSolved()) {
                        return Optional.of(successor);
                    } else {
                        frontier.put(successor.getState(), successor);
                    }
                }
            }
        }

        return Optional.absent();
    }
}
