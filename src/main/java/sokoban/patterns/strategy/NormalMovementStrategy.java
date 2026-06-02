package sokoban.patterns.strategy;

import sokoban.model.Board;
import sokoban.model.Direction;
import sokoban.model.Position;

public class NormalMovementStrategy implements MovementStrategy {

    @Override
    public Position calculateFinalPosition(Board board, Position startPosition, Direction direction) {
        return startPosition;
    }
}