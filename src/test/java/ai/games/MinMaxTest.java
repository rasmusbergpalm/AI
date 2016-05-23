package ai.games;

import org.junit.Test;

import static org.junit.Assert.*;

public class MinMaxTest {

    @Test
    public void tic_tac_toe_ends_in_draws() {
        TicTacToe played = new MinMax().play(new TicTacToe());
        System.out.println(played.getState());
        assertTrue(played.isDone());
        assertEquals(0, played.utility(), 0);
    }
}