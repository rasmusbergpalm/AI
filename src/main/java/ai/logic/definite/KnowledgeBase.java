package ai.logic.definite;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class KnowledgeBase {

    private final ImmutableMultimap<String, List<String>> kb;

    public static KnowledgeBase empty() {
        return new KnowledgeBase();
    }

    private KnowledgeBase() {
        this(ImmutableMultimap.<String, List<String>>of());
    }

    private KnowledgeBase(final ImmutableMultimap<String, List<String>> kb) {
        this.kb = kb;
    }

    public KnowledgeBase add(final String head, final String... body) {
        return new KnowledgeBase(
            ImmutableMultimap.<String, List<String>>builder()
                .putAll(kb)
                .put(head, ImmutableList.copyOf(body))
                .build()
        );
    }

    public Collection<List<String>> get(final String statement) {
        return kb.get(statement);
    }

    public boolean containsKey(final String statement) {
        return kb.containsKey(statement);
    }

    public Set<String> keySet() {
        return kb.keySet();
    }
}
