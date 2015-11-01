import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SimulatedAnnealing implements Solver {
    private final AnnealingSchedule schedule;
    private final Random random;

    public SimulatedAnnealing(AnnealingSchedule schedule, Random random) {
        this.schedule = schedule;
        this.random = random;
    }

    @Override
    public Problem solve(Problem problem) {
        int i = 0;
        double temperature;
        while ((temperature = schedule.getTemperature(i++)) > 0) {
            final Set<Problem> successors = problem.getSuccessors();
            List<Problem> shuffled = Lists.newArrayList(successors);
            Collections.shuffle(shuffled, random);
            final Problem next = shuffled.get(0);
            final double deltaScore = next.getScore() - problem.getScore();
            if (deltaScore > 0 || random.nextDouble() < Math.exp(deltaScore / temperature)) {
                problem = next;
            }
        }
        return problem;
    }

    public interface AnnealingSchedule {
        public double getTemperature(int iteration);
    }
}
