package sokoban.patterns.strategy;

import sokoban.model.Board;
import sokoban.model.Direction;
import sokoban.model.Position;

public interface MovementStrategy {

    Position calculateFinalPosition(Board board, Position startPosition, Direction direction);
}