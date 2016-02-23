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
        final Set<T> explored = Sets.newHashSet();

        while (!frontier.isEmpty()) {
            final T current = frontier.poll();
            if (current.isSolved()) {
                return Optional.of(current);
            }
            explored.add(current);
            final Set<T> successors = current.getSuccessors();
            for (final T successor : successors) {
                final Optional<T> frontierSuccessor = frontier.get(successor);
                if (!explored.contains(successor) && !frontierSuccessor.isPresent()) {
                    frontier.offer(successor);
                } else if (frontierSuccessor.isPresent() && successor.getCost() < frontierSuccessor.get().getCost()) {
                    frontier.remove(frontierSuccessor.get());
                    frontier.offer(successor);
                }
            }
        }

        return Optional.absent();
    }

    public static class Frontier<T extends Problem<T>> {
        private final PriorityQueue<T> queue = new PriorityQueue<T>(10, Problem.COST_COMPARATOR);
        private final Map<T, T> map = Maps.newHashMap();

        public boolean isEmpty() {
            return queue.isEmpty();
        }

        public Optional<T> get(final T obj) {
            return Optional.fromNullable(map.get(obj));
        }

        public T poll() {
            final T obj = queue.remove();
            if (obj != null) {
                map.remove(obj);
            }
            return obj;
        }

        public void offer(final T obj) {
            Preconditions.checkNotNull(obj);
            queue.offer(obj);
            map.put(obj, obj);
        }

        public void remove(final T obj) {
            queue.remove(obj); // TODO this is O(n) complex.
            map.remove(obj);
        }
    }
}
