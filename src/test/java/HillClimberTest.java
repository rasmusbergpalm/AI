import org.junit.Test;

public class HillClimberTest {

    @Test
    public void testSolve() {
        int solved = 0;
        final int N = 10000;
        for(int i=0; i< N; i++){
            final QueensProblem queensProblem = new QueensProblem();
            final HillClimber climber = new HillClimber<QueensProblem>(100);
            final QueensProblem solution = (QueensProblem) climber.solve(queensProblem);
            if(solution.getScore() == 0) solved++;

        }
        System.out.println((float)solved/N);
    }
}
