package ai.problems;

import java.util.Comparator;
import java.util.Set;

public interface Problem<T extends Problem> {

    Comparator<Problem> COST_COMPARATOR = new Comparator<Problem>() {
        @Override
        public int compare(final Problem o1, final Problem o2) {
            return Double.compare(o1.getCost(), o2.getCost());
        }
    };

    /**
     * Lower is better
     */
    double getCost();

    /**
     * Whether the problem is solved or not
     */
    boolean isSolved();

    /**
     * Returns all possible successor states
     */
    Set<T> getSuccessors();

    /**
     * Textual representations
     */
    String toString();
}
