package ai.logic.definite;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class ReasonerTest {

    private Reasoner reasoner;

    abstract Reasoner getReasoner();

    @Before
    public void setup() {
        reasoner = getReasoner();
    }

    @Test
    public void empty_rules_are_true() {
        final KnowledgeBase kb = KnowledgeBase.empty()
            .add("breakfast");

        assertTrue(reasoner.isTrue(kb, "breakfast"));
    }

    @Test
    public void unspecified_concepts_are_false() {
        final KnowledgeBase kb = KnowledgeBase.empty()
            .add("foo");

        assertFalse(reasoner.isTrue(kb, "bar"));
    }

    @Test
    public void concepts_with_one_rule_can_be_evaluated() {
        KnowledgeBase kb = KnowledgeBase.empty()
            .add("breakfast", "hotdrink", "food")
            .add("hotdrink");

        assertFalse(reasoner.isTrue(kb, "breakfast"));

        kb = kb.add("food");

        assertTrue(reasoner.isTrue(kb, "breakfast"));
    }

    @Test
    public void concepts_with_multiple_rules_can_be_evaluated() {
        KnowledgeBase kb = KnowledgeBase.empty()
            .add("breakfast", "hotdrink", "food")
            .add("breakfast", "juice", "food")
            .add("food");

        assertFalse(reasoner.isTrue(kb, "breakfast"));

        kb = kb.add("juice");

        assertTrue(reasoner.isTrue(kb, "breakfast"));
    }

    @Test
    public void deep_statements_can_be_evaluated() {
        KnowledgeBase kb = KnowledgeBase.empty()
            .add("breakfast", "hotdrink", "food")
            .add("hotdrink", "tea")
            .add("hotdrink", "coffee")
            .add("food");

        assertFalse(reasoner.isTrue(kb, "breakfast"));

        kb = kb.add("tea");

        assertTrue(reasoner.isTrue(kb, "breakfast"));
    }

    @Test
    public void rules_with_repetitions_are_handled() {
        KnowledgeBase kb = KnowledgeBase.empty()
            .add("breakfast", "juice", "food", "food", "food", "juice")
            .add("food", "egg")
            .add("egg")
            .add("juice");

        assertTrue(reasoner.isTrue(kb, "breakfast"));
    }

    @Test
    public void unsolveable_recursive_definitions_are_false() {
        KnowledgeBase kb = KnowledgeBase.empty()
            .add("breakfast", "breakfast");

        assertFalse(reasoner.isTrue(kb, "breakfast"));
    }

    @Test
    public void solvable_recursive_definitions_are_true() {
        final KnowledgeBase kb = KnowledgeBase.empty()
            .add("breakfast", "breakfast")
            .add("breakfast", "food")
            .add("food");

        assertTrue(reasoner.isTrue(kb, "breakfast"));
    }

}