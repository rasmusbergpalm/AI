package ai.problems;

import ai.logic.propositional.CNF;
import ai.logic.propositional.Clause;
import ai.solvers.SolutionPrinter;
import ai.solvers.Solver;
import ai.solvers.AStarSolver;
import com.google.common.base.Optional;
import org.junit.Test;

import static ai.logic.propositional.CNF.n;
import static ai.logic.propositional.CNF.p;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ReasoningProblemTest {

    private final Solver solver = new AStarSolver();

    @Test
    public void given_a_tautology_you_cant_prove_anything() {
        final CNF kb = p("A").and(Clause.of("foo", "foo"));

        final Optional<ReasoningProblem> s1 = solver.solve(new ReasoningProblem(kb, p("bar")));
        assertFalse(s1.isPresent());

        final Optional<ReasoningProblem> s2 = solver.solve(new ReasoningProblem(kb, n("bar")));
        assertFalse(s2.isPresent());
    }

    @Test
    public void given_a_single_literal_only_that_literal_can_be_proven() {
        final CNF kb = p("A");

        final Optional<ReasoningProblem> s1 = solver.solve(new ReasoningProblem(kb, p("A")));
        assertTrue(s1.isPresent());

        final Optional<ReasoningProblem> s2 = solver.solve(new ReasoningProblem(kb, n("A")));
        assertFalse(s2.isPresent());

        final Optional<ReasoningProblem> s3 = solver.solve(new ReasoningProblem(kb, p("foo")));
        assertFalse(s3.isPresent());
    }

    @Test
    public void wumpus_step1() {
        final CNF kb = p("S11").iff(p("W12 W21"))
                .and(p("B11").iff(p("P12 P21")))
                .and(n("B11"))
                .and(n("S11"));

        final Optional<ReasoningProblem> s1 = solver.solve(new ReasoningProblem(kb, p("P12")));
        assertFalse(s1.isPresent());

        final Optional<ReasoningProblem> s2 = solver.solve(new ReasoningProblem(kb, n("P12")));
        assertTrue(s2.isPresent());

        final Optional<ReasoningProblem> s3 = solver.solve(new ReasoningProblem(kb, n("P21")));
        assertTrue(s3.isPresent());

        final Optional<ReasoningProblem> s4 = solver.solve(new ReasoningProblem(kb, n("W21").and(n("W12"))));
        assertTrue(s4.isPresent());
    }

    @Test
    public void wumpus_step3() throws Exception {
        final CNF kb = p("S11").iff(p("W12 W21"))
                .and(p("S12").iff(p("W11 W22 W13")))
                .and(p("S21").iff(p("W11 W22 W31")))
                .and(p("B11").iff(p("P12 P21")))
                .and(p("B12").iff(p("P11 P22 P13")))
                .and(p("B21").iff(p("P11 P22 P31")))
                .and(n("B11"))
                .and(n("S11"))
                .and(n("B21"))
                .and(p("S21"))
                .and(p("B12"))
                .and(n("S12"));

        final Optional<ReasoningProblem> s1 = solver.solve(new ReasoningProblem(kb, p("W31")));
        assertTrue(s1.isPresent());
        SolutionPrinter.print(s1.get());

        final Optional<ReasoningProblem> s2 = solver.solve(new ReasoningProblem(kb, p("P13")));
        assertTrue(s2.isPresent());

        final Optional<ReasoningProblem> s3 = solver.solve(new ReasoningProblem(kb, p("P22")));
        assertFalse(s3.isPresent());

        final Optional<ReasoningProblem> s4 = solver.solve(new ReasoningProblem(kb, n("P22")));
        assertTrue(s4.isPresent());
    }

    @Test
    public void rasmus_implies_human_but_human_does_not_imply_rasmus() {
        final CNF kb = p("human").iff("male female")
                .and(p("rasmus").implies("male"));

        final Optional<ReasoningProblem> s1 = solver.solve(new ReasoningProblem(kb, p("rasmus").implies("human")));
        assertTrue(s1.isPresent());

        final Optional<ReasoningProblem> s2 = solver.solve(new ReasoningProblem(kb, p("human").implies("rasmus")));
        assertFalse(s2.isPresent());
    }

}