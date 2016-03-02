package ai.solvers;

import ai.problems.Problem;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class UniformCostSolver implements Solver {

    @Override
    public <T extends Problem<T>> Optional<T> solve(final T initial) {
        if (initial.isSolved()) {
            return Optional.of(initial);
        }

        final Frontier<T> frontier = new Frontier<T>();
        frontier.offer(initial);
        final Set<String> explored = Sets.newHashSet();

        while (!frontier.isEmpty()) {
            final T current = frontier.poll();
            if (current.isSolved()) {
                return Optional.of(current);
            }
            explored.add(current.getState());
            final Set<T> successors = current.getSuccessors();
            for (final T successor : successors) {
                final Optional<T> successorInFrontier = frontier.get(successor.getState());
                if (!explored.contains(successor.getState()) && !successorInFrontier.isPresent()) {
                    frontier.offer(successor);
                } else if (successorInFrontier.isPresent() && successor.getCost() < successorInFrontier.get()
                    .getCost()) {
                    frontier.remove(successorInFrontier.get());
                    frontier.offer(successor);
                }
            }
        }

        return Optional.absent();
    }

    public static class Frontier<T extends Problem<T>> {
        private final PriorityQueue<T> queue = new PriorityQueue<T>(10, Problem.COST_COMPARATOR);
        private final Map<String, T> map = Maps.newHashMap();

        public boolean isEmpty() {
            return queue.isEmpty();
        }

        public Optional<T> get(final String state) {
            return Optional.fromNullable(map.get(state));
        }

        public T poll() {
            final T obj = queue.remove();
            map.remove(obj.getState());
            return obj;
        }

        public void offer(final T obj) {
            Preconditions.checkNotNull(obj);
            queue.offer(obj);
            map.put(obj.getState(), obj);
        }

        public void remove(final T obj) {
            if (!queue.remove(obj)) {
                throw new IllegalStateException("Was asked to remove object from queue that was not present: " + obj.getState());
            }
            if (map.remove(obj.getState()) == null) {
                throw new IllegalStateException("Was asked to remove object from map that was not present: " + obj.getState());
            }
        }
    }
}
