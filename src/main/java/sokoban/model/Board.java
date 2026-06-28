package sokoban.model;

import java.util.ArrayList;
import java.util.List;

import sokoban.model.boxes.Box;
import sokoban.model.elements.BoardElement;
import sokoban.model.elements.OpenWall;
import sokoban.model.elements.Player;
import sokoban.model.items.Item;

public class Board {
    private final BoardElement[][] cells;
    private Player player;
    private final List<Box> boxes;
    private final List<Item> items;

    public Board(int rows, int cols) {
        cells = new BoardElement[rows][cols];
        boxes = new ArrayList<>();
        items = new ArrayList<>();
    }

    public int getRows() {
        return cells.length;
    }

    public int getCols() {
        return cells[0].length;
    }

    public BoardElement getElementAt(Position position) {
        return cells[position.getRow()][position.getCol()];
    }

    public void setElementAt(Position position, BoardElement element) {
        cells[position.getRow()][position.getCol()] = element;
    }

    public boolean isInside(Position position) {
        return position.getRow() >= 0 &&
                position.getRow() < getRows() &&
                position.getCol() >= 0 &&
                position.getCol() < getCols();
    }

    public boolean isWalkable(Position position) {
        if (!isInside(position)) {
            return false;
        }

        BoardElement element = getElementAt(position);

        return element != null &&
                element.isWalkable() &&
                getBoxAt(position) == null;
    }

    public boolean isDestination(Position position) {
        if (!isInside(position)) {
            return false;
        }

        BoardElement element = getElementAt(position);
        return element != null && element.isDestinationCell();
    }

    public boolean isLockCell(Position position) {
        if (!isInside(position)) {
            return false;
        }

        BoardElement element = getElementAt(position);
        return element != null && element.isLockCell();
    }

    public void openClosedWalls() {
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getCols(); col++) {
                Position position = new Position(row, col);
                BoardElement element = getElementAt(position);

                if (element != null && element.isClosedWall()) {
                    setElementAt(position, new OpenWall());
                }
            }
        }
    }

    public Box getBoxAt(Position position) {
        for (Box box : boxes) {
            if (box.getPosition().equals(position)) {
                return box;
            }
        }

        return null;
    }

    public void addBox(Box box) {
        boxes.add(box);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public List<Item> getItems() {
        return items;
    }

    public Item getItemAt(Position position) {
        for (Item item : items) {
            if (item.getPosition().equals(position)) {
                return item;
            }
        }
        return null;
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public BoardElement[][] getCellsCopy() {
        BoardElement[][] copy = new BoardElement[getRows()][getCols()];
        for (int row = 0; row < getRows(); row++) {
            System.arraycopy(cells[row], 0, copy[row], 0, getCols());
        }
        return copy;
    }

    public String getSymbolAt(Position position) {
        if (player != null && player.getPosition().equals(position)) {
            return "@";
        }

        Box box = getBoxAt(position);

        if (box != null) {
            return box.getSymbol();
        }

        BoardElement element = getElementAt(position);

        if (element != null) {
            return element.getSymbol();
        }

        return " ";
    }
}