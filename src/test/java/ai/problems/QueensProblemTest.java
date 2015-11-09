package ai.problems;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class QueensProblemTest {

    @Test
    public void testGetScore() {
        QueensProblem problem = new QueensProblem(new int[]{0, 0, 0, 0, 0, 0, 0, 0});
        assertEquals(-56, problem.getScore(), 0d);

        problem = new QueensProblem(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        assertEquals(-56, problem.getScore(), 0d);

        problem = new QueensProblem(new int[]{7, 6, 5, 4, 3, 2, 1, 0});
        assertEquals(-56, problem.getScore(), 0d);

        problem = new QueensProblem(new int[]{0, 0, 0, 0, 7, 7, 7, 7});
        assertEquals(-26, problem.getScore(), 0d);

        problem = new QueensProblem(new int[]{5, 3, 1, 7, 2, 8, 6, 4});
        assertEquals(0, problem.getScore(), 0d);
        assertTrue(problem.isSolved());

        try {
            new QueensProblem(new int[]{5, 3, 1, 7, 2, 8, 6, 4, 1});
            fail("Should only allow 8 queens");
        } catch (final Exception ignored) {
        }
    }

    @Test
    public void testGetSuccessors() {
        QueensProblem problem = new QueensProblem(new int[]{0, 0, 0, 0, 0, 0, 0, 0});

        final Set<QueensProblem> successors = problem.getSuccessors();
        assertEquals(56, successors.size());
        for (final QueensProblem successor : successors) {
            assertFalse(problem.equals(successor));

            final int[] successorState = successor.getState();
            int countZeros = 0;
            for (int i = 0; i < 8; i++) {
                if (successorState[i] == 0) countZeros++;
            }
            assertEquals(7, countZeros);
        }
    }
}
