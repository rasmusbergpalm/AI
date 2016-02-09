package ai.reasoning;

public interface Reasoner {

    boolean isTrue(final KnowledgeBase kb, final String statement);
}
