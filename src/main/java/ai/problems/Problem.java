package ai.problems;

import java.util.Comparator;
import java.util.Set;

public interface Problem {

    Comparator<Problem> COMPARATOR = new Comparator<Problem>() {
        @Override
        public int compare(Problem a, Problem b) {
            return Double.compare(b.getScore(), a.getScore());
        }
    };


    /**
     * Higher is better
     */
    double getScore();

    /**
     * Returns all possible succesor states
     */
    Set<Problem> getSuccessors();

    /**
     * Textual representations
     */
    String toString();
}
