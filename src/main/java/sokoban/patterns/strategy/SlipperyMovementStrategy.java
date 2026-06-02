package sokoban.patterns.strategy;

import sokoban.model.Board;
import sokoban.model.Direction;
import sokoban.model.Position;

public class SlipperyMovementStrategy implements MovementStrategy {

    @Override
    public Position calculateFinalPosition(Board board, Position startPosition, Direction direction) {
        Position currentPosition = startPosition;
        Position nextPosition = currentPosition.move(direction);

        while (board.isWalkable(nextPosition)) {
            currentPosition = nextPosition;
            nextPosition = currentPosition.move(direction);
        }

        return currentPosition;
    }
}