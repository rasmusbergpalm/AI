package ai.games;

public interface Strategy {

    <T extends TwoPlayerGame<T>> T play(T initial);

}
