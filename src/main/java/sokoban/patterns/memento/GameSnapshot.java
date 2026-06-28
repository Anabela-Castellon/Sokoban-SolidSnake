package sokoban.patterns.memento;

import java.util.ArrayList;
import java.util.List;

import sokoban.model.Position;
import sokoban.model.boxes.Box;
import sokoban.model.boxes.FragileBox;
import sokoban.model.boxes.KeyBox;
import sokoban.model.elements.BoardElement;

public class GameSnapshot {
    private final List<BoxMementoData> savedBoxes;
    private final Position playerPosition;
    private final BoardElement[][] savedCells;
    private final int movements;
    private final int pushes;

    public GameSnapshot(List<Box> boxes, int movements, int pushes, Position playerPosition, BoardElement[][] savedCells) {
        this.movements = movements;
        this.pushes = pushes;
        this.playerPosition = playerPosition;
        this.savedCells = savedCells;

        this.savedBoxes = new ArrayList<>();
        for (Box b : boxes) {
            if (b == null) {
                continue;
            }
            if (b instanceof FragileBox fragileBox) {
                this.savedBoxes.add(new BoxMementoData(fragileBox.getPosition(), "FRAGILE", fragileBox.getResistance()));
            } else if (b instanceof KeyBox) {
                this.savedBoxes.add(new BoxMementoData(b.getPosition(), "KEY", 0));
            } else {
                this.savedBoxes.add(new BoxMementoData(b.getPosition(), "NORMAL", 0));
            }
        }
    }

    public List<BoxMementoData> getSavedBoxes() {
        return savedBoxes;
    }

    public Position getPlayerPosition() {
        return playerPosition;
    }

    public BoardElement[][] getSavedCells() {
        return savedCells;
    }

    public int getMovements() {
        return movements;
    }

    public int getPushes() {
        return pushes;
    }
}