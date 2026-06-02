package sokoban.model.elements;

import sokoban.patterns.strategy.MovementStrategy;
import sokoban.patterns.strategy.NormalMovementStrategy;

public abstract class BoardElement {
    private final String name;
    private final boolean walkable;
    private final String symbol;
    private final MovementStrategy movementStrategy;

    public BoardElement(String name, boolean walkable, String symbol) {
        this(name, walkable, symbol, new NormalMovementStrategy());
    }

    public BoardElement(String name, boolean walkable, String symbol, MovementStrategy movementStrategy) {
        this.name = name;
        this.walkable = walkable;
        this.symbol = symbol;
        this.movementStrategy = movementStrategy;
    }

    public String getName() {
        return name;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public String getSymbol() {
        return symbol;
    }

    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    public boolean isDestinationCell() {
        return false;
    }

    public boolean isLockCell() {
        return false;
    }

    public boolean isClosedWall() {
        return false;
    }
}
