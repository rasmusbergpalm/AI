package ai.problems;

import ai.problems.RouteProblem.CityMap.Intersection;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.ImmutableTable.Builder;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RouteProblem implements Problem<RouteProblem> {

    private final CityMap map;
    private final String position;
    private final String goal;
    private final double cost;
    private final Optional<String> streetTaken;
    private final Optional<RouteProblem> parent;

    public RouteProblem(final CityMap map, final Intersection start, final Intersection goal) {
        this.map = Preconditions.checkNotNull(map);
        Preconditions.checkNotNull(start);
        Preconditions.checkNotNull(goal);
        position = map.find(start);
        this.goal = map.find(goal);
        cost = 0;
        streetTaken = Optional.absent();
        parent = Optional.absent();
    }

    private RouteProblem(final CityMap map, final String position, final String goal, final double cost, final String streetTaken, final RouteProblem parent) {
        this.map = map;
        this.position = position;
        this.goal = goal;
        this.cost = cost;
        this.streetTaken = Optional.of(streetTaken);
        this.parent = Optional.of(parent);
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public boolean isSolved() {
        return position.equals(goal);
    }

    @Override
    public Set<RouteProblem> getSuccessors() {
        final ImmutableSet.Builder<RouteProblem> builder = ImmutableSet.builder();
        final Map<String, String> neighbours = map.neighbours(position);
        for (final Entry<String, String> neighbour : neighbours.entrySet()) {
            final String destination = neighbour.getKey();
            final String street = neighbour.getValue();
            builder.add(
                new RouteProblem(
                    map, destination, goal, cost + getDistance(position, destination), street, this
                )
            );
        }

        return builder.build();
    }

    private double getDistance(final String pos1, final String pos2) {
        final String[] s1 = pos1.split(",");
        final String[] s2 = pos2.split(",");
        return Math.sqrt(
            Math.pow(Double.valueOf(s1[0]) - Double.valueOf(s2[0]), 2) +
                Math.pow(Double.valueOf(s1[1]) - Double.valueOf(s2[1]), 2)
        );
    }

    @Override
    public Optional<RouteProblem> getParent() {
        return parent;
    }

    @Override
    public String getState() {
        return position;
    }

    public Optional<String> getStreetTaken() {
        return streetTaken;
    }

    @Override
    public double getHeuristicCost() {
        return getDistance(position, goal);
    }

    public static class CityMap {

        private final ImmutableTable<String, String, String> map;
        private final ImmutableMultimap<String, String> streetPositions;

        private CityMap(final ImmutableTable<String, String, String> map, final ImmutableMultimap<String, String> streetPositions) {
            this.map = map;
            this.streetPositions = streetPositions;
        }

        public static CityMap from(final Path file) throws IOException {
            final List<String> lines = Files.readAllLines(file, Charset.forName("UTF-8"));
            final Builder<String, String, String> mapBuilder = ImmutableTable.builder();
            final ImmutableMultimap.Builder<String, String> streetBuilder = ImmutableMultimap.builder();
            for (final String line : lines) {
                final String[] parts = line.split(" ");
                final String from = parts[0] + "," + parts[1];
                final String label = parts[2];
                final String to = parts[3] + "," + parts[4];
                mapBuilder.put(from, to, label);
                streetBuilder.putAll(label, from, to);
            }
            return new CityMap(mapBuilder.build(), streetBuilder.build());
        }

        public ImmutableMap<String, String> neighbours(final String position) {
            return map.row(position);
        }

        public String find(final Intersection intersection) {
            final String s1 = intersection.getStreet1();
            final String s2 = intersection.getStreet2();
            Preconditions.checkArgument(streetPositions.containsKey(s1), "No street named %s in map", s1);
            Preconditions.checkArgument(streetPositions.containsKey(s2), "No street named %s in map", s2);
            final Set<String> intersections = Sets.intersection(
                Sets.newHashSet(streetPositions.get(s1)),
                Sets.newHashSet(streetPositions.get(s2))
            );

            Preconditions.checkArgument(
                intersections.size() == 1,
                "Expected 1 intersection of streets %s and %s, found: ",
                s1,
                s2,
                intersections.size()
            );

            return intersections.iterator().next();
        }

        public static class Intersection {
            private final String street1;
            private final String street2;

            public static Intersection of(final String street1, final String street2) {
                return new Intersection(street1, street2);
            }

            private Intersection(final String street1, final String street2) {
                this.street1 = street1;
                this.street2 = street2;
            }

            public String getStreet1() {
                return street1;
            }

            public String getStreet2() {
                return street2;
            }
        }

    }
}
