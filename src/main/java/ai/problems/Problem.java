package ai.problems;

import java.util.Comparator;
import java.util.Set;

public interface Problem<T extends Problem> {

    Comparator<Problem> COMPARATOR = new Comparator<Problem>() {
        @Override
        public int compare(final Problem o1, final Problem o2) {
            return Double.compare(o2.getScore(), o1.getScore());
        }
    };

    /**
     * Higher is better
     */
    double getScore();

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
