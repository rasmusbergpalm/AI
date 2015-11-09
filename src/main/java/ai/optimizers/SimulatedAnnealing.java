package ai.optimizers;

import ai.problems.Problem;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SimulatedAnnealing implements Optimizer {
    private final AnnealingSchedule schedule;
    private final Random random;

    public SimulatedAnnealing(final AnnealingSchedule schedule, final Random random) {
        this.schedule = schedule;
        this.random = random;
    }

    @Override
    public <T extends Problem<T>> T optimize(final T initial) {
        int i = 0;
        double temperature;
        T problem = initial;
        while ((temperature = schedule.getTemperature(i++)) > 0) {
            final Set<T> successors = problem.getSuccessors();
            final List<T> shuffled = Lists.newArrayList(successors);
            Collections.shuffle(shuffled, random);
            final T next = shuffled.get(0);
            final double deltaScore = next.getScore() - problem.getScore();
            if (deltaScore > 0 || random.nextDouble() < Math.exp(deltaScore / temperature)) {
                problem = next;
            }
        }
        return problem;
    }

    public interface AnnealingSchedule {
        double getTemperature(int iteration);
    }
}
