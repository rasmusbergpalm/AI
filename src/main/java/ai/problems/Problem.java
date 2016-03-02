package ai.problems;

import com.google.common.base.Optional;

import java.util.Comparator;
import java.util.Set;

public interface Problem<T extends Problem> {

    Comparator<Problem> COST_COMPARATOR = new Comparator<Problem>() {
        @Override
        public int compare(final Problem o1, final Problem o2) {
            return Double.compare(o1.getCost() + o1.getHeuristicCost(), o2.getCost() + o2.getHeuristicCost());
        }
    };

    /**
     * Cost to get to current node. Lower is better.
     */
    double getCost();

    /**
     * Estimated cost from the current state to goal. Lower is better. Has to be smaller than or equal to the actual cost to the goal.
     */
    double getHeuristicCost();

    /**
     * Whether the problem is solved or not.
     */
    boolean isSolved();

    /**
     * Returns all possible successor problems.
     */
    Set<T> getSuccessors();

    /**
     * Return the parent problem, or absent if no parent exists.
     */
    Optional<T> getParent();

    /**
     * The state of the problem in string form.
     */
    String getState();
}
