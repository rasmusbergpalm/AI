package ai.problems;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QueensProblemTest {

    @Test
    public void score_can_be_calculated() {
        QueensProblem problem = new QueensProblem(new int[]{0, 0, 0, 0, 0, 0, 0, 0});
        assertEquals(56, problem.getCost(), 0d);

        problem = new QueensProblem(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        assertEquals(56, problem.getCost(), 0d);

        problem = new QueensProblem(new int[]{7, 6, 5, 4, 3, 2, 1, 0});
        assertEquals(56, problem.getCost(), 0d);

        problem = new QueensProblem(new int[]{0, 0, 0, 0, 7, 7, 7, 7});
        assertEquals(26, problem.getCost(), 0d);

        problem = new QueensProblem(new int[]{5, 3, 1, 7, 2, 8, 6, 4});
        assertEquals(0, problem.getCost(), 0d);
        assertTrue(problem.isSolved());
    }

    @Test
    public void can_create_successors() {
        final QueensProblem problem = new QueensProblem(new int[]{0, 0, 0, 0, 0, 0, 0, 0});

        final Set<QueensProblem> successors = problem.getSuccessors();
        assertEquals(56, successors.size());
    }
}
