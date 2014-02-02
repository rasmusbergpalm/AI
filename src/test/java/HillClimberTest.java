import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class HillClimberTest {

    @Test
    public void testSolve() {
        int solved = 0;
        final int N = 1000;
        final Random random = new Random(1l);
        for(int i=0; i< N; i++){
            final QueensProblem queensProblem = new QueensProblem(random);
            final HillClimber<QueensProblem> climber = new HillClimber<QueensProblem>(100);
            final QueensProblem solution = climber.solve(queensProblem);
            if(solution.getScore() == 0) solved++;
        }
        assertEquals(0.14, (double) solved / N, 0.02d);
    }
}
