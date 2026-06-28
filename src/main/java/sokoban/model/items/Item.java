package sokoban.model.items;

import sokoban.model.Position;
import sokoban.model.elements.Player;

public interface Item {
    Position getPosition();
    String getType();
    String getSymbol();
    void apply(Player player);
}
