package sokoban.patterns.memento;

import sokoban.model.Position;

public class ItemMementoData {
    private final Position position;
    private final String itemType;

    public ItemMementoData(Position position, String itemType) {
        this.position = position;
        this.itemType = itemType;
    }

    public Position getPosition() {
        return position;
    }

    public String getItemType() {
        return itemType;
    }
}
