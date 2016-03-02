package ai.solvers;

import ai.problems.Problem;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import java.util.List;

public class SolutionPrinter {

    public static <T extends Problem<T>> void print(final Problem<T> solution) {
        final List<Problem<T>> path = Lists.newArrayList();
        path.add(solution);
        Optional<T> parent = solution.getParent();

        while (parent.isPresent()) {
            path.add(parent.get());
            parent = parent.get().getParent();
        }
        for (Problem<T> problem : Lists.reverse(path)) {
            System.out.println(problem.getState() + " cost:" + problem.getCost() + " heuristic: " + problem.getHeuristicCost());
        }
    }
}
