package ai.games;

import static ai.games.TwoPlayerGame.PLAYER.MAX;

public class MinMax implements Strategy {

    @Override
    public <T extends TwoPlayerGame<T>> T play(T game) {
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;
        Node<T> node = game.player() == MAX ? max(game, alpha, beta) : min(game, alpha, beta);
        return node.getGame();
    }

    private <T extends TwoPlayerGame<T>> Node<T> max(T game, double alpha, double beta) {
        if (game.isDone()) {
            return new Node<>(game, game.utility());
        }

        Node<T> best = new Node<>(null, Double.NEGATIVE_INFINITY);
        for (T proposalGame : game.actions()) {
            Node<T> proposal = min(proposalGame, alpha, beta);
            if (proposal.getUtility() > best.getUtility()) {
                best = proposal;
            }
            if (best.getUtility() >= beta) {
                return proposal;
            }
            alpha = Math.max(alpha, best.getUtility());
        }

        return best;
    }

    private <T extends TwoPlayerGame<T>> Node<T> min(T game, double alpha, double beta) {
        if (game.isDone()) {
            return new Node<>(game, game.utility());
        }

        Node<T> best = new Node<>(null, Double.POSITIVE_INFINITY);
        for (T proposalGame : game.actions()) {
            Node<T> proposal = max(proposalGame, alpha, beta);
            if (proposal.getUtility() < best.getUtility()) {
                best = proposal;
            }
            if (best.getUtility() <= alpha) {
                return best;
            }
            beta = Math.min(beta, best.getUtility());
        }

        return best;
    }

    private static class Node<T extends TwoPlayerGame<T>> {
        private final T game;
        private final double utility;

        private Node(T game, double utility) {
            this.utility = utility;
            this.game = game;
        }

        public T getGame() {
            return game;
        }

        public double getUtility() {
            return utility;
        }
    }

}
