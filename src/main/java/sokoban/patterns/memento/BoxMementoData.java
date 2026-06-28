package sokoban.patterns.memento;

import sokoban.model.Position;

public class BoxMementoData {
    private final Position position;
    private final String boxType; // "NORMAL", "KEY", o "FRAGILE"
    private final int currentResistance; // Crucial para la FragileBox

    public BoxMementoData(Position position, String boxType, int currentResistance) {
        this.position = position;
        this.boxType = boxType;
        this.currentResistance = currentResistance;
    }

    public Position getPosition() {
        return position;
    }

    public String getBoxType() {
        return boxType;
    }

    public int getCurrentResistance() {
        return currentResistance;
    }
}
