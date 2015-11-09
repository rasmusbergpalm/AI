package ai.solvers;

import ai.problems.Problem;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public class RandomSolver implements Solver {

    private final Random random;
    private final int maxAttempts;

    public RandomSolver(final Random random, final int maxAttempts) {
        this.random = random;
        this.maxAttempts = maxAttempts;
    }

    @Override
    public <T extends Problem<T>> T solve(final T initial) {
        T problem = initial;
        for (int i = 0; i < maxAttempts; i++) {
            if (problem.isSolved()) {
                return problem;
            }
            final List<T> successors = Lists.newArrayList(problem.getSuccessors());
            problem = successors.get(random.nextInt(successors.size()));
        }

        return problem;
    }
}
