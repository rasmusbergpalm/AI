package ai.solvers;

import ai.problems.Problem;

import java.util.PriorityQueue;
import java.util.Set;

public class HillClimber implements Solver {
    private final int maxIterations;

    public HillClimber(final int maxIterations) {
        this.maxIterations = maxIterations;
    }

    @Override
    public Problem solve(Problem problem) {
        for (int i = 0; i < maxIterations; i++) {
            final Set<Problem> successors = problem.getSuccessors();

            final PriorityQueue<Problem> problems = new PriorityQueue<Problem>(successors.size(), Problem.COMPARATOR);
            problems.addAll(successors);

            final Problem best = problems.poll();

            if (best.getScore() <= problem.getScore()) {
                return problem;
            }

            problem = best;
        }

        return problem;
    }
}
