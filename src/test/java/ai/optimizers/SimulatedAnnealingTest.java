package ai.optimizers;

import ai.optimizers.SimulatedAnnealing.AnnealingSchedule;
import ai.problems.QueensProblem;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class SimulatedAnnealingTest {

    private final AnnealingSchedule schedule = new AnnealingSchedule() {
        @Override
        public double getTemperature(final int iteration) {
            return 1 - (double) iteration / 100;
        }
    };

    @Test
    public void testSolve() {
        final Random random = new Random(1L);

        final SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(schedule, new Random(1L));

        final QueensProblem problem = new QueensProblem(random, 8);
        final QueensProblem optimized = simulatedAnnealing.optimize(problem);

        assertTrue(optimized.getCost() < problem.getCost());
    }
}
