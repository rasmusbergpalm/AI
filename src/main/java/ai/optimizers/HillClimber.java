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
        T problem = initial;
        for (int i = 0; i < maxIterations; i++) {
            final Set<T> successors = problem.getSuccessors();

            final PriorityQueue<T> problems = new PriorityQueue<T>(successors.size(), Problem.COMPARATOR);
            problems.addAll(successors);

            final T best = problems.poll();

            if (best.getScore() <= problem.getScore()) {
                return problem;
            }

            problem = best;
        }

        return problem;
    }
}
