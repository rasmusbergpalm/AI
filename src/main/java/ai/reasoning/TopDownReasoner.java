package ai.reasoning;

import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class TopDownReasoner implements Reasoner {

    private final Set<String> asserting = Sets.newHashSet(); // TODO make threadsafe.

    @Override
    public boolean isTrue(final KnowledgeBase kb, final String statement) {
        asserting.add(statement);
        if (!kb.containsKey(statement)) {
            asserting.remove(statement);
            return false;
        }
        final Collection<List<String>> rules = kb.get(statement);
        for (final List<String> rule : rules) {
            boolean isTrue = true;
            for (final String subStatement : rule) {
                if (asserting.contains(subStatement) || !isTrue(kb, subStatement)) {
                    isTrue = false;
                    break;
                }
            }
            if (isTrue) {
                asserting.remove(statement);
                return true;
            }
        }
        asserting.remove(statement);
        return false;
    }

}
