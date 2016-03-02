package ai.problems;

import ai.problems.RouteProblem.CityMap;
import ai.problems.RouteProblem.CityMap.Intersection;
import ai.solvers.SolutionPrinter;
import ai.solvers.Solver;
import ai.solvers.UniformCostSolver;
import com.google.common.base.Optional;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RouteProblemTest {

    @Test
    public void copenhagen_test() throws IOException, URISyntaxException {
        final CityMap cityMap = CityMap.from(
            Paths.get(
                CityMap.class.getClassLoader()
                    .getResource("copenhagen.txt")
                    .toURI()
            )
        );
        final Solver solver = new UniformCostSolver();
        final RouteProblem problem = new RouteProblem(
            cityMap,
            Intersection.of("SktPedersStraede", "Larsbjoernsstraede"),
            Intersection.of("Studiestraede", "Larsbjoernsstraede")
        );

        final Optional<RouteProblem> solution = solver.solve(problem);
        assertTrue(solution.isPresent());
        assertEquals(161.85, solution.get().getCost(), 0.01);
        SolutionPrinter.print(solution.get());
    }

    @Test
    public void manhattan_test() throws IOException, URISyntaxException {
        final CityMap cityMap = CityMap.from(
            Paths.get(
                CityMap.class.getClassLoader()
                    .getResource("manhattan.txt")
                    .toURI()
            )
        );
        final Solver solver = new UniformCostSolver();
        final RouteProblem problem = new RouteProblem(
            cityMap,
            Intersection.of("street_0", "avenue_0"),
            Intersection.of("street_9", "avenue_9")
        );

        final Optional<RouteProblem> solution = solver.solve(problem);
        assertTrue(solution.isPresent());
        assertEquals(18.0, solution.get().getCost(), 0);
    }

}