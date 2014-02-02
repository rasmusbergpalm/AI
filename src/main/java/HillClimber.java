import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Set;

public class HillClimber<P extends Problem> implements Solver<P> {
    private int maxIterations;

    public HillClimber(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    @Override
    public P solve(P problem) {
        for (int i = 0; i < maxIterations; i++) {
            final Set<P> successors = problem.getSuccessors();

            final PriorityQueue<P> states = new PriorityQueue<P>(successors.size(), new Comparator<P>() {
                @Override
                public int compare(P a, P b) {
                    return Double.compare(b.getScore(), a.getScore());
                }
            });
            states.addAll(successors);

            final P best = states.poll();

            if(best.getScore() <= problem.getScore()) return problem;

            problem = best;
        }

        return problem;
    }
}
