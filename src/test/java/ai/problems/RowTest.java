package ai.problems;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RowTest {

    @Test
    public void when_compared_to_goal_returns_correct_number_of_black_and_white() {
        Row row = new Row(ImmutableList.of(0, 1, 2, 3), ImmutableList.of(1, 2, 3, 4));
        assertEquals(0, row.getRightColorAndPosition());
        assertEquals(3, row.getRightColor());

        row = new Row(ImmutableList.of(0, 1, 2, 3), ImmutableList.of(3, 0, 1, 2));
        assertEquals(0, row.getRightColorAndPosition());
        assertEquals(4, row.getRightColor());

        row = new Row(ImmutableList.of(0, 1, 2, 3), ImmutableList.of(0, 1, 2, 3));
        assertEquals(4, row.getRightColorAndPosition());
        assertEquals(0, row.getRightColor());

        row = new Row(ImmutableList.of(0, 1, 2, 3), ImmutableList.of(0, 1, 4, 5));
        assertEquals(2, row.getRightColorAndPosition());
        assertEquals(0, row.getRightColor());

        row = new Row(ImmutableList.of(0, 0, 2, 3), ImmutableList.of(0, 1, 4, 5));
        assertEquals(1, row.getRightColorAndPosition());
        assertEquals(0, row.getRightColor());

        row = new Row(ImmutableList.of(2, 3, 0, 0), ImmutableList.of(0, 1, 4, 5));
        assertEquals(0, row.getRightColorAndPosition());
        assertEquals(1, row.getRightColor());

        row = new Row(ImmutableList.of(2, 3, 1, 0), ImmutableList.of(0, 0, 4, 5));
        assertEquals(0, row.getRightColorAndPosition());
        assertEquals(1, row.getRightColor());

        row = new Row(ImmutableList.of(2, 3, 0, 0), ImmutableList.of(0, 0, 4, 5));
        assertEquals(0, row.getRightColorAndPosition());
        assertEquals(2, row.getRightColor());
    }

    @Test
    public void foo() {
        Row row = new Row(ImmutableList.of(0, 0, 0, 0), ImmutableList.of(1, 1, 1, 1));
        assertFalse(row.isPossible(ImmutableList.of(0, 0, 0, 0)));

        row = new Row(ImmutableList.of(0, 0, 0, 0), ImmutableList.of(1, 1, 1, 1));
        assertTrue(row.isPossible(ImmutableList.of(1, 1, 1, 1)));
    }

}