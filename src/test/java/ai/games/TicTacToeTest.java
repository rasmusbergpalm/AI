package ai.games;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Set;

import static ai.games.TwoPlayerGame.PLAYER.MAX;
import static ai.games.TwoPlayerGame.PLAYER.MIN;
import static org.junit.Assert.*;

public class TicTacToeTest {
    @Test
    public void utility() throws Exception {
        TicTacToe game = new TicTacToe();
        assertEquals(0, game.utility(), 0);

        game = new TicTacToe(new String[]{"xxx", "---", "---"}, MAX);
        assertEquals(1, game.utility(), 0);

        game = new TicTacToe(new String[]{"ooo", "---", "---"}, MAX);
        assertEquals(-1, game.utility(), 0);
    }

    @Test
    public void actions() throws Exception {
        TicTacToe game = new TicTacToe();
        Set<String> expected = Sets.newHashSet(
                "x--\n---\n---",
                "-x-\n---\n---",
                "--x\n---\n---",
                "---\nx--\n---",
                "---\n-x-\n---",
                "---\n--x\n---",
                "---\n---\nx--",
                "---\n---\n-x-",
                "---\n---\n--x"
        );
        Set<String> actual = Sets.newHashSet();
        for (TicTacToe g : game.actions()) {
            actual.add(g.getState());
        }

        assertEquals(expected, actual);

        game = new TicTacToe(new String[]{"x--", "---", "---"}, MIN);
        expected = Sets.newHashSet(
                "xo-\n---\n---",
                "x-o\n---\n---",
                "x--\no--\n---",
                "x--\n-o-\n---",
                "x--\n--o\n---",
                "x--\n---\no--",
                "x--\n---\n-o-",
                "x--\n---\n--o"
        );
        actual = Sets.newHashSet();
        for (TicTacToe g : game.actions()) {
            actual.add(g.getState());
        }

        assertEquals(expected, actual);
    }

    @Test
    public void isDone() throws Exception {
        TicTacToe game = new TicTacToe();
        assertFalse(game.isDone());

        game = new TicTacToe(new String[]{"xxx", "---", "---"}, MAX);
        assertTrue(game.isDone());

        game = new TicTacToe(new String[]{"ooo", "---", "---"}, MAX);
        assertTrue(game.isDone());

        game = new TicTacToe(new String[]{"x--", "x--", "x--"}, MAX);
        assertTrue(game.isDone());

        game = new TicTacToe(new String[]{"x--", "-x-", "--x"}, MAX);
        assertTrue(game.isDone());

        game = new TicTacToe(new String[]{"--x", "-x-", "--x"}, MAX);
        assertFalse(game.isDone());
    }

    @Test
    public void player() throws Exception {
        TicTacToe game = new TicTacToe();
        TwoPlayerGame.PLAYER expected = MAX;
        while (!game.actions().isEmpty()) {
            assertEquals(expected, game.player());
            game = game.actions().iterator().next();
            expected = (expected == MAX ? MIN : MAX);
        }
    }

    @Test
    public void getState() throws Exception {
        TicTacToe game = new TicTacToe();
        assertEquals("---\n---\n---", game.getState());

        game = new TicTacToe(new String[]{"xxx", "---", "---"}, MAX);
        assertEquals("xxx\n---\n---", game.getState());
    }

}