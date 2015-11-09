package ai.problems;

import ai.solvers.RandomSolver;
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
        final Set<MasterMindProblem> successors = problem.getSuccessors();
        assertEquals(1296, successors.size());
        final Set<List<Integer>> seen = Sets.newHashSet();
        for (final MasterMindProblem successor : successors) {
            final List<Integer> attempt = successor.getRows().get(0).getAttempt();
            assertFalse(seen.contains(attempt));
            seen.add(attempt);
        }
    }

    @Test
    public void can_solve_problems_fast() {
        final long start = System.currentTimeMillis();
        int sumAttempts = 0;
        final int N = 100;
        final RandomSolver solver = new RandomSolver(new Random(0), 10);
        for (int u = 0; u < N; u++) {
            final MasterMindProblem solved = solver.solve(new MasterMindProblem(new Random(0)));
            sumAttempts += solved.getRows().size();
        }
        final long took = System.currentTimeMillis() - start;
        assertTrue(took < 1000);
        assertTrue((float) sumAttempts / N < 6);
    }

}