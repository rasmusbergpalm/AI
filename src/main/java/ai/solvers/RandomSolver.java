package ai.solvers;

import ai.problems.Problem;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public class RandomSolver implements Solver {

    private final Random random;
    private final int maxAttempts;

    public RandomSolver(Random random, final int maxAttempts) {
        this.random = random;
        this.maxAttempts = maxAttempts;
    }

    @Override
    public Problem solve(final Problem initial) {
        Problem problem = initial;
        for (int i = 0; i < maxAttempts; i++) {
            if (problem.isSolved()) {
                return problem;
            }
            final List<Problem> successors = Lists.newArrayList(problem.getSuccessors());
            problem = successors.get(random.nextInt(successors.size()));
        }

        return problem;
    }
}
