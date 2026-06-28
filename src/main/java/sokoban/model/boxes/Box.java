package sokoban.model.boxes;

import sokoban.model.Board;
import sokoban.model.Position;
import sokoban.patterns.strategy.PushEnergyStrategy;
import sokoban.patterns.strategy.NormalPushEnergyStrategy;

public abstract class Box {
    private Position position;
    private final PushEnergyStrategy pushEnergyStrategy;

    public Box(Position position) {
        this(position, new NormalPushEnergyStrategy());
    }

    public Box(Position position, PushEnergyStrategy pushEnergyStrategy) {
        this.position = position;
        this.pushEnergyStrategy = pushEnergyStrategy;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public PushEnergyStrategy getPushEnergyStrategy() {
        return pushEnergyStrategy;
    }

    public int getPushEnergyCost() {
        return pushEnergyStrategy.getEnergyCost();
    }

    public abstract String getType();

    public abstract String getSymbol();

    public boolean canBePushed() {
        return true;
    }

    public void onPushed(Board board) {
        // Por defecto, una caja normal no cambia su comportamiento.
    }

    public boolean isCorrectlyPlaced(Board board) {
        return board.isDestination(position);
    }
}