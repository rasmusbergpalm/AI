package ai.problems;

import com.google.common.collect.ImmutableList;
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
    public void generates_solutions() {
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
    public void can_solve_random_problem() {
        MasterMindProblem problem = new MasterMindProblem(new Random(1));
        assertEquals(ImmutableList.of(3, 4, 1, 3), problem.getGoal());
        int i = 0;
        do {
            final Set<Problem> successors = problem.getSuccessors();
            problem = (MasterMindProblem) successors.iterator().next();
        } while (!problem.isSolved() && i++ < 100);
        System.out.println(problem);
        assertTrue(problem.getRows().size() < 10);
        assertEquals(ImmutableList.of(3, 4, 1, 3), problem.getRows().reverse().get(0).getAttempt());

    }

    @Test
    public void timer() {
        final long start = System.currentTimeMillis();
        int sum = 0;
        final int N = 1000;
        for (int u = 0; u < N; u++) {
            MasterMindProblem problem = new MasterMindProblem(new Random());
            int i = 0;
            do {
                final Set<Problem> successors = problem.getSuccessors();
                problem = (MasterMindProblem) successors.iterator().next();
            } while (!problem.isSolved() && i++ < 200);
            sum += problem.getRows().size();
        }
        System.out.println("Average: " + (float) (System.currentTimeMillis() - start) / N + "ms per problem");
        System.out.println("Average attempts: " + sum / (float) N);
    }

}