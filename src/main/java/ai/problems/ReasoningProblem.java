package ai.problems;

import ai.logic.propositional.CNF;
import ai.logic.propositional.Clause;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;

import java.util.Set;

import static ai.logic.propositional.CNF.not;

public class ReasoningProblem implements Problem<ReasoningProblem> {

    private final CNF knowledgeBase;
    private final Clause state;
    private final Optional<ReasoningProblem> parent;
    private final boolean initial;

    public ReasoningProblem(final CNF knowledgeBase, final CNF hypothesis) {
        this.knowledgeBase = knowledgeBase.and(not(hypothesis));
        this.parent = Optional.absent();
        this.state = null;
        this.initial = true;
    }

    private ReasoningProblem(final CNF knowledgeBase, final Clause state, final ReasoningProblem parent) {
        this.knowledgeBase = knowledgeBase;
        this.parent = Optional.of(parent);
        this.state = state;
        this.initial = false;
    }

    @Override
    public double getCost() {
        if (parent.isPresent()) {
            return parent.get().getCost() + 1;
        }
        return 0;
    }

    @Override
    public double getHeuristicCost() {
        return initial ? 0 : state.getNegative().size() + state.getPositive().size();
    }

    @Override
    public boolean isSolved() {
        return !initial && state.isContradiction();
    }

    @Override
    public Set<ReasoningProblem> getSuccessors() {
        final Set<ReasoningProblem> successors = Sets.newHashSet();
        if (initial) {
            for (final Clause clause : knowledgeBase.getClauses()) {
                successors.add(new ReasoningProblem(knowledgeBase, clause, this));
            }
            return successors;
        }
        for (final Clause clause : knowledgeBase.getClauses()) {
            for (final Clause resolvent : state.resolve(clause)) {
                successors.add(new ReasoningProblem(knowledgeBase.and(state), resolvent, this));
            }
        }

        return successors;
    }

    @Override
    public Optional<ReasoningProblem> getParent() {
        return parent;
    }

    @Override
    public String getState() {
        return initial ? "START" : state.toString();
    }
}
