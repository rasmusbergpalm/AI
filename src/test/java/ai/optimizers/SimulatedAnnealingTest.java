package ai.optimizers;

import ai.optimizers.SimulatedAnnealing.AnnealingSchedule;
import ai.problems.QueensProblem;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

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
        int solved = 0;
        final int N = 1000;
        for (int i = 0; i < N; i++) {
            final SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(schedule, new Random(1L));

            final QueensProblem problem = new QueensProblem(random, 8);
            final QueensProblem optimized = simulatedAnnealing.optimize(problem);

            if (optimized.isSolved()) {
                solved++;
            }
        }
        assertEquals(0.089, (double) solved / N, 0.02d);
    }
}
