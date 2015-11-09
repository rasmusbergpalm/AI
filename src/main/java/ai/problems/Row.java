package ai.problems;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

public class Row implements Predicate<ImmutableList<Integer>> {
    private final ImmutableList<Integer> attempt;
    private final int rightColor;
    private final int rightColorAndPosition;

    public Row(final ImmutableList<Integer> attempt, final ImmutableList<Integer> goal) {
        this.attempt = attempt;
        final int[] score = score(attempt, goal);
        rightColorAndPosition = score[0];
        rightColor = score[1];
    }

    public ImmutableList<Integer> getAttempt() {
        return attempt;
    }

    public int getRightColor() {
        return rightColor;
    }

    public int getRightColorAndPosition() {
        return rightColorAndPosition;
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < rightColorAndPosition; i++) {
            out += "x";
        }
        for (int i = 0; i < rightColor; i++) {
            out += "o";
        }
        for (int i = 0; i < (MasterMindProblem.POSITIONS - (rightColor + rightColorAndPosition)); i++) {
            out += "-";
        }
        return out + "\t" + Joiner.on(" ").join(attempt);
    }

    /**
     * Given the evidence we have by this attempt, is the given solution possible?
     */
    public boolean isPossible(final ImmutableList<Integer> newAttempt) {
        final int[] score = score(newAttempt, attempt);

        if (score[0] != rightColorAndPosition) {
            return false;
        }

        if (score[1] != rightColor) {
            return false;
        }

        return true;
    }

    private int[] score(final ImmutableList<Integer> newAttempt, final ImmutableList<Integer> reference) {
        final int[] score = new int[2];
        final List<Integer> restAtt = Lists.newArrayList();
        final List<Integer> restRef = Lists.newArrayList();
        for (int i = 0; i < newAttempt.size(); i++) {
            final Integer c1 = newAttempt.get(i);
            final Integer c2 = reference.get(i);
            if (c1.equals(c2)) {
                score[0]++;
            } else {
                restAtt.add(c1);
                restRef.add(c2);
            }
        }
        for (int i = 0; i < restAtt.size(); i++) {
            if (restRef.remove(restAtt.get(i))) {
                score[1]++;
            }
        }
        return score;
    }

    /**
     * Given the evidence we have by this attempt, is the given attempt possible?
     */
    @Override
    public boolean apply(final ImmutableList<Integer> t) {
        return isPossible(t);
    }
}
