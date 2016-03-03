package ai.logic.propositional;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import static ai.logic.propositional.CNF.n;
import static ai.logic.propositional.CNF.not;
import static ai.logic.propositional.CNF.p;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CNFTest {

    @Test
    public void test_n() {
        CNF cnf = n("A");
        assertEquals(ImmutableSet.of(Clause.n("A")), cnf.getClauses());

        cnf = n("A B");
        assertEquals(ImmutableSet.of(Clause.n("A B")), cnf.getClauses());
    }

    @Test
    public void test_p() {
        CNF cnf = p("A");
        assertEquals(ImmutableSet.of(Clause.p("A")), cnf.getClauses());

        cnf = p("A B");
        assertEquals(ImmutableSet.of(Clause.p("A B")), cnf.getClauses());
    }

    @Test
    public void test_clause_not() {
        assertEquals(n("a"), not(Clause.p("a")));
        assertEquals(p("a"), not(not(Clause.p("a"))));

        //!(a or b or !c)
        //!a and !b and c
        final CNF cnf = not(Clause.of("a b", "c"));
        assertEquals(
            ImmutableSet.of(Clause.n("a"), Clause.n("b"), Clause.p("c")), cnf.getClauses()
        );
    }

    @Test
    public void test_cnf_not() {
        assertEquals(p("a"), not(not(p("a"))));

        CNF cnf = not(p("a"));
        assertEquals(ImmutableSet.of(Clause.n("a")), cnf.getClauses());

        cnf = not(n("a"));
        assertEquals(ImmutableSet.of(Clause.p("a")), cnf.getClauses());

        cnf = not(p("a b"));
        assertEquals(ImmutableSet.of(Clause.n("a"), Clause.n("b")), cnf.getClauses());

        cnf = not(p("a").and("b"));
        assertEquals(
            ImmutableSet.of(Clause.n("a b")),
            cnf.getClauses()
        );

        //!(a and (b or !c))
        //((NOT a) OR  (NOT b)) AND ((NOT a) OR  c)
        cnf = not(p("a").and(p("b").or(n("c"))));
        assertEquals(
            ImmutableSet.of(
                Clause.n("a b"),
                Clause.of("c", "a")
            ),
            cnf.getClauses()
        );
    }

    @Test
    public void test_and() {
        CNF cnf = p("A").and("B");
        assertEquals(
            ImmutableSet.of(Clause.p("A"), Clause.p("B")),
            cnf.getClauses()
        );

        assertEquals(p("A").and("B"), p("B").and("A"));

        assertEquals(p("A").and("B").and("C"), p("A").and(p("B").and("C")));

        cnf = p("A").and("B").and("C");
        assertEquals(ImmutableSet.of(Clause.p("A"), Clause.p("B"), Clause.p("C")), cnf.getClauses());

        cnf = p("A").and("B").and(n("C"));
        assertEquals(ImmutableSet.of(Clause.p("A"), Clause.p("B"), Clause.n("C")), cnf.getClauses());

        cnf = p("A").and(p("B").and("D")).and(n("C"));
        assertEquals(ImmutableSet.of(Clause.p("A"), Clause.p("B"), Clause.p("D"), Clause.n("C")), cnf.getClauses());

        //A and ((B or F) and (D or E)) and !C
        //A and (B or F) and (D or E) and !C
        cnf = p("A").and(p("B F").and("D E")).and(n("C"));
        assertEquals(ImmutableSet.of(Clause.p("A"), Clause.p("B F"), Clause.p("D E"), Clause.n("C")), cnf.getClauses());
    }

    @Test
    public void test_or() {
        CNF cnf = p("A").or("B");
        assertEquals(
            ImmutableSet.of(Clause.p("A B")),
            cnf.getClauses()
        );

        assertEquals(p("A").or("B"), p("B").or("A"));

        assertEquals(p("A").or("B").or("C"), p("A").or(p("B").or("C")));

        //(A and B) or (C and D)
        //(A or C) and (B or C) and (A or D) and (B or D)
        cnf = p("A").and("B").or(p("C").and("D"));
        assertEquals(
            ImmutableSet.of(Clause.p("A C"), Clause.p("B C"), Clause.p("A D"), Clause.p("B D")),
            cnf.getClauses()
        );

        //(A and (not B)) or (C and D)
        //(A or C) and ((not B) or C) and (A or D) and ((not B) or D)
        cnf = p("A").and(n("B")).or(p("C").and("D"));
        assertEquals(
            ImmutableSet.of(Clause.p("A C"), Clause.of("C", "B"), Clause.p("A D"), Clause.of("D", "B")),
            cnf.getClauses()
        );

        // (a and b) or (c and !d) or (e and (f or g))
        // solution from http://www.wolframalpha.com/input/?i=(a+and+b)+or+(c+and+!d)+or+(e+and+(f+or+g))
        cnf = p("a").and("b").or(p("c").and(n("d"))).or(p("e").and(p("f").or("g")));
        assertEquals(
            ImmutableSet.of(
                Clause.p("a c e"), // (a OR  c OR  e) AND
                Clause.p("a c f g"), // (a OR  c OR  f OR  g) AND
                Clause.of("a e", "d"), // (a OR  (NOT d) OR  e) AND
                Clause.of("a f g", "d"), // (a OR  (NOT d) OR  f OR  g) AND
                Clause.p("b c e"), // (b OR  c OR  e) AND
                Clause.p("b c f g"), // (b OR  c OR  f OR  g) AND
                Clause.of("b e", "d"), // (b OR  (NOT d) OR  e) AND
                Clause.of("b f g", "d") // (b OR  (NOT d) OR  f OR  g)
            ),
            cnf.getClauses()
        );
    }

    @Test
    public void test_implies() {
        assertEquals(ImmutableSet.of(Clause.of("B", "A")), p("A").implies("B").getClauses());
        assertEquals(p("A").implies("B"), n("B").implies(n("A")));

        assertEquals(p("p").implies("q"), n("p").or(p("q")));
        assertEquals(p("p").implies("q"), n("q").implies(n("p")));
        assertEquals(p("p").or("q"), n("p").implies("q"));
        assertEquals(p("p").and("q"), not(p("p").implies(n("q"))));
        assertEquals(not(p("p").implies("q")), p("p").and(n("q")));
        assertEquals(p("p").implies("q").and(p("p").implies("r")), p("p").implies(p("q").and("r")));
        assertEquals(p("p").implies("q").or(p("p").implies("r")), p("p").implies(p("q").or("r")));
        assertEquals(p("p").implies("r").and(p("q").implies("r")), p("p").or("q").implies("r"));
        assertEquals(p("p").implies("r").or(p("q").implies("r")), p("p").and("q").implies("r"));
    }

    @Test
    public void test_iff() {
        assertEquals(
            ImmutableSet.of(
                Clause.of("B", "A"),
                Clause.of("A", "B")
            ), // ((NOT a) OR  b) AND  (a OR  (NOT b))
            p("A").iff("B").getClauses()
        );

        assertEquals(p("A").iff("B"), p("B").iff("A"));
        assertEquals(p("A").implies("B").and(p("B").implies("A")), p("A").iff("B"));
        assertNotEquals(p("A").implies("B").and("B").implies("A"), p("A").iff("B"));

        assertEquals(n("p").iff(n("q")), p("p").iff("q"));
        assertEquals(p("p").and("q").or(n("p").and(n("q"))), p("p").iff("q"));
        assertEquals(not(p("p").iff("q")), p("p").iff(n("q")));
    }
}