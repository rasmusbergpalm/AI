package ai.problems;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.util.Random;
import java.util.Set;

public class QueensProblem implements Problem {

    private int[] state = new int[8];

    public QueensProblem(Random random) {
        int[] state = new int[8];
        for (int i = 0; i < 8; i++) {
            state[i] = random.nextInt(8);
        }
        this.state = state;
    }

    public QueensProblem(int[] state) {
        Preconditions.checkArgument(state.length == 8);
        this.state = state;
    }

    public int[] getState() {
        return state;
    }

    @Override
    public double getScore() {
        double score = 0;
        for (int i = 0; i < 8; i++) {
            score += getScore(i, state[i]);
        }
        return -score;
    }

    @Override
    public Set<Problem> getSuccessors() {
        Set<Problem> successors = Sets.newHashSet();
        for (int i = 0; i < 8; i++) {
            for (int u = 0; u < 8; u++) {
                if (state[i] == u) continue;
                int[] newState = new int[8];
                System.arraycopy(state, 0, newState, 0, state.length);
                newState[i] = u;
                successors.add(new QueensProblem(newState));
            }
        }
        return successors;
    }

    private int getScore(int column, int row) {
        int score = 0;
        for (int i = 0; i < 8; i++) {
            if (i == column) continue;
            int dist = Math.abs(i - column);

            if (state[i] == row) score++;
            if (state[i] - dist == row) score++;
            if (state[i] + dist == row) score++;
        }

        return score;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Score: ").append(getScore()).append("\n");
        for (int i = 7; i >= 0; i--) {
            for (int u = 0; u < 8; u++) {
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
