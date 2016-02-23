package ai.problems;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class MasterMindProblem implements Problem<MasterMindProblem> {

    public static final int POSITIONS = 4;
    private static final ImmutableMap<Integer, String> COLORS = ImmutableMap.<Integer, String>builder()
        .put(0, "RED")
        .put(1, "GREEN")
        .put(2, "BLUE")
        .put(3, "BLACK")
        .put(4, "YELLOW")
        .put(5, "WHITE")
        .build();

    private static final Generator<Integer> ALL = Factory.createPermutationWithRepetitionGenerator(
        Factory.createVector(COLORS.keySet()),
        POSITIONS
    );

    private final ImmutableList<Integer> goal;
    private final ImmutableList<Row> rows;
    private final Optional<MasterMindProblem> parent;

    public MasterMindProblem(final ImmutableList<Integer> goal, final ImmutableList<Row> rows, final MasterMindProblem parent) {
        this.goal = goal;
        this.rows = rows;
        this.parent = Optional.of(parent);
    }

    public MasterMindProblem(final Random random) {
        rows = ImmutableList.of();
        final Builder<Integer> goalBuilder = ImmutableList.builder();
        for (int i = 0; i < POSITIONS; i++) {
            goalBuilder.add(random.nextInt(COLORS.size()));
        }
        goal = goalBuilder.build();
        parent = Optional.absent();
    }

    @Override
    public boolean isSolved() {
        return !rows.isEmpty() && rows.reverse().get(0).getAttempt().equals(goal);
    }

    public ImmutableList<Row> getRows() {
        return rows;
    }

    @Override
    public double getCost() {
        return 0;
    }

    @Override
    public Set<MasterMindProblem> getSuccessors() {
        final Set<MasterMindProblem> successors = Sets.newHashSet();
        for (final ICombinatoricsVector<Integer> move : ALL) {
            final ImmutableList<Integer> attempt = ImmutableList.copyOf(move.getVector());
            if (isPossible(attempt)) {
                successors.add(
                    new MasterMindProblem(
                        goal,
                        ImmutableList.<Row>builder()
                            .addAll(rows)
                            .add(new Row(attempt, goal))
                            .build(),
                        this
                    )
                );
            }
        }
        return successors;
    }

    @Override
    public Optional<MasterMindProblem> getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return
            "Goal:\t" + Joiner.on(" ").join(goal) + "\n"
                + "Score: " + getCost() + "\n\n"
                + Joiner.on("\n").join(rows.reverse()) + "\n";
    }

    private boolean isPossible(final ImmutableList<Integer> attempt) {
        for (final Row prev : rows) {
            if (!prev.isPossible(attempt)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MasterMindProblem that = (MasterMindProblem) o;

        if (!goal.equals(that.goal)) {
            return false;
        }
        if (!rows.equals(that.rows)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = goal.hashCode();
        result = 31 * result + rows.hashCode();
        return result;
    }

    public static class Row implements Predicate<ImmutableList<Integer>> {
        private final ImmutableList<Integer> attempt;
        private final int rightColor;
        private final int rightColorAndPosition;

        public Row(final ImmutableList<Integer> attempt, final ImmutableList<Integer> goal) {
            this.attempt = Preconditions.checkNotNull(attempt);
            Preconditions.checkNotNull(goal);
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
            for (int i = 0; i < (POSITIONS - (rightColor + rightColorAndPosition)); i++) {
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

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Row row = (Row) o;

            if (rightColor != row.rightColor) {
                return false;
            }
            if (rightColorAndPosition != row.rightColorAndPosition) {
                return false;
            }
            if (!attempt.equals(row.attempt)) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = attempt.hashCode();
            result = 31 * result + rightColor;
            result = 31 * result + rightColorAndPosition;
            return result;
        }
    }
}
