package ai.logic.propositional;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;

/**
 * A CNF is a conjunction of clauses, e.g. C1 and C2 and C3
 */
public class CNF {

    private final ImmutableSet<Clause> clauses;

    private CNF(final ImmutableSet<Clause> clauses) {
        this.clauses = clauses;
    }

    private static CNF empty() {
        return new CNF(ImmutableSet.<Clause>of());
    }

    public static CNF n(final String s) {
        return empty().and(Clause.n(s));
    }

    public static CNF p(final String s) {
        return empty().and(Clause.p(s));
    }

    public static CNF not(final CNF cnf) {
        final UnmodifiableIterator<Clause> iterator = cnf.getClauses().iterator();
        CNF result = not(iterator.next());
        while (iterator.hasNext()) {
            result = result.or(not(iterator.next()));
        }
        return result;
    }

    public static CNF not(final Clause clause) {
        CNF result = empty();
        for (final String negative : clause.getNegative()) {
            result = result.and(p(negative));
        }
        for (final String positive : clause.getPositive()) {
            result = result.and(n(positive));
        }
        return result;
    }

    public CNF and(final Clause clause) {
        if (clause.isTautology()) {
            return this;
        } else {
            return new CNF(
                    ImmutableSet.<Clause>builder()
                            .addAll(clauses)
                            .add(clause)
                            .build()
            );
        }
    }

    public CNF and(final CNF that) {
        CNF result = this;
        for (final Clause clause : that.getClauses()) {
            result = result.and(clause);
        }
        return result;
    }

    public CNF and(final String s) {
        return and(p(s));
    }

    public CNF or(final CNF that) {
        CNF result = empty();
        for (final Clause c1 : clauses) {
            for (final Clause c2 : that.getClauses()) {
                result = result.and(c1.or(c2));
            }

        }
        return result;
    }

    public CNF or(final String s) {
        return or(p(s));
    }

    public CNF implies(final CNF that) {
        return not(this).or(that);
    }

    public CNF implies(final String s) {
        return implies(p(s));
    }

    public CNF iff(final CNF that) {
        return this.implies(that).and(that.implies(this));
    }

    public CNF iff(final String s) {
        return iff(p(s));
    }

    public ImmutableSet<Clause> getClauses() {
        return clauses;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final CNF cnf = (CNF) o;

        if (!clauses.equals(cnf.clauses)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return clauses.hashCode();
    }

    @Override
    public String toString() {
        return Joiner.on(" and ").join(clauses);
    }
}
