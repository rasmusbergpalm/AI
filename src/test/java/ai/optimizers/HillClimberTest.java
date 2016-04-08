package ai.optimizers;

import ai.problems.QueensProblem;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class HillClimberTest {

    @Test
    public void testSolve() {
        final HillClimber climber = new HillClimber(100);
        final QueensProblem problem = new QueensProblem(new Random(1L), 8);
        final QueensProblem optimized = climber.optimize(problem);
        assertTrue(optimized.getCost() < problem.getCost());
    }
}
