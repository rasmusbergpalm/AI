package ai.optimizers;

import ai.problems.Problem;

public interface Optimizer {

    /**
     * Optimize the problem
     *
     * @return
     */
    Problem optimize(Problem problem);
}
