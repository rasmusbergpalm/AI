import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Set;

public class HillClimber<P extends Problem> implements Solver<P> {
    private final int maxIterations;
    private final Comparator<P> PROBLEM_COMPARATOR = new Comparator<P>() {
        @Override
        public int compare(P a, P b) {
            return Double.compare(b.getScore(), a.getScore());
        }
    };

    public HillClimber(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    @Override
    public P solve(P problem) {
        for (int i = 0; i < maxIterations; i++) {
            final Set<P> successors = problem.getSuccessors();

            final PriorityQueue<P> problems = new PriorityQueue<P>(successors.size(), PROBLEM_COMPARATOR);
            problems.addAll(successors);

            final P best = problems.poll();

            if (best.getScore() <= problem.getScore()) return problem;

            problem = best;
        }

        return problem;
    }
}
