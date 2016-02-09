package ai.reasoning;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TopDownReasoner implements Reasoner {

    @Override
    public boolean isTrue(final KnowledgeBase kb, final String statement) {
        return new StateFullTopDownReasoner().isTrue(kb, statement);
    }

    private static class StateFullTopDownReasoner implements Reasoner {

        private final Set<String> asserting = Sets.newHashSet();
        private final Map<String, Boolean> cache = Maps.newHashMap();

        StateFullTopDownReasoner() {
        }

        @Override
        public boolean isTrue(final KnowledgeBase kb, final String statement) {
            asserting.add(statement);
            if (!kb.containsKey(statement)) {
                return answer(statement, false);
            }
            if (cache.containsKey(statement)) {
                return answer(statement, cache.get(statement));
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
                    return answer(statement, true);
                }
            }
            return answer(statement, false);
        }

        private boolean answer(final String statement, final boolean answer) {
            asserting.remove(statement);
            cache.put(statement, answer);
            return answer;
        }

    }

}
