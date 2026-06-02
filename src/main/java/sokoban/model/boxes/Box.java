package sokoban.model.boxes;

import sokoban.model.Board;
import sokoban.model.Position;

public abstract class Box {
    private Position position;

    public Box(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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