package ai.solvers;

import ai.problems.QueensProblem;
import com.google.common.base.Optional;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class BreadthFirstSolverTest {

    @Test
    public void can_solve_queens_problems() {
        final QueensProblem problem = new QueensProblem(new Random(), 6);
        final Solver solver = new BreadthFirstSolver();
        final Optional<QueensProblem> solution = solver.solve(problem);
        assertTrue(solution.isPresent());
    }
}