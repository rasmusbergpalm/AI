package ai.reasoning;

import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class BottomUpReasoner implements Reasoner {

    @Override
    public boolean isTrue(final KnowledgeBase kb, final String statement) {
        final Set<String> facts = Sets.newHashSet();
        Set<String> newFacts;
        do {
            if (facts.contains(statement)) {
                return true;
            }
            newFacts = Sets.newHashSet();
            final Set<String> unchecked = Sets.newHashSet(kb.keySet());
            unchecked.removeAll(facts);
            for (final String key : unchecked) {
                final Collection<List<String>> conditions = kb.get(key);
                for (final List<String> condition : conditions) {
                    if (facts.containsAll(condition)) {
                        newFacts.add(key);
                        break;
                    }
                }
            }
            facts.addAll(newFacts);
        } while (!newFacts.isEmpty());

        return false;
    }
}
