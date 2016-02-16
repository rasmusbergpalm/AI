package ai.optimizers;

import ai.problems.QueensProblem;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class HillClimberTest {

    @Test
    public void testSolve() {
        int solved = 0;
        final int N = 1000;
        final Random random = new Random(1L);
        for (int i = 0; i < N; i++) {
            final QueensProblem queensProblem = new QueensProblem(random, 8);
            final HillClimber climber = new HillClimber(100);
            final QueensProblem optimized = climber.optimize(queensProblem);
            if (optimized.isSolved()) {
                solved++;
            }
        }
        assertEquals(0.14, (double) solved / N, 0.02d);
    }
}
