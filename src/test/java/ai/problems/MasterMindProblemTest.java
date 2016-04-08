package ai.problems;

import ai.solvers.RandomSolver;
import com.google.common.base.Optional;
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
    public void can_solve_problems() {
        final RandomSolver solver = new RandomSolver(new Random(0), 10);

        final Optional<MasterMindProblem> solution = solver.solve(new MasterMindProblem(new Random(0)));

        assertTrue(solution.isPresent());
    }

}