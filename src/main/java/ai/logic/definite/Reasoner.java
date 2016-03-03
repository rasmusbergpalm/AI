package ai.logic.definite;

public interface Reasoner {

    boolean isTrue(final KnowledgeBase kb, final String statement);
}
