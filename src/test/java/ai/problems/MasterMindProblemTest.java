package ai.problems;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MasterMindProblemTest {

    @Test
    public void generates_accessors() {
        final MasterMindProblem problem = new MasterMindProblem(new Random(1));
        final Set<Problem> successors = problem.getSuccessors();
        assertEquals(1296, successors.size());
        Set<List<Integer>> seen = Sets.newHashSet();
        for (Problem successor : successors) {
            final List<Integer> attempt = ((MasterMindProblem) successor).getRows().get(0).getAttempt();
            assertFalse(seen.contains(attempt));
            seen.add(attempt);
        }
    }

    @Test
    public void can_solve_problems_fast() {
        final long start = System.currentTimeMillis();
        int sum = 0;
        final int N = 100;
        for (int u = 0; u < N; u++) {
            MasterMindProblem problem = new MasterMindProblem(new Random());
            int i = 0;
            do {
                final Set<Problem> successors = problem.getSuccessors();
                problem = (MasterMindProblem) successors.iterator().next();
            } while (!problem.isSolved() && i++ < 200);
            sum += problem.getRows().size();
        }
        final long took = System.currentTimeMillis() - start;
        assertTrue(took < 1000);
        assertTrue((float) sum / N < 6);
    }

}