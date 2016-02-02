package ai.reasoning;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class KnowledgeBaseTest {

    @Test
    public void empty_rules_are_true() {
        final KnowledgeBase kb = new KnowledgeBase();

        kb.put("breakfast");

        assertTrue(kb.isTrue("breakfast"));
    }

    @Test
    public void unspecified_concepts_are_false() {
        final KnowledgeBase kb = new KnowledgeBase();

        kb.put("foo");

        assertFalse(kb.isTrue("bar"));
    }

    @Test
    public void concepts_with_one_rule_can_be_evaluated() {
        final KnowledgeBase kb = new KnowledgeBase();

        kb.put("breakfast", "hotdrink", "food");
        kb.put("hotdrink");

        assertFalse(kb.isTrue("breakfast"));

        kb.put("food");

        assertTrue(kb.isTrue("breakfast"));
    }

    @Test
    public void concepts_with_multiple_rules_can_be_evaluated() {
        final KnowledgeBase kb = new KnowledgeBase();

        kb.put("breakfast", "hotdrink", "food");
        kb.put("breakfast", "juice", "food");
        kb.put("food");

        assertFalse(kb.isTrue("breakfast"));

        kb.put("juice");

        assertTrue(kb.isTrue("breakfast"));
    }

    @Test
    public void rules_can_be_recursively_evaluated() {
        final KnowledgeBase kb = new KnowledgeBase();

        kb.put("breakfast", "hotdrink", "food");
        kb.put("hotdrink", "tea");
        kb.put("hotdrink", "coffee");
        kb.put("food");

        assertFalse(kb.isTrue("breakfast"));

        kb.put("tea");

        assertTrue(kb.isTrue("breakfast"));
    }

    @Test
    @Ignore //TODO fix.
    public void self_referencing_rules_can_be_evaluated() {
        final KnowledgeBase kb = new KnowledgeBase();

        kb.put("breakfast", "breakfast");

        assertFalse(kb.isTrue("breakfast"));
    }
}