package ai.optimizers;

import ai.problems.Problem;

public interface Optimizer {

    /**
     * Optimize the problem
     *
     * @return
     * @param initial
     */
    <T extends Problem<T>> T optimize(T initial);
}
