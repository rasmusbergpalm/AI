package ai.solvers;

import ai.problems.Problem;
import com.google.common.base.Optional;

public interface Solver {
    <T extends Problem<T>> Optional<T> solve(T initial);
}
