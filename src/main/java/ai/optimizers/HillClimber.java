package ai.optimizers;

import ai.problems.Problem;

import java.util.PriorityQueue;
import java.util.Set;

public class HillClimber implements Optimizer {
    private final int maxIterations;

    public HillClimber(final int maxIterations) {
        this.maxIterations = maxIterations;
    }

    @Override
    public <T extends Problem<T>> T optimize(final T initial) {
        T current = initial;
        for (int i = 0; i < maxIterations; i++) {
            final Set<T> successors = current.getSuccessors();

            final PriorityQueue<T> problems = new PriorityQueue<T>(successors.size(), Problem.COST_COMPARATOR);
            problems.addAll(successors);

            final T next = problems.poll();

            if (next.getCost() >= current.getCost()) {
                return current;
            }

            current = next;
        }

        return current;
    }
}
