package ai.logic.propositional;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ClauseTest {

    @Test
    public void only_empty_clauses_are_contradictions() {
        assertTrue(Clause.of("", "").isContradiction());
        assertFalse(Clause.of("A", "").isContradiction());
        assertFalse(Clause.of("", "A").isContradiction());
        assertFalse(Clause.of("A", "B").isContradiction());
    }

    @Test
    public void test_p() {
        final Clause clause = Clause.p("A B C");
        assertEquals(ImmutableSet.of("A", "B", "C"), clause.getPositive());
        assertTrue(clause.getNegative().isEmpty());
    }

    @Test
    public void test_n() {
        final Clause clause = Clause.n("A B C");
        assertEquals(ImmutableSet.of("A", "B", "C"), clause.getNegative());
        assertTrue(clause.getPositive().isEmpty());
    }

    @Test
    public void test_or() {
        assertEquals(
                Clause.of("A C", "B D"),
                Clause.of("A", "B").or(Clause.of("C", "D"))
        );
    }

    @Test
    public void can_handle_extra_whitespace() {
        final Clause of = Clause.of("  A  ", "  B   C  ");
        assertEquals(ImmutableSet.of("A"), of.getPositive());
        assertEquals(ImmutableSet.of("B", "C"), of.getNegative());
    }

    @Test
    public void resolving_two_equal_clauses_returns_no_resolvents() {
        final Clause c1 = Clause.of("A C", "B D");
        final Clause c2 = Clause.of("A C", "B D");
        assertTrue(c1.resolve(c2).isEmpty());
        assertTrue(c2.resolve(c1).isEmpty());
    }

    @Test
    public void resolving_two_clauses_with_no_complementary_literals_returns_no_resolvents() {
        final Clause c1 = Clause.of("A C", "B D");
        final Clause c2 = Clause.of("E F", "G H");
        assertTrue(c1.resolve(c2).isEmpty());
        assertTrue(c2.resolve(c1).isEmpty());
    }

    @Test
    public void resolution_with_one_complementary_literals_results_in_one_resolvant() {
        final Clause c1 = Clause.of("A C", "B D");
        final Clause c2 = Clause.of("F G", "H C");

        final ImmutableSet<Clause> expected = ImmutableSet.of(Clause.of("A F G", "B D H"));
        assertEquals(expected, c1.resolve(c2));
        assertEquals(expected, c2.resolve(c1));
    }

    @Test
    public void resolving_clauses_with_multiple_complementary_literals_results_in_tautologies_that_are_filtered() {
        final Clause c1 = Clause.of("A C", "B D");
        final Clause c2 = Clause.of("F G", "A C K");

        assertEquals(ImmutableSet.<Clause>of(), c1.resolve(c2));
        assertEquals(ImmutableSet.<Clause>of(), c2.resolve(c1));
    }

    @Test
    public void resolving_clauses_can_result_in_the_empty_clause() {
        final Clause c1 = Clause.of("A", "");
        final Clause c2 = Clause.of("", "A");

        final ImmutableSet<Clause> expected = ImmutableSet.of(Clause.of("", ""));
        assertEquals(expected, c1.resolve(c2));
        assertEquals(expected, c2.resolve(c1));
    }

    @Test
    public void resolving_with_tautologies_is_ok() {
        assertEquals(
                ImmutableSet.of(Clause.of("A", "")),
                Clause.of("A", "A").resolve(Clause.of("A", ""))
        );
        assertEquals(
                ImmutableSet.of(Clause.of("", "A")),
                Clause.of("A", "A").resolve(Clause.of("", "A"))
        );
        assertEquals(
                ImmutableSet.of(Clause.of("A B", "")),
                Clause.of("A", "A").resolve(Clause.of("A B", ""))
        );
        assertEquals(
                ImmutableSet.of(),
                Clause.of("A", "A").resolve(Clause.of("A B", "A"))
        );
        assertEquals(
                ImmutableSet.of(),
                Clause.of("A", "A").resolve(Clause.of("A", "A"))
        );
    }

    @Test
    public void wumpus_book_examples() {
        final Clause c1 = Clause.of("B11", "P21");
        final Clause c2 = Clause.of("P12 P21", "B11");
        final Clause c3 = Clause.of("B11", "P12");
        final Clause c4 = Clause.of("", "B11");
        final Clause c5 = Clause.of("P12", "");

        assertEquals(ImmutableSet.of(), c1.resolve(c2));
        assertEquals(ImmutableSet.of(Clause.of("", "P21")), c1.resolve(c4));
        assertEquals(ImmutableSet.of(), c2.resolve(c3));
        assertEquals(ImmutableSet.of(Clause.of("", "P12")), c3.resolve(c4));
        assertEquals(ImmutableSet.of(Clause.of("", "")), Clause.of("", "P12").resolve(c5));
    }

}