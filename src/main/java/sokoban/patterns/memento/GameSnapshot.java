package sokoban.patterns.memento;

import java.util.ArrayList;
import java.util.List;

import sokoban.model.Position;
import sokoban.model.boxes.Box;
import sokoban.model.boxes.FragileBox;
import sokoban.model.boxes.KeyBox;
import sokoban.model.elements.BoardElement;
import sokoban.model.items.Item;
import sokoban.model.items.WaterBottle;
import sokoban.patterns.memento.ItemMementoData;

public class GameSnapshot {
    private final List<BoxMementoData> savedBoxes;
    private final List<ItemMementoData> savedItems;
    private final Position playerPosition;
    private final int playerEnergy;
    private final BoardElement[][] savedCells;
    private final int movements;
    private final int pushes;

    public GameSnapshot(List<Box> boxes, List<Item> items, int movements, int pushes, Position playerPosition, int playerEnergy, BoardElement[][] savedCells) {
        this.movements = movements;
        this.pushes = pushes;
        this.playerPosition = playerPosition;
        this.playerEnergy = playerEnergy;
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
            } else if (b instanceof sokoban.model.boxes.HeavyBox) {
                this.savedBoxes.add(new BoxMementoData(b.getPosition(), "HEAVY", 0));
            } else {
                this.savedBoxes.add(new BoxMementoData(b.getPosition(), "NORMAL", 0));
            }
        }

        this.savedItems = new ArrayList<>();
        for (Item item : items) {
            if (item == null) {
                continue;
            }
            if (item instanceof WaterBottle) {
                this.savedItems.add(new ItemMementoData(item.getPosition(), "WATER_BOTTLE"));
            }
        }
    }

    public List<BoxMementoData> getSavedBoxes() {
        return savedBoxes;
    }

    public List<ItemMementoData> getSavedItems() {
        return savedItems;
    }

    public Position getPlayerPosition() {
        return playerPosition;
    }

    public int getPlayerEnergy() {
        return playerEnergy;
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