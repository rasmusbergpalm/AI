package ai.problems;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;

import java.util.Random;
import java.util.Set;

public class QueensProblem implements Problem<QueensProblem> {

    private final int[] state;
    private final Optional<QueensProblem> parent;

    public QueensProblem(final Random random, final int numQueens) {
        final int[] state = new int[numQueens];
        for (int i = 0; i < numQueens; i++) {
            state[i] = random.nextInt(numQueens);
        }
        this.state = state;
        parent = Optional.absent();
    }

    public QueensProblem(final int[] state) {
        this.state = state;
        parent = Optional.absent();
    }

    public QueensProblem(final int[] state, final QueensProblem parent) {
        this.state = state;
        this.parent = Optional.of(parent);
    }

    @Override
    public double getCost() {
        double cost = 0;
        for (int i = 0; i < state.length; i++) {
            for (int i1 = 0; i1 < state.length; i1++) {
                if (i1 == i) {
                    continue;
                }
                final int dist = Math.abs(i1 - i);

                if (state[i1] == state[i]) {
                    cost++;
                }
                if (state[i1] - dist == state[i]) {
                    cost++;
                }
                if (state[i1] + dist == state[i]) {
                    cost++;
                }
            }
        }
        return cost;
    }

    @Override
    public double getHeuristicCost() {
        return 0;
    }

    @Override
    public boolean isSolved() {
        return getCost() == 0;
    }

    @Override
    public Set<QueensProblem> getSuccessors() {
        final Set<QueensProblem> successors = Sets.newHashSet();
        for (int i = 0; i < state.length; i++) {
            for (int u = 0; u < state.length; u++) {
                if (state[i] == u) {
                    continue;
                }
                final int[] newState = new int[state.length];
                System.arraycopy(state, 0, newState, 0, state.length);
                newState[i] = u;
                successors.add(new QueensProblem(newState, this));
            }
        }
        return successors;
    }

    @Override
    public Optional<QueensProblem> getParent() {
        return parent;
    }

    @Override
    public String getState() {
        final StringBuilder sb = new StringBuilder();
        for (int i = state.length - 1; i >= 0; i--) {
            for (int u = 0; u < state.length; u++) {
                if (state[u] == i) {
                    sb.append(" * ");
                } else {
                    sb.append(" - ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
