import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class SimulatedAnnealingTest {

    private SimulatedAnnealing.AnnealingSchedule schedule = new SimulatedAnnealing.AnnealingSchedule() {
        @Override
        public double getTemperature(int iteration) {
            return 1 - (double) iteration / 100;
        }
    };

    @Test
    public void testSolve() {
        final Random random = new Random(1l);
        int solved = 0;
        final int N = 1000;
        for (int i = 0; i < N; i++) {
            final SimulatedAnnealing<QueensProblem> simulatedAnnealing = new SimulatedAnnealing<QueensProblem>(schedule, new Random(1l));

            final QueensProblem problem = new QueensProblem(random);
            final QueensProblem solution = simulatedAnnealing.solve(problem);

            if (solution.getScore() == 0) solved++;
        }
        assertEquals(0.089, (double) solved / N, 0.02d);
    }
}
