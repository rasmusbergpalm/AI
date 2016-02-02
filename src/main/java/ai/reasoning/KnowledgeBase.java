package ai.reasoning;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;

public class KnowledgeBase {

    private final Multimap<String, String[]> knowledgeBase = ArrayListMultimap.create();

    public void put(final String head, final String... body) {
        knowledgeBase.put(head, body);
    }

    public boolean isTrue(final String query) {
        if (!knowledgeBase.containsKey(query)) {
            return false;
        }
        final Collection<String[]> rules = knowledgeBase.get(query);
        for (final String[] rule : rules) {
            if (rule.length == 0) {
                return true;
            }
            boolean isTrue = true;
            for (final String concept : rule) {
                if (!isTrue(concept)) {
                    isTrue = false;
                    break;
                }
            }
            if (isTrue) {
                return true;
            }
        }
        return false;
    }

}
