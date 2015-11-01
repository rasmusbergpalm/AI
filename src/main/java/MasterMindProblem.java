import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.util.Random;
import java.util.Set;

public class MasterMindProblem implements Problem {

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

    public MasterMindProblem(final ImmutableList<Integer> goal, final ImmutableList<Row> attempts, final ImmutableList<Integer> attempt) {
        this.goal = goal;
        this.rows = ImmutableList.<Row>builder()
            .addAll(attempts)
            .add(new Row(attempt, goal))
            .build();
    }

    public MasterMindProblem(final Random random) {
        rows = ImmutableList.of();
        final Builder<Integer> goalBuilder = ImmutableList.builder();
        for (int i = 0; i < POSITIONS; i++) {
            goalBuilder.add(random.nextInt(COLORS.size()));
        }
        goal = goalBuilder.build();

    }

    public boolean isSolved() {
        return rows.reverse().get(0).getAttempt().equals(goal);
    }

    public ImmutableList<Row> getRows() {
        return rows;
    }

    public ImmutableList<Integer> getGoal() {
        return goal;
    }

    @Override
    public double getScore() {
        return 0; //TODO
    }

    @Override
    public Set<Problem> getSuccessors() {
        final Set<Problem> successors = Sets.newHashSet();
        for (final ICombinatoricsVector<Integer> move : ALL) {
            final ImmutableList<Integer> attempt = ImmutableList.copyOf(move.getVector());
            if (isPossible(attempt)) {
                successors.add(new MasterMindProblem(goal, rows, attempt));
            }
        }
        return successors;
    }

    @Override
    public String toString() {
        return
            "Goal:\t" + Joiner.on(" ").join(goal) + "\n"
                + "Score: " + getScore() + "\n\n"
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

}
