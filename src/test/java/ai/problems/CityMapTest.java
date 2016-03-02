package ai.problems;

import ai.problems.RouteProblem.CityMap;
import ai.problems.RouteProblem.CityMap.Intersection;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CityMapTest {

    private CityMap map;

    @Before
    public void setup() throws URISyntaxException, IOException {
        map = CityMap.from(
            Paths.get(
                CityMap.class.getClassLoader()
                    .getResource("manhattan.txt")
                    .toURI()
            )
        );
    }

    @Test
    public void can_find_neighbours_of_position() {
        Map<String, String> expected = ImmutableMap.<String, String>builder()
            .put("1,0", "street_0")
            .put("0,1", "avenue_0")
            .build();

        assertEquals(expected, map.neighbours("0,0"));
    }

    @Test
    public void can_find_intersections_of_streets() {
        final String intersection = map.find(Intersection.of("street_2", "avenue_4"));

        assertEquals("4,2", intersection);
    }

    @Test
    public void throws_exception_if_street_not_in_map_when_finding_intersections() {
        try {
            map.find(Intersection.of("street_23", "avenue_43"));
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException ignored) {
        }
    }


}