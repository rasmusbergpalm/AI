package ai.games;

import com.google.common.collect.ImmutableSet;

public interface TwoPlayerGame<T extends TwoPlayerGame> {

    double utility();

    ImmutableSet<T> actions();

    boolean isDone();

    PLAYER player();

    String getState();

    enum PLAYER {
        MAX,
        MIN
    }


}
