package ai.logic.propositional;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * A clause is a disjunction of a set of positive and negative literals.
 * e.g. "A or B or (not C) or D"
 */
public class Clause {
    private final ImmutableSet<String> positive;
    private final ImmutableSet<String> negative;
    public static final Predicate<Clause> TAUTOLOGY_FILTER = new Predicate<Clause>() {
        @Override
        public boolean apply(final Clause input) {
            return !input.isTautology();
        }
    };

    /**
     * @param positive A space separated string of literals, e.g. "A B C"
     * @param negative A space separated string of negative literals, e.g. "D E"
     *                 <p/>
     *                 The values above would result in the clause "A or B or C or (not D) or (not E)"
     * @return the clause
     */
    public static Clause of(final String positive, final String negative) {
        final String ptrim = positive.trim();
        final String ntrim = negative.trim();
        return new Clause(
                ptrim.isEmpty() ? ImmutableSet.<String>of() : ImmutableSet.copyOf(ptrim.split(" +")),
                ntrim.isEmpty() ? ImmutableSet.<String>of() : ImmutableSet.copyOf(ntrim.split(" +"))
        );
    }

    public Clause or(final Clause c2) {
        return new Clause(
                Sets.union(positive, c2.getPositive()).immutableCopy(),
                Sets.union(negative, c2.getNegative()).immutableCopy()
        );
    }

    public static Clause p(final String positive) {
        return of(positive, "");
    }

    public static Clause n(final String negative) {
        return of("", negative);
    }

    private Clause(final ImmutableSet<String> positive, final ImmutableSet<String> negative) {
        this.positive = positive;
        this.negative = negative;
    }

    public boolean isContradiction() {
        return positive.isEmpty() && negative.isEmpty();
    }

    public ImmutableSet<String> getPositive() {
        return positive;
    }

    public ImmutableSet<String> getNegative() {
        return negative;
    }

    public ImmutableSet<Clause> resolve(final Clause that) {
        final Builder<Clause> builder = ImmutableSet.builder();
        for (final String s : Sets.intersection(this.getPositive(), that.getNegative())) {
            final Set<String> literal = ImmutableSet.of(s);
            builder.add(
                    new Clause(
                            Sets.union(Sets.difference(this.getPositive(), literal), that.getPositive()).immutableCopy(),
                            Sets.union(Sets.difference(that.getNegative(), literal), this.getNegative()).immutableCopy()
                    ));
        }
        for (final String s : Sets.intersection(this.getNegative(), that.getPositive())) {
            final Set<String> literal = ImmutableSet.of(s);
            builder.add(
                    new Clause(
                            Sets.union(Sets.difference(that.getPositive(), literal), this.getPositive()).immutableCopy(),
                            Sets.union(Sets.difference(this.getNegative(), literal), that.getNegative()).immutableCopy()
                    ));
        }

        return ImmutableSet.copyOf(Sets.filter(builder.build(), TAUTOLOGY_FILTER));
    }

    public boolean isTautology() {
        return !Sets.intersection(positive, negative).isEmpty();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Clause clause = (Clause) o;

        if (!negative.equals(clause.negative)) {
            return false;
        }
        return positive.equals(clause.positive);

    }

    @Override
    public int hashCode() {
        int result = positive.hashCode();
        result = 31 * result + negative.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final Set<String> all = Sets.newTreeSet(positive);
        for (final String s : negative) {
            all.add("!" + s);
        }
        return "[" + Joiner.on(" or ").join(all) + "]";
    }

}
