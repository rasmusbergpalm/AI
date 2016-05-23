package ai.games;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;

import static ai.games.TwoPlayerGame.PLAYER.MAX;
import static ai.games.TwoPlayerGame.PLAYER.MIN;

public class TicTacToe implements TwoPlayerGame<TicTacToe> {

    private final String[] rows;
    private final String[] columns;
    private final String[] diagonals;
    private final PLAYER player;

    public TicTacToe() {
        this(new String[]{"---", "---", "---"}, MAX);
    }

    TicTacToe(String[] rows, PLAYER player) {
        this.rows = rows;
        this.columns = getColumns(rows);
        this.diagonals = getDiagonals(rows);
        this.player = player;
    }

    private String[] getDiagonals(String[] rows) {
        return new String[]{
                "" + rows[0].charAt(0) + rows[1].charAt(1) + rows[2].charAt(2),
                "" + rows[0].charAt(2) + rows[1].charAt(1) + rows[2].charAt(0),
        };
    }

    private String[] getColumns(String[] rows) {
        String[] columns = new String[rows.length];
        for (int c = 0; c < columns.length; c++) {
            String column = "";
            for (String row : rows) {
                column += row.charAt(c);
            }
            columns[c] = column;
        }
        return columns;
    }

    @Override
    public double utility() {
        if (hasWon(MAX)) {
            return 1;
        }
        if (hasWon(MIN)) {
            return -1;
        }
        return 0;
    }

    @Override
    public ImmutableSet<TicTacToe> actions() {
        ImmutableSet.Builder<TicTacToe> actions = ImmutableSet.builder();
        char token = player == MAX ? 'x' : 'o';
        for (int r = 0; r < rows.length; r++) {
            for (int c = 0; c < columns.length; c++) {
                if (rows[r].charAt(c) == '-') {
                    String[] clonedRows = rows.clone();
                    char[] clonedRow = clonedRows[r].toCharArray();
                    clonedRow[c] = token;
                    clonedRows[r] = String.valueOf(clonedRow);
                    actions.add(new TicTacToe(clonedRows, player == MAX ? MIN : MAX));
                }
            }
        }
        return actions.build();
    }

    public boolean isDone() {
        return hasWon(MAX) || hasWon(MIN) || this.actions().isEmpty();
    }

    private boolean hasWon(PLAYER player) {
        return hasWon(rows, player) || hasWon(columns, player) || hasWon(diagonals, player);
    }

    private boolean hasWon(String[] strs, PLAYER player) {
        String pattern = (player == MAX ? "xxx" : "ooo");
        for (String str : strs) {
            if (str.equals(pattern)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public PLAYER player() {
        return player;
    }

    @Override
    public String getState() {
        return Joiner.on("\n").join(rows);
    }
}
